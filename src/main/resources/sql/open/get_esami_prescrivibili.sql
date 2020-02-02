SELECT E.codice, E.nome, S.nome AS area, E.prezzo AS prezzo, E.descrizione  
FROM esame_specialistico AS E
    JOIN specialita AS S ON E.area=S.id
WHERE (E.for_sesso = ? OR E.for_sesso = ?) AND
    lower(E.nome) LIKE ? OR
    lower(S.nome) LIKE ? OR
    lower(descrizione) LIKE ?