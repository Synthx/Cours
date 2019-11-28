CREATE OR REPLACE FUNCTION verif_quotite_employe()
RETURNS TRIGGER AS
$$
DECLARE emp record; total integer;
BEGIN
SELECT INTO emp * FROM employe WHERE nemp = new.nemp;
SELECT INTO total SUM(pourcent) FROM affectation WHERE nemp = new.nemp;

IF total > emp.temps THEN
RAISE EXCEPTION 'quotite pour l''employe depasse (max %)', emp.temps;
END IF;

RETURN new;
END;
$$ LANGUAGE 'plpgsql';

DROP TRIGGER verif_quotite_employe ON affectation;

CREATE TRIGGER verif_quotite_employe
AFTER UPDATE OR INSERT
ON affectation FOR EACH ROW
EXECUTE PROCEDURE verif_quotite_employe();
