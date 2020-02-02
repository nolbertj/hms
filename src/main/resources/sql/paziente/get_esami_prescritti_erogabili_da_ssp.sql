/*
    Query per ottenere gli esami prescritti al paziente, che sono erogabili dal SSP
    Presenta anche la possibilità di cercare all'interno di tutte le colonne della tabella risultante.
    --------------------------------------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/

--- nome esame, nome area, idPrestazione, codice prestazione, prescrivente, data prescrizione ---
WITH esami AS (
    (
        ----- esami prescritti dal medico di base ------
        SELECT
            ep.id,
            ep.codice_esame,
            e.nome AS nome_esame,
            e.area as codice_area,
            spec.nome AS area,
            ep.data_prescrizione AS data_prescrizione,
            m.nome || ' ' || m.cognome AS nome_medico_prescrittore,
            ee.data_erogazione AS data_erogazione
        FROM esame_prescritto AS ep
            JOIN paziente AS p ON p.id = ep.id_paziente
            JOIN medico_base AS m ON ep.id_prescrivente = m.id
            JOIN esame_specialistico AS e ON e.codice = ep.codice_esame
            JOIN specialita AS spec ON spec.id = e.area
            LEFT OUTER JOIN esame_erogato AS ee ON ee.id = ep.id
        WHERE
            ep.is_medico AND p.id = ?
        -----------------------------------------------
    )
    UNION
    (
        --- esami prescritti dal servizio sanitario ---
        SELECT
            ep.id,
            ep.codice_esame,
            e.nome AS nome_esame,
            e.area as codice_area,
            spec.nome AS area,
            ep.data_prescrizione AS data_prescrizione,
            s.nome AS nome_medico_prescrittore,
            ee.data_erogazione AS data_erogazione
        FROM esame_prescritto AS ep
            JOIN paziente AS p ON p.id = ep.id_paziente
            JOIN servizio_sanitario AS s ON ep.id_prescrivente = s.id
            JOIN esame_specialistico AS e ON e.codice = ep.codice_esame
            JOIN specialita AS spec ON spec.id = e.area
            LEFT OUTER JOIN esame_erogato AS ee ON ee.id = ep.id
        WHERE
            ep.is_ssp AND p.id = ?
        ----------------------------------------------
    )
)
------------------------------------------------------------------------------------------------------
SELECT * FROM esami
WHERE
    (codice_area = 4 OR codice_area =10 OR codice_area=11 OR codice_area=12 OR codice_area=13
        OR codice_area=14 OR codice_area=15 OR codice_area=16 OR codice_area=20 OR codice_area=22) AND
    (lower(esami.id::text) LIKE ? OR
    lower(esami.codice_esame::text) LIKE ? OR
    lower(esami.data_prescrizione::text) LIKE ? OR
    lower(esami.data_erogazione::text) LIKE ? OR
    lower(esami.nome_esame) LIKE ? OR
    lower(esami.nome_medico_prescrittore) LIKE ? OR
    lower(esami.area) LIKE ?)