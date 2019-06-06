# Planification de livraison

> ROSSET Vincent - TANIEL Rémi

## Contenu de l'archive

L'archive contient les éléments suivants :
- un makefile
- fichiers sources (`.h` et `.c`)

En ce qui concerne les fichiers sources, voici leur description :
- `main.c` : programme principal
- `utils.h / utils.c` : fonctions permettant la lecture des fichiers d'entrées et l'affichage des différentes structure de donnée que nous utilisons
- `structure.h / structure.c` : déclarations des structures de données ainsi que toutes les fonctions qui leur sont associées
- `functions.h / functions.c` : implémentation des différents algorithmes (grand tour, split et bellman) nécessaire à l'éxecution de programme

## Compilation

Un makefile s'occupe d'éxécuter les commandes nécessaire à la compilation de nos différents fichiers,
il suffit donc d'éxécuter la commande suivante :

``` bash
$ make
```

## Execution

La compilation génère un executable `programme`, pour l'éxécuter rien de plus simple, il n'a pas besoin de redirection de l'entrée standard :

``` bash
$ ./programme
```