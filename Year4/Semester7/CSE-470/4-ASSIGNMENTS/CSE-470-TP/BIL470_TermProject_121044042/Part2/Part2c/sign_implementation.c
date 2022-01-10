#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "aes_header.h"
#include "hash_header.h"
#include "sign_header.h"

void sign(char *src, char *hash, char *dest)
{
	uint8 buf;
	
	FILE *fp_src = fopen(src, "rb");
	FILE *fp_dest = fopen(dest, "wb");
	while (!feof(fp_src))
	{
		fread(&buf,1,1,fp_src);
		if (!feof(fp_src))
			fwrite(&buf,1,1,fp_dest);
	}
	fclose(fp_dest);
	fclose(fp_src);

	char temp[30] = "Temp_Sign_File.txt";

	uint8 key[16] = {0x0f, 0x15, 0x71, 0xc9, 0x47, 0xd9, 0xe8, 0x59, 0x0c, 0xb7, 0xad, 0xd6, 0xaf, 0x7f, 0x67, 0x98};
	uint8 IV1[16] = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1};

	aes_cbc_encrypt(hash, temp, key, IV1);

	FILE *fp_temp = fopen(temp, "rb");
	FILE *fp_dest_2 = fopen(dest, "ab");
	while (!feof(fp_temp))
	{
		fread(&buf,1,1,fp_temp);
		if (!feof(fp_temp))
			fwrite(&buf,1,1,fp_dest_2);
	}
	fclose(fp_dest_2);
	fclose(fp_temp);

	remove(temp);

	return;
}
