ACCENT=/home/rtaniel/Cours/LT/Compil/accent/accent/accent
ENTIRE=/home/rtaniel/Cours/LT/Compil/accent/entire/entire.c
LEX=lex
CC=gcc

$(NOM) :	yygrammar.c lex.yy.c auxil.c
		$(CC) -o $(NOM) yygrammar.c lex.yy.c auxil.c $(ENTIRE) -Wno-implicit-function-declaration -Wno-implicit-int

lex.yy.c :      $(NOM).lex
		$(LEX) $(NOM).lex

yygrammar.c :   $(NOM).acc
		$(ACCENT) $(NOM).acc
