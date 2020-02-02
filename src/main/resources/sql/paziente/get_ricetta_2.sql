SELECT f.id as codice, f.nome, f.descrizione, count(id_farmaco) as quantita, f.prezzo, count(id_farmaco)*f.prezzo as totale, f.note  
FROM ricetta_farmaco as rf
    JOIN ricetta on rf.id_ricetta = ricetta.id_ricetta
    JOIN farmaco as f on rf.id_farmaco=f.id
WHERE rf.id_ricetta=? AND ricetta.id_paziente=?
GROUP BY f.id,rf.id_ricetta, rf.id_farmaco