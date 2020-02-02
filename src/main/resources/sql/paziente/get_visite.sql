/*
    Query per ottenere le visite (esami e ricette prescritte) del paziente
    NOTA: colonna 'tipo':
        E = esame
        R = ricetta
    ---------------------------------------------------------------------------------
    @author Nolbert Juarez <nolbert.juarezvera@studenti.unitn.it>
*/
WITH visite AS (
    (
        ----- esami prescritti dal medico di base ------
        SELECT 'E' AS tipo,
               ep.id                      AS idPrescrizione,
               m.nome || ' ' || m.cognome AS prescrivente,
               ep.data_prescrizione       AS data_prescrizione,
               ee.data_erogazione         AS data_erogazione,
               es.nome                    AS descrizione
        FROM esame_prescritto AS ep
                 JOIN esame_specialistico AS es ON es.codice = ep.codice_esame
                 JOIN paziente AS p ON p.id = ep.id_paziente
                 JOIN medico_base AS m ON ep.id_prescrivente = m.id
                 LEFT OUTER JOIN esame_erogato AS ee ON ee.id = ep.id
        WHERE ep.is_medico AND p.id = ?
        -----------------------------------------------
    )
    UNION
    (
        --- esami prescritti dal servizio sanitario ---
        SELECT 'E' AS tipo,
               ep.id                AS idPrescrizione,
               s.nome               AS prescrivente,
               ep.data_prescrizione AS data_prescrizione,
               ee.data_erogazione   AS data_erogazione,
               es.nome              AS descrizione
        FROM esame_prescritto AS ep
                 JOIN esame_specialistico AS es ON es.codice = ep.codice_esame
                 JOIN paziente AS p ON p.id = ep.id_paziente
                 JOIN servizio_sanitario AS s ON ep.id_prescrivente = s.id
                 LEFT OUTER JOIN esame_erogato AS ee ON ee.id = ep.id
        WHERE ep.is_ssp AND p.id = ?
        ----------------------------------------------
    )
    UNION
    (
        --- ricette ---
        SELECT 'R' AS tipo,
               r.id_ricetta               AS idPrescrizione,
               m.nome || ' ' || m.cognome AS prescrivente,
               r.data_prescrizione,
               re.data_erogazione,
               CASE
                   WHEN r.descrizione IS NULL THEN ''
                   ELSE ''
               END AS descrizione
        FROM ricetta AS r
                 JOIN medico_base AS m ON m.id = r.id_medico_base
                 LEFT OUTER JOIN ricetta_erogata AS re ON re.id_ricetta = r.id_ricetta
        WHERE r.id_paziente = ?
        ----------------------------------------------
    )
)
SELECT *, COUNT(*) OVER() counter
FROM visite AS v
WHERE
    lower(v.data_prescrizione::text) LIKE ? OR
    lower(v.data_erogazione::text) LIKE ? OR
    lower(v.prescrivente) LIKE ?
