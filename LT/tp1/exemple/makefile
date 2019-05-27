$(NOM) :   lex.yy.c
	   gcc  -o $(NOM) lex.yy.c -ll
lex.yy.c : $(NOM).lex
	   lex $(NOM).lex
