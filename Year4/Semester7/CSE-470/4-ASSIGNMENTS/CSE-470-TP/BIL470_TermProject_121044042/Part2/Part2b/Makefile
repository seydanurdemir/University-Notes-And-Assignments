all: Part2b.exe
	
Part2b.exe : main.o aes_ofb_mode.o aes_cbc_mode.o aes_implementation.o
	gcc main.o aes_ofb_mode.o aes_cbc_mode.o aes_implementation.o -o Part2b.exe

main.o: main.c
	gcc -c main.c

aes_ofb_mode.o: aes_ofb_mode.c
	gcc -c aes_ofb_mode.c

aes_cbc_mode.o: aes_cbc_mode.c
	gcc -c aes_cbc_mode.c

aes_implementation.o: aes_implementation.c
	gcc -c aes_implementation.c

clean:
	rm -rf *o *exe
