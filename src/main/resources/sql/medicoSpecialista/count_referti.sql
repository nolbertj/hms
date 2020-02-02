/*

    ---------------------------------------------------------------------
    @author Alessandro Tomazzolli <a.tomazzolli@studenti.unitn.it>
*/
SELECT COUNT(ep.id)
FROM esame_erogato as ee
         JOIN esame_prescritto as ep ON ep.id=ee.id
         JOIN esame_specialistico as es ON es.codice=ep.codice_esame
         JOIN medico_base as mb ON ep.id_prescrivente=mb.id
         JOIN paziente as p ON ep.id_paziente=p.id
WHERE ee.is_medico AND ee.id_erogatore=? AND
      (lower(ep.id::text) LIKE ? OR
        lower(es.nome) LIKE ? OR
        lower(ee.data_erogazione::text) LIKE ? OR
        lower(mb.nome || ' ' || mb.cognome) LIKE ? OR
        lower(p.nome || ' ' || p.cognome) LIKE ?);