#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "aes_header.h"

void aes_cbc_encrypt(char *src, char *dest, uint8 *key, uint8 *IV)
{
    uint8 buffer[16] = {0};
    uint8 *xor_in = IV;
    FILE *fp = fopen(src, "rb");
    FILE *f_write = fopen(dest, "wb");
    int readlen = 0;
    int i = 0;
    while (1)
    {
        readlen = fread(buffer, 1, 16, fp);
        pkcs_7(buffer, readlen);
        bytesxor(buffer, xor_in);
        encrypt(buffer, key);
        for (i = 0; i < 16; i++)
            xor_in[i] = buffer[i];
        fwrite(buffer, 1, 16, f_write);
        if (readlen != 16 || readlen == 0)
            break;
    }
    fclose(fp);
    fclose(f_write);
    return;
}

void aes_cbc_decrypt(char *src, char *dest, uint8 *key, uint8 *IV)
{
    uint8 buffer[16] = {0}, tmp[16] = {0};
    uint8 *xor_in = IV;
    FILE *fp = fopen(src, "rb");
    FILE *f_write = fopen(dest, "wb");
    int total = filesize(fp) / 16;
    int readlen = 0, count = 0;
    int i = 0;
    while (1)
    {
        count++;
        readlen = fread(buffer, 1, 16, fp);
        if (readlen == 0)
            break;
        for (i = 0; i < 16; i++)
            tmp[i] = buffer[i];
        decrypt(buffer, key);
        bytesxor(buffer, xor_in);
        for (i = 0; i < 16; i++)
            xor_in[i] = tmp[i];
        int write_len = 16;
        if (count == total)
            write_len = 16 - buffer[15];
        fwrite(buffer, 1, write_len, f_write);
    }
    fclose(fp);
    fclose(f_write);
    return;
}
