/*

    ---------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
SELECT COUNT(ricetta.id_ricetta)
FROM ricetta
LEFT OUTER JOIN ricetta_erogata ON ricetta.id_ricetta = ricetta_erogata.id_ricetta
WHERE ricetta.id_paziente = ? AND data_erogazione IS NULL