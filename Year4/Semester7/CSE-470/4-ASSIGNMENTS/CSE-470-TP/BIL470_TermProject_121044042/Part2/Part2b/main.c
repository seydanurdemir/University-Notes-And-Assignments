#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "aes_header.h"

int main (void)
{
	char src[30] = "Plain_Text.txt";

	char decrypt_cbc[30] = "Decrypted_Text_CBC.txt";
	char dest_cbc[30] = "Encrypted_Text_CBC.txt";

	char decrypt_ofb[30] = "Decrypted_Text_OFB.txt";
	char dest_ofb[30] = "Encrypted_Text_OFB.txt";

	uint8 key[16] = {0x0f, 0x15, 0x71, 0xc9, 0x47, 0xd9, 0xe8, 0x59, 0x0c, 0xb7, 0xad, 0xd6, 0xaf, 0x7f, 0x67, 0x98};

	uint8 IV1[16] = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
	uint8 IV2[16] = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1};

	int start_cbc = clock();
	aes_cbc_encrypt(src, dest_cbc, key, IV1);
	aes_cbc_decrypt(dest_cbc, decrypt_cbc, key, IV2);
	int end_cbc = clock();

	printf("\nTime of Encryption Decryption in CBC Mode : %lf\n\n", ((double)(end_cbc - start_cbc) / CLOCKS_PER_SEC));

	int start_ofb = clock();
	aes_ofb_encrypt(src, dest_ofb, key, IV1);
	aes_ofb_decrypt(dest_ofb, decrypt_ofb, key, IV2);
	int end_ofb = clock();

	printf("\nTime of Encryption Decryption in OFB Mode : %lf\n\n", ((double)(end_ofb - start_ofb) / CLOCKS_PER_SEC));

	return 0;
}
