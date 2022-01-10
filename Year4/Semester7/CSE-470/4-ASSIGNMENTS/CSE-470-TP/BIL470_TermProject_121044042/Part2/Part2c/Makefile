all: Part2c.exe
	
Part2c.exe : main.o sign_implementation.o hash_implementation.o aes_cbc_mode.o aes_implementation.o
	gcc main.o sign_implementation.o hash_implementation.o aes_cbc_mode.o aes_implementation.o -o Part2c.exe

main.o: main.c
	gcc -c main.c

sign_implementation.o: sign_implementation.c
	gcc -c sign_implementation.c

hash_implementation.o: hash_implementation.c
	gcc -c hash_implementation.c

aes_cbc_mode.o: aes_cbc_mode.c
	gcc -c aes_cbc_mode.c

aes_implementation.o: aes_implementation.c
	gcc -c aes_implementation.c

clean:
	rm -rf *o *exe
