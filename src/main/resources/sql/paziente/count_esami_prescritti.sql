/*

    ---------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
WITH esami as (
    (
        ----- esami prescritti dal medico di base ------
        SELECT
           ep.id,
           ep.codice_esame,
           e.nome as nome_esame,
           ep.data_prescrizione as data_prescrizione,
           m.nome || ' ' || m.cognome as nome_medico_prescrittore,
           ee.data_erogazione as data_erogazione
        FROM esame_prescritto AS ep
            JOIN paziente AS p ON p.id = ep.id_paziente
            JOIN medico_base AS m ON ep.id_prescrivente = m.id
            JOIN esame_specialistico AS e ON e.codice = ep.codice_esame
            LEFT OUTER JOIN esame_erogato AS ee ON ee.id = ep.id
        WHERE
            ep.is_medico IS TRUE AND p.id = ?
        -----------------------------------------------
    )
    UNION
    (
        --- esami prescritti dal servizio sanitario ---
        SELECT
           ep.id,
           ep.codice_esame,
           e.nome as nome_esame,
           ep.data_prescrizione as data_prescrizione,
           s.nome as nome_medico_prescrittore,
           ee.data_erogazione as data_erogazione
        FROM esame_prescritto AS ep
            JOIN paziente AS p ON p.id = ep.id_paziente
            JOIN servizio_sanitario AS s ON ep.id_prescrivente = s.id
            JOIN esame_specialistico AS e ON e.codice = ep.codice_esame
            LEFT OUTER JOIN esame_erogato AS ee ON ee.id = ep.id
            WHERE
                  ep.is_ssp IS TRUE AND p.id = ?
        ----------------------------------------------
    )
)
------------------------------------------------------------------------------------------------------
SELECT count(esami.id) FROM esami
WHERE
    lower(esami.id::text) LIKE ? OR
    lower(esami.codice_esame::text) LIKE ? OR
    lower(esami.nome_esame) LIKE ? OR
    lower(esami.data_prescrizione::text) LIKE ? OR
    lower(esami.data_erogazione::text) LIKE ? ;