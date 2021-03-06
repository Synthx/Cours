/* lexic.lex */

%{
void afficher_entier(char *);
void afficher_reel(char *);
void afficher_id(char *);
%}

chiffre [0-9]
lettre [a-zA-Z]
separateur [ \t\n]

%%

"+" { printf("Addition\n"); }
"-" { printf("Soustraction\n"); }
":=" { printf("Affectation\n"); }

("+"|"-"){chiffre}+ { afficher_entier(yytext); }
("+"|"-"){chiffre}*"."{chiffre}+ { afficher_reel(yytext); }

{lettre}({lettre}|{chiffre})* { afficher_id(yytext); }

{separateur}+ ;
. { printf("Mauvais caractere : %c\n", yytext[0]); }

%%

void afficher_entier(char *ent) {
   printf("Entier signe reconnu : %d\n", atoi(ent));
}

void afficher_reel(char *reel) {
   printf("Reel signe reconnu : %.2f\n", atof(reel));
}

void afficher_id(char *id) {
   printf("Identificateur : %s\n", id);
}