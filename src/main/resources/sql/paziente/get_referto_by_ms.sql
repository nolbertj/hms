/*

    ---------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
SELECT ee.id,
       p.nome || ' ' || p.cognome as n_paziente,
       es.nome as nome_esame,
       ee.data_erogazione,
       m.nome || ' ' || m.cognome as medico_prescrivente,
       ms.nome || ' ' || ms.cognome as medico_esecutore,
       ee.anamnesi,
       ee.conclusioni
FROM esame_erogato AS ee
JOIN esame_prescritto AS ep ON ee.id = ep.id
JOIN esame_specialistico es ON es.codice = ep.codice_esame
JOIN paziente AS p ON p.id = ep.id_paziente
JOIN medico_base AS m ON m.id = ep.id_prescrivente
JOIN medico_specialista AS ms ON ms.id = ee.id_erogatore
WHERE ep.is_medico AND ee.id = ? AND p.id = ? AND ee.is_medico
union(
    SELECT ee.id,
           p.nome || ' ' || p.cognome as n_paziente,
           es.nome as nome_esame,
           ee.data_erogazione,
           m.nome || ' ' || m.cognome as medico_prescrivente,
           ssp.nome as medico_esecutore,
           ee.anamnesi,
           ee.conclusioni
    FROM esame_erogato AS ee
             JOIN esame_prescritto AS ep ON ee.id = ep.id
             JOIN esame_specialistico es ON es.codice = ep.codice_esame
             JOIN paziente AS p ON p.id = ep.id_paziente
             JOIN medico_base AS m ON m.id = ep.id_prescrivente
             JOIN servizio_sanitario AS ssp ON ssp.id = ee.id_erogatore
    WHERE ep.is_medico AND ee.id = ? AND p.id = ? and ee.is_ssp
)