# François Boulier
# Tarification d'électricité
# option solver cplex

#----------------------------------------------------------------------
# Ce qui ne concerne que les tranches horaires

param N integer >= 0;
set TRANCHES := 1 .. N circular;

param duree {TRANCHES} >= 0;		# en heure
check : sum {t in TRANCHES} duree [t] = 24;

param marge >= 0;
param demande {TRANCHES} >= 0;		# en megawatt 

#----------------------------------------------------------------------
# Ce qui ne concerne que les types de generateur

set TYPES;

param nb_gen {TYPES} >= 0;		# en generateur
param niv_min {TYPES} >= 0;		# en megawatt/generateur
param niv_max {TYPES} >= 0;		# en megawatt/generateur
param cout_demarrage {TYPES} >= 0;	# en euro/generateur
param cout_au_min {TYPES} >= 0;		# en euro/generateur/heure
param cout_extra_megawatt {TYPES} >= 0;	# en euro/megawatt/heure
					# au-dessus du minimum
#----------------------------------------------------------------------
# Ce qui concerne les deux
					# en generateur
var nb_demarres {TRANCHES, y in TYPES} integer >= 0, <= nb_gen [y];
var nb_actifs {TRANCHES, y in TYPES} integer >= 0, <= nb_gen [y];

#----------------------------------------------------------------------
# Des variables « superflues » mais qui aident à la lecture

var production {TRANCHES, TYPES} >= 0;	        # en megawatt
var production_majoree {TRANCHES, TYPES} >= 0;	# en megawatt
var frais_demarrage {TRANCHES} >= 0;	        # en euro
var frais_extra_megawatt {TRANCHES} >= 0;	# en euro
var frais_au_min {TRANCHES} >= 0;	        # en euro

#----------------------------------------------------------------------
# Les contraintes 

# normalement = max (0, nb_actifs [t, y] - nb_actifs [prev (t), y])
# mais c'est non linéaire !
# La contrainte suivante est correcte car l'objectif minimise les coûts

subject to contrainte_demarrages {t in TRANCHES, y in TYPES} :
    nb_demarres [t, y] >= nb_actifs [t, y] - nb_actifs [prev (t), y];

# La production doit être supérieure ou égale à la demande
subject to satisfaire_demande {t in TRANCHES} :
    sum {y in TYPES} production [t, y] >= demande [t];

# La production majorée doit être supérieure ou égale à la demande 
# augmentée de la marge de 15%
subject to satisfaire_demande_majoree {t in TRANCHES} :
    sum {y in TYPES} production_majoree [t, y] >= demande [t] * (1 + marge);

# Deux contraintes (le nombre de generateur en activité borne la production)
subject to nb_minimum_en_activite {t in TRANCHES, y in TYPES} :
    production [t, y] >= nb_actifs [t, y] * niv_min [y];

subject to nb_maximum_en_activite {t in TRANCHES, y in TYPES} :
    production [t, y] <= nb_actifs [t, y] * niv_max [y];

# Mêmes contraintes pour la production majorée
subject to nb_minimum_en_activite_majoree {t in TRANCHES, y in TYPES} :
    production_majoree [t, y] >= nb_actifs [t, y] * niv_min [y];

subject to nb_maximum_en_activite_majoree {t in TRANCHES, y in TYPES} :
    production_majoree [t, y] <= nb_actifs [t, y] * niv_max [y];

subject to calcul_frais_demarrage {t in TRANCHES} :
    frais_demarrage [t] = 
	sum {y in TYPES} nb_demarres [t, y] * cout_demarrage [y];

subject to calcul_frais_au_min {t in TRANCHES} :
    frais_au_min [t] =
	sum {y in TYPES} nb_actifs [t, y] * cout_au_min [y] * duree [t];

subject to calcul_frais_extra_megawatt {t in TRANCHES} :
    frais_extra_megawatt [t] =
	sum {y in TYPES} 
	    (production [t, y] - nb_actifs [t, y] * niv_min [y]) * 
					cout_extra_megawatt [y] * duree [t];
#----------------------------------------------------------------------
# L'objectif

minimize tous_les_frais : 
    sum {t in TRANCHES} 
	(frais_demarrage [t] + frais_au_min [t] + frais_extra_megawatt [t]);
