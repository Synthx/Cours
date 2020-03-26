#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#define MAX 	10

int tab[MAX];
int nb=0;

void lire() {
	int i;
	printf("saisir %d entiers\n", MAX);
	for (i=0; i<MAX ; i++) scanf("%d", &tab[i]);
}
void affiche(){
	int i;
	printf("entiers saisis :");
	for (i=0; i<MAX ; i++) printf("%d ",tab [i]);
	printf("\n");
}
void moyenne(){
	int i, moy=tab[0];
	for (i=1; i<MAX; i++) moy = moy + tab[i];
	printf("moyenne des entiers = %f\n", (float)moy/MAX);
}
void supSeuil(int seuil){
	int i;
	for (i=0; i<MAX; i++) if (tab[i]>seuil) nb++;
	printf("fin de fonction supSeuil : nb = %d\n", nb);
}
void infSeuil(int seuil){
	int i;
	for (i=0; i<MAX; i++) if (tab[i]<seuil) nb++;
	printf("fin de fonction infSeuil : nb = %d\n", nb);
}

void minMax(int *min, int *max) {
	int i;
	*min=tab[0];
	*max=tab[0];
	for (i=1; i<MAX; i++) {
	    if (tab[i]<*min) *min=tab[i];
	    else if (tab[i]>*max) *max=tab[i];
	}
}

void *thread_moyenne(void *arg) {
	moyenne();
	
	return NULL;
}

int main(){
	int seuilInf, seuilSup, min, max;
	pthread_attr_t attr;
	pthread_t thread;

	lire();
	affiche();

	printf("saisir les seuils sup et inf : ");
	scanf("%d%d", &seuilSup, &seuilInf);

	// attribut du thread
	if (pthread_attr_init(&attr)) {
		perror("pthread_attr_init");
		exit(1);
	}
	if (pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_DETACHED)) {
		perror("pthread_attr_setdetachstate");
		exit(1);
	}

	// création du thread
	if (pthread_create(&thread, &attr, thread_moyenne, NULL)) {
		perror("pthread_create");
		exit(1);
	}

	supSeuil(seuilSup);
	infSeuil(seuilInf);
	minMax(&min, &max);

	printf("min = %d, max = %d\n", min, max);

	pthread_exit(NULL);

	return 0;
}
