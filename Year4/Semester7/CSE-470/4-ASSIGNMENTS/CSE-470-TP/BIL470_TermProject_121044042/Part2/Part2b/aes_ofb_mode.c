#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "aes_header.h"

void aes_ofb_encrypt(char *src, char *dest, uint8 *key, uint8 *IV)
{
    uint8 buffer[1] = {0}, select = 0;
    uint8 *reg = IV, x[16] = {0};
    FILE *fp = fopen(src, "rb");
    FILE *f_write = fopen(dest, "wb");
    int readlen = 0;
    int i = 0;
    while (1)
    {
        readlen = fread(buffer, 1, 1, fp);
        if (readlen == 0)
            break;
        for (i = 0; i < 16; i++)
            x[i] = reg[i];
        encrypt(x, key);
        buffer[0] ^= x[0];
        fwrite(buffer, 1, 1, f_write);
        for (i = 0; i < 16; i++)
            reg[i] = reg[i + 1];
        reg[15] = x[0];
    }
    fclose(fp);
    fclose(f_write);
    return;
}

void aes_ofb_decrypt(char *src, char *dest, uint8 *key, uint8 *IV)
{
    uint8 buffer[1] = {0}, select = 0;
    uint8 *reg = IV, x[16] = {0};
    FILE *fp = fopen(src, "rb");
    FILE *f_write = fopen(dest, "wb");
    int readlen = 0;
    int i = 0;
    while (1)
    {
        readlen = fread(buffer, 1, 1, fp);
        if (readlen == 0)
            break;
        for (i = 0; i < 16; i++)
            x[i] = reg[i];
        encrypt(x, key);
        buffer[0] ^= x[0];
        fwrite(buffer, 1, 1, f_write);
        for (i = 0; i < 16; i++)
            reg[i] = reg[i + 1];
        reg[15] = x[0];
    }
    fclose(fp);
    fclose(f_write);
    return;
}
