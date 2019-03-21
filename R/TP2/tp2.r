### Analyse en composantes principales

### QUESTION 1

## Chargement de la base de donnée
```{r}
ocde <- read.table("ocde.txt", header=TRUE, sep = " ")
```


# Peut etre analyse bivarié ??


# Question 2
#install.packages("FactoMineR")
#install.packages("factoextra")

library(FactoMineR)
library(factoextra)

rownames(data) <- data$PAYS
PCA(data[,-1], scale.unit = FALSE)

# PIB non réduit donc il "explose" toutes les autres valeurs
# graphe non exploitable en l'état

# Question 3
# 99.15% pour la variance du premier axe principal

# Question 4
pca <- PCA(data[,-1], scale.unit = TRUE)
# install.packages("ade4")
library(ade4)

# Analyse selon l'axe 1 (Horizontal)
# PIB, NRJ, PROT correle entre eux,
# decorelle par rapport à INFL, APRI, MINF, NATA

# Analyse selon l'axe 2 (Vertical)
# ASEC non correle CHOM

par()
fviz_eig(dudi.pca(data[,-1], scale = TRUE, scan = FALSE, nf = 2), addlabels = TRUE)

prcomp(data[,-1])

# Question 5
# On garde 3 axes :
# Coudes situés au niveau 3
# repart > 80% avec les 3 premiers axes

