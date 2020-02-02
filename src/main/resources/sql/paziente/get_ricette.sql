/*

    ---------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
WITH origin AS (
   SELECT rf.id_ricetta, count(rf.id_farmaco) as nr_farmaci
   FROM ricetta_farmaco AS rf
   GROUP BY rf.id_ricetta
)
SELECT r.id_ricetta,
       r.data_prescrizione,
       m.nome || ' ' || m.cognome AS medico_prescrivente,
       o.nr_farmaci,
       re.data_erogazione,
       m.sesso as sesso_medico
FROM ricetta AS r
JOIN medico_base AS m ON m.id = r.id_medico_base
JOIN origin AS o ON r.id_ricetta = o.id_ricetta
LEFT OUTER JOIN ricetta_erogata AS re ON re.id_ricetta = r.id_ricetta
WHERE r.id_paziente = ? AND lower(m.nome || ' ' || m.cognome) LIKE ?