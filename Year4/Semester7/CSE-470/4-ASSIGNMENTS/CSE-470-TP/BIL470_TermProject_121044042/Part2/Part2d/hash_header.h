#ifndef _HASH_HEADER_H_
#define _HASH_HEADER_H_

void encrypt_aes128cbc(char *src, char *temp);
void encrypted_last128block(char *temp, char *dest);
void hash(char *src, char *dest);

#endif
