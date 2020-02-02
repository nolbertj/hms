/*

    ---------------------------------------------------------------------
    @author Alessandro Tomazzolli <a.tomazzolli@studenti.unitn.it>
*/
with origin as(
SELECT ep.id as id_esame,
       es.nome as nome_esame,
       mb.nome || ' ' || mb.cognome as medico_prescrittore,
       ee.data_erogazione as data,
       p.nome || ' ' || p.cognome as paziente,
       ee.anamnesi,
       ee.conclusioni,
       ep.id_paziente
FROM esame_erogato as ee
         JOIN esame_prescritto as ep ON ep.id=ee.id
         JOIN esame_specialistico as es ON es.codice=ep.codice_esame
         JOIN medico_base as mb ON ep.id_prescrivente=mb.id
         JOIN paziente as p ON ep.id_paziente=p.id
WHERE ee.is_medico AND ee.id_erogatore=?)
 select * from origin o where
      lower(o.id_esame::text) LIKE ? OR
       lower(o.nome_esame) LIKE ? OR
       lower(o.data::text) LIKE ? OR
       lower(o.medico_prescrittore) LIKE ? OR
       lower(o.paziente) LIKE ?