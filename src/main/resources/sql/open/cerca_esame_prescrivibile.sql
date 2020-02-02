/*
    Ritorna una tabella contenente solo i dati necessari per una
    ricerca tramite suggestion box
    -------------------------------------------------------------
    @author Nolbert Juarez <nolbert.juarezvera@studenti.unitn.it>
*/
SELECT e.codice,e.nome,e.prezzo,e.for_sesso,s.nome AS area
FROM esame_specialistico AS e
JOIN specialita AS s ON e.area = s.id
WHERE
    lower(e.codice::text) LIKE ? OR
    lower(e.nome) LIKE ? OR
    lower(s.nome) LIKE ?;