#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "aes_header.h"

int main (void)
{
	char src[30] = "Plain_Text.txt";

	char decrypt_ecb[30] = "Decrypted_Text_ECB.txt";
	char encrypt_ecb[30] = "Encrypted_Text_ECB.txt";

	uint8 key[16] = {0x0f, 0x15, 0x71, 0xc9, 0x47, 0xd9, 0xe8, 0x59, 0x0c, 0xb7, 0xad, 0xd6, 0xaf, 0x7f, 0x67, 0x98};

	uint8 IV1[16] = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
	uint8 IV2[16] = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1};

	int start_ecb = clock();
	aes_ecb_encrypt(src, encrypt_ecb, key, IV1);
	aes_ecb_decrypt(encrypt_ecb, decrypt_ecb, key, IV2);
	int end_ecb = clock();

	printf("\nTime of Encryption Decryption (in ECB Mode as Default) : %lf\n\n", ((double)(end_ecb - start_ecb) / CLOCKS_PER_SEC));

	return 0;
}
