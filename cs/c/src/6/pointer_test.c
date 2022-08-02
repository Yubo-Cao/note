#include <stdio.h>

void main(void){
	int a = 0;
	int *d = &a;
	*d = 10 - *d; // Access a pointer variable, obtain its value to calculate a rvalue (i.e., 10). and then assign it back to memory address of it
	d = 10 - *d; // ERROR: Nobody really want to use int as memory address
}
