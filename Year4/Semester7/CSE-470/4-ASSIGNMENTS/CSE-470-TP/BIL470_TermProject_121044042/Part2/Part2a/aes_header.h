#ifndef _AES_HEADER_H_
#define _AES_HEADER_H_

typedef unsigned char uint8;

void pkcs_7(uint8 *content, int len);
int filesize(FILE *fp);
void bytesxor(uint8 *a, uint8 *b);

int NrComputer(int Nk);

void ShiftRows(uint8 *s);
void InvShiftRows(uint8 *s);

void MixColumns(uint8 *s);
void InvMixColumns(uint8 *s);

void AddRoundKey(uint8 *s, uint8 *Roundkey);
void InvAddRoundKey(uint8 *s, uint8 *Roundkey);

void SubBytes(uint8 *s);
void InvSubBytes(uint8 *s);

unsigned int RotWord(unsigned int word);

unsigned int Key_Sub(unsigned int w);
uint8 **KeyExpansion(uint8 *key, int mode);

void encrypt(uint8 *state, uint8 *key);
void decrypt(uint8 *state, uint8 *key);

#endif
