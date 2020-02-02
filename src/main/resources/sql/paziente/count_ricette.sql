/*

    ---------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
SELECT COUNT(r.id_ricetta)
FROM ricetta AS r
JOIN medico_base AS m ON m.id = r.id_medico_base
LEFT OUTER JOIN ricetta_erogata AS re ON re.id_ricetta = r.id_ricetta
WHERE r.id_paziente = ? AND lower(m.nome || ' ' || m.cognome) LIKE ?