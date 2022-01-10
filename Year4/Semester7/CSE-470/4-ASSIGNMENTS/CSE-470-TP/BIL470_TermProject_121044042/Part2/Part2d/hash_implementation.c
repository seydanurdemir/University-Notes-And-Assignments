#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "aes_header.h"
#include "hash_header.h"

void encrypt_aes128cbc(char *src, char *temp)
{
	uint8 key[16] = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	uint8 IV1[16] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	
	aes_cbc_encrypt(src, temp, key, IV1);
	
	return;
}

void encrypted_last128block(char *temp, char *dest)
{
    uint8 buffer[16] = {0};

	FILE *fp_temp = fopen(temp, "rb");
    int readlen = 0;
    while (1)
    {
		readlen = fread(buffer, 1, 16, fp_temp);
		if (readlen != 16 || readlen == 0)
			break;
		FILE *fp_dest = fopen(dest, "wb");
			fwrite(buffer, 1, 16, fp_dest);
		fclose(fp_dest);
    }
    fclose(fp_temp);

	return;
}

void hash(char *src, char *dest)
{
	char temp[30] = "Temp_Hash_File.txt";

	encrypt_aes128cbc(src,temp);
	encrypted_last128block(temp,dest);

	remove(temp);

    return;
}
