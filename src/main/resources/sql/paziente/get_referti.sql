/*

    ---------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
WITH origin AS (
    (
        (SELECT ee.id,
               es.nome as nome_esame,
               m.nome || ' ' || m.cognome as medico_prescrivente,
               ee.data_erogazione,
               ms.nome || ' ' ||  ms.cognome as medico_esecutore,
               ee.anamnesi,
               ee.conclusioni
        FROM esame_erogato AS ee
        JOIN esame_prescritto AS ep ON ee.id = ep.id
        JOIN esame_specialistico AS es ON es.codice = ep.codice_esame
        JOIN paziente AS p ON p.id = ep.id_paziente
        JOIN medico_base AS m ON m.id = ep.id_prescrivente
        JOIN medico_specialista AS ms ON ms.id = ee.id_erogatore
        WHERE ep.is_medico IS TRUE AND p.id = ? and ee.is_medico)
        Union (
            SELECT ee.id,
                   es.nome as nome_esame,
                   m.nome || ' ' || m.cognome as medico_prescrivente,
                   ee.data_erogazione,
                   ssp.nome as medico_esecutore,
                   ee.anamnesi,
                   ee.conclusioni
            FROM esame_erogato AS ee
                     JOIN esame_prescritto AS ep ON ee.id = ep.id
                     JOIN esame_specialistico AS es ON es.codice = ep.codice_esame
                     JOIN paziente AS p ON p.id = ep.id_paziente
                     JOIN medico_base AS m ON m.id = ep.id_prescrivente
                     JOIN servizio_sanitario AS ssp ON ssp.id = ee.id_erogatore
            WHERE ep.is_medico IS TRUE AND p.id = ? and ee.is_ssp
        )
    )
    UNION
    (
        (SELECT ee.id,
               es.nome as nome_esame,
               s.nome as medico_prescrivente,
               ee.data_erogazione,
               ms.nome || ' ' || ms.cognome as medico_esecutore,
               ee.anamnesi,
               ee.conclusioni
        FROM esame_erogato AS ee
        JOIN esame_prescritto AS ep ON ee.id = ep.id
        JOIN esame_specialistico AS es ON es.codice = ep.codice_esame
        JOIN paziente AS p ON p.id = ep.id_paziente
        JOIN servizio_sanitario AS s ON s.id = ep.id_prescrivente
        JOIN medico_specialista AS ms ON ms.id = ee.id_erogatore
        where ep.is_ssp IS TRUE AND p.id = ? and ee.is_medico)
        union(
            (SELECT ee.id,
                    es.nome as nome_esame,
                    s.nome as medico_prescrivente,
                    ee.data_erogazione,
                    ssp.nome as medico_esecutore,
                    ee.anamnesi,
                    ee.conclusioni
             FROM esame_erogato AS ee
                      JOIN esame_prescritto AS ep ON ee.id = ep.id
                      JOIN esame_specialistico AS es ON es.codice = ep.codice_esame
                      JOIN paziente AS p ON p.id = ep.id_paziente
                      JOIN servizio_sanitario AS s ON s.id = ep.id_prescrivente
                      JOIN servizio_sanitario ssp ON ssp.id = ee.id_erogatore
             where ep.is_ssp IS TRUE AND p.id = ? and ee.is_ssp)
        )

    )

)
---------------------------------------------------------------------
SELECT * FROM origin
WHERE
    lower(origin.id::text) LIKE ? OR
    lower(origin.nome_esame) LIKE ? OR
    lower(origin.medico_prescrivente::text) LIKE ? OR
    lower(origin.medico_esecutore::text) LIKE ? OR
    lower(origin.data_erogazione::text) LIKE ?