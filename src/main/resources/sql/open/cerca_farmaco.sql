/*
    Ritorna una tabella contenente solo i dati necessari per una
    ricerca tramite suggestion box del farmaco
    -------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
SELECT f.id,f.nome,f.descrizione
FROM farmaco f
WHERE
    lower(f.nome::text) LIKE ? OR
    lower(f.descrizione) LIKE ?;