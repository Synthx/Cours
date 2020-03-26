#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#define MAX 	10

int tab[MAX];
int nb=0;
pthread_mutex_t lock;

struct min_max_arg
{
	int *min, *max;
};

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
	for (i=0; i<MAX; i++) {
		if (tab[i]>seuil) {
			pthread_mutex_lock(&lock);
			nb++;
			pthread_mutex_unlock(&lock);
		}
	}
}
void infSeuil(int seuil){
	int i;
	for (i=0; i<MAX; i++) {
		if (tab[i]<seuil) {
			pthread_mutex_lock(&lock);
			nb++;
			pthread_mutex_unlock(&lock);
		}
	};
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

void *thread_seuilSup(void *arg) {
	int seuil = *(int *)arg;
	free(arg);

	supSeuil(seuil);
	
	return NULL;
}

void *thread_seuifInf(void *arg) {
	int seuil = *(int *)arg;
	free(arg);

	infSeuil(seuil);

	return NULL;
}

void *thread_min_max(void *arg) {
	struct min_max_arg *minMaxArg = (struct min_max_arg *)arg;
	free(arg);

	minMax(minMaxArg->min, minMaxArg->max);

	return NULL;
}

int main(){
	int *seuilInf, *seuilSup, min, max;
	struct min_max_arg *minMaxArg;
	pthread_t mean_thread, sup_thread, inf_thread, min_max_thread;
	pthread_attr_t attr;

	minMaxArg = (struct min_max_arg *) malloc(sizeof(struct min_max_arg));
	seuilInf = (int *) malloc(sizeof(int));
	seuilSup = (int *) malloc(sizeof(int));

	minMaxArg->min = &min;
	minMaxArg->max = &max;

	lire();
	affiche();

	printf("saisir les seuils sup et inf : ");
	scanf("%d%d", seuilSup, seuilInf);

	// initialisation du sémaphore
	if (pthread_mutex_init(&lock, NULL) != 0) { 
        perror("pthread_mutex_init"); 
        exit(1);
    } 

	// attribut des threads
	if (pthread_attr_init(&attr)) {
		perror("pthread_attr_init");
		exit(1);
	}
	if (pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_DETACHED)) {
		perror("pthread_attr_setdetachstate");
		exit(1);
	}

	// creattion du thread détaché pour calculer la moyenne
	if (pthread_create(&mean_thread, &attr, thread_moyenne, NULL)) {
		perror("pthread_create moyenne");
		exit(1);
	}
	// creattion du thread joignable pour calculer la borne sup
	if (pthread_create(&sup_thread, NULL, thread_seuilSup, (void *)seuilSup)) {
		perror("pthread_create seuilSup");
		exit(1);
	}
	// creattion du thread joignable pour calculer la borne inf
	if (pthread_create(&inf_thread, NULL, thread_seuifInf, (void *)seuilInf)) {
		perror("pthread_create seuilInf");
		exit(1);
	}
	// creattion du thread joignable pour calculer le min max
	if (pthread_create(&min_max_thread, NULL, thread_min_max, (void *)minMaxArg)) {
		perror("pthread_create min_max");
		exit(1);
	}

	pthread_join(min_max_thread, NULL);

	printf("min = %d, max = %d\n", max, max);

	pthread_join(sup_thread, NULL);
	pthread_join(inf_thread, NULL);

	printf("nb = %d\n", nb);

	pthread_exit(NULL);

	return 0;
}
