CREATE OR REPLACE FUNCTION verif_num_employe()
RETURNS TRIGGER AS
$$
DECLARE projet record; num_employe integer;
BEGIN
SELECT INTO projet * FROM Projet WHERE nproj = new.nproj;
SELECT INTO num_employe COUNT(*) FROM affectation WHERE nproj = new.nproj;

IF num_employe > projet.nbempmax THEN
RAISE EXCEPTION 'nombre max d''employe sur le projet atteint (max %)', projet.nbempmax;
END IF;

RETURN new;
END;
$$ LANGUAGE 'plpgsql';

DROP TRIGGER verif_num_employe ON affectation;

CREATE TRIGGER verif_num_employe
AFTER UPDATE OR INSERT
ON affectation FOR EACH ROW
EXECUTE PROCEDURE verif_num_employe();
