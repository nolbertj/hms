SELECT COUNT(E.codice)  
FROM esame_specialistico AS E JOIN specialita AS S ON E.area=S.id
WHERE (E.for_sesso = ? OR E.for_sesso = ?) AND
    lower(E.nome) LIKE ? OR
    lower(S.nome) LIKE ? OR
    lower(descrizione) LIKE ?