#ifndef _SIGN_HEADER_H_
#define _SIGN_HEADER_H_

void take_last128block(char *src, char *temp);
int auth(char *src, char *dest_sign);

#endif
