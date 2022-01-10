#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "aes_header.h"
#include "hash_header.h"
#include "sign_header.h"
#include "auth_header.h"

int main (void)
{
	char src[30] = "Plain_Text.txt";

	char dest_hash[30] = "Hash_of_File.txt";
	char dest_sign[30] = "Signed_Text.txt";

	int start_hash = clock();
	hash(src, dest_hash);
	int end_hash = clock();

	printf("\nTime of Hash (using CBC Mode) : %lf\n\n", ((double)(end_hash - start_hash) / CLOCKS_PER_SEC));
	
	int start_sign = clock();
	sign(src, dest_hash, dest_sign);
	int end_sign = clock();

	printf("\nTime of Sign (using HASH) : %lf\n\n", ((double)(end_sign - start_sign) / CLOCKS_PER_SEC));

	int start_auth = clock();
	int authorized = auth(src, dest_sign);
	int end_auth = clock();

	printf("\nTime of Auth (compares SIGN) : %lf\n\n", ((double)(end_auth - start_auth) / CLOCKS_PER_SEC));
	
	if (authorized == 1)
		printf("\nAuthorized, File Did Not Changed, Your Security is Under the Control.\n\n");
	else
		printf("\nCould Not Authorized, File May Be Changed, Your Security is Under the Risk.\n\n");

	//remove(dest_hash);

	return 0;
}
