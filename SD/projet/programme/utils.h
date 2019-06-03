/* utils.h */
#include <stdio.h>
#include <stdlib.h>
#include "functions.h"

#define MAX 100

extern void init_data(struct donnees *, int *);
extern void read_data(FILE *, struct donnees *);
extern void display_T(int *, int);
extern void display_H(struct liste *, int);
extern void display_res(struct result, int *);