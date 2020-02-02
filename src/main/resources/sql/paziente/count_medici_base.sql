/*

    ---------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
WITH
origin1 AS
    (
        SELECT c2.provincia as prov_res
        FROM paziente AS p
        JOIN citta AS c2 ON p.residenza = c2.id
        WHERE p.id = ?
    ),
origin2 AS
    (
        SELECT
               mb1.id,
               mb1.nome,
               mb1.cognome,
               mb1.data_nascita,
               mb1.sesso,
               a1.denominazione as nome_ambulatorio,
               a1.indirizzo as indirizzo_medico,
               a1.contatto_telefonico
        FROM medico_base AS mb1
        JOIN paziente_medico AS pm ON pm.id_medico_base = mb1.id
        JOIN paziente AS p ON p.id = pm.id_paziente
        JOIN ambulatorio AS a1 ON mb1.ambulatorio = a1.id
        WHERE
              p.id=? AND pm.al IS NULL
    ),
origin3 AS
    (
        SELECT
               mb.id,
               mb.nome,
               mb.cognome,
               mb.data_nascita,
               mb.sesso,
               a.denominazione as nome_ambulatorio,
               a.indirizzo as indirizzo_medico,
               a.contatto_telefonico
        FROM medico_base AS mb
        JOIN ambulatorio AS a ON mb.ambulatorio = a.id
        JOIN citta AS c1 ON a.citta = c1.id
        JOIN origin1 ON c1.provincia = origin1.prov_res
        EXCEPT SELECT * FROM origin2
    )
------------------------------------------------------------------------
SELECT count(origin3.id) FROM origin3
WHERE
    lower(origin3.nome) LIKE ? OR
    lower(origin3.cognome) LIKE ? OR
    lower(origin3.data_nascita::text) LIKE ? OR
    lower(origin3.sesso::text) LIKE ? OR
    lower(origin3.nome_ambulatorio) LIKE ? OR
    lower(origin3.indirizzo_medico) LIKE ?