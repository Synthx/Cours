Le fichier AMPL initial se nomme : tarification_electrique.ampl

Le fichier simplifié : tarification.dat

Le code est fonctionnel :

gcc *.c
./a.out < tarification.dat

Sortie (affiche le fichier en entrée sans faire aucun traitement) :

duree # heure
niv_min # megawatt/generateur
niv_max # megawatt/generateur
cout_au_min # euro/generateur/heure
nb_actifs # generateur
nb_actifs * niv_min = production
nb_actifs * TMP = frais_au_min
cout_au_min * duree = TMP

