#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "aes_header.h"
#include "hash_header.h"
#include "sign_header.h"
#include "auth_header.h"

char* strrev(char *str)
{
    char temp;
    size_t len = strlen(str) - 1;
    size_t i;
    size_t k = len;

    for(i = 0; i < len; i++)
    {
        temp = str[k];
        str[k] = str[i];
        str[i] = temp;
        k--;

        if(k == (len / 2))
        {
            break;
        }
    }
}

void take_last128block(char *src, char *temp)
{
    char ch;
    char buffer[32];
	int i = 0, j = 0, cnt = 0;
	
	FILE *fp1 = fopen(src, "r");
	fseek(fp1,0,SEEK_END);
	cnt = ftell(fp1);
	while(i < 32)
	{
		i++;
		fseek(fp1,-i,SEEK_END);
		fscanf(fp1,"%c",&ch);
		buffer[j++] = ch;
	}
    fclose(fp1);
    
    buffer[32] = '\0';
    strrev(buffer);
    
	FILE *fp2 = fopen(temp, "w");
	fprintf(fp2,"%s",buffer);
    fclose(fp2);

	return;
}

int auth(char *src, char *dest_sign)
{
	char temp_sign[30] = "Temporary_File_1.txt";
	char temp_hash[30] = "Temporary_File_2.txt";

	take_last128block(dest_sign, temp_sign);
	uint8 key[16] = {0x0f, 0x15, 0x71, 0xc9, 0x47, 0xd9, 0xe8, 0x59, 0x0c, 0xb7, 0xad, 0xd6, 0xaf, 0x7f, 0x67, 0x98};
	uint8 IV1[16] = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
	uint8 IV2[16] = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
	aes_cbc_decrypt(temp_sign, temp_hash, key, IV2);

	char auth_hash[30] = "Temporary_File_3.txt";

	hash(src, auth_hash);

	FILE *fp1 = fopen(auth_hash, "r");
	FILE *fp2 = fopen(temp_hash, "r");
	int status = 0;
    char ch1 = '.', ch2 = '.';
	while(status != EOF)
    {
		if (status != EOF)
		{
			status = fscanf(fp1,"%c",&ch1);
			fscanf(fp2,"%c",&ch2);
			if (status != EOF)
			{
				if (ch1 != ch2)
					return 0;
			}
		}
    }
	fclose(fp2);
    fclose(fp1);

	remove(auth_hash);
	remove(temp_hash);
	remove(temp_sign);

	return 1;
}
