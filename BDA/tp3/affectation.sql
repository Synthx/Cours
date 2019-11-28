CREATE OR REPLACE FUNCTION affiche_affectation(text)
RETURNS boolean AS
$$
DECLARE num ALIAS FOR $1; emp record; projet record;
BEGIN
SELECT INTO emp * FROM employe WHERE employe.nemp = num;

IF NOT FOUND THEN
RETURN false;
END IF;

RAISE NOTICE 'employe %', emp.nemp;
RAISE NOTICE '% %', emp.nomemp, emp.prenom;
RAISE NOTICE 'travaillant à %', emp.temps;

FOR projet IN SELECT * FROM affectation a, projet p WHERE a.nemp = emp.nemp AND a.nproj = p.nproj
LOOP
RAISE NOTICE '-> projet %: %', projet.nproj, projet.nomproj;
RAISE NOTICE 'quotite: %', projet.pourcent;
END LOOP;

RETURN true;
END;
$$ LANGUAGE 'plpgsql';
