#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "aes_header.h"

void aes_ecb_encrypt(char *src, char *dest, uint8 *key)
{
    uint8 buffer[16] = {0};
    FILE *fp = fopen(src, "rb");
    FILE *f_write = fopen(dest, "wb");
    int readlen = 0;
    while (1)
    {
        readlen = fread(buffer, 1, 16, fp);
        pkcs_7(buffer, readlen);
        encrypt(buffer, key);
        fwrite(buffer, 1, 16, f_write);
        if (readlen != 16 || readlen == 0)
            break;
    }
    fclose(fp);
    fclose(f_write);
    return;
}

void aes_ecb_decrypt(char *src, char *dest, uint8 *key)
{
    uint8 buffer[16] = {0};
    FILE *fp = fopen(src, "rb");
    FILE *f_write = fopen(dest, "wb");
    int total = filesize(fp) / 16;
    int readlen = 0, count = 0;
    while (1)
    {
        count++;
        readlen = fread(buffer, 1, 16, fp);
        if (readlen == 0)
            break;
        decrypt(buffer, key);
        int write_len = 16;
        if (count == total)
            write_len = 16 - buffer[15];
        fwrite(buffer, 1, write_len, f_write);
    }
    fclose(fp);
    fclose(f_write);
    return;
}
