/*

    ---------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
SELECT ee.id,
       p.nome || ' ' || p.cognome as n_paziente,
       ee.data_erogazione,
       es.nome as nome_esame,
       s.nome as medico_prescrivente,
       ms.nome || ' ' || ms.cognome as medico_esecutore,
       ee.anamnesi,
       ee.conclusioni
FROM esame_erogato AS ee
JOIN esame_prescritto AS ep ON ee.id = ep.id
JOIN esame_specialistico AS es ON es.codice = ep.codice_esame
JOIN paziente AS p ON p.id = ep.id_paziente
JOIN servizio_sanitario AS s ON s.id = ep.id_prescrivente
JOIN medico_specialista AS ms ON ms.id = ee.id_erogatore
WHERE ep.is_ssp IS TRUE AND ee.id = ? AND p.id = ? and ee.is_medico
union (
    SELECT ee.id,
           p.nome || ' ' || p.cognome as n_paziente,
           ee.data_erogazione,
           es.nome as nome_esame,
           s.nome as medico_prescrivente,
           ssp.nome as medico_esecutore,
           ee.anamnesi,
           ee.conclusioni
    FROM esame_erogato AS ee
             JOIN esame_prescritto AS ep ON ee.id = ep.id
             JOIN esame_specialistico AS es ON es.codice = ep.codice_esame
             JOIN paziente AS p ON p.id = ep.id_paziente
             JOIN servizio_sanitario AS s ON s.id = ep.id_prescrivente
             JOIN servizio_sanitario AS ssp ON ssp.id = ee.id_erogatore
    WHERE ep.is_ssp IS TRUE AND ee.id = ? AND p.id = ? and ee.is_ssp
)