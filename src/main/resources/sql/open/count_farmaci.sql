SELECT count(f.id) as conteggio_farmaci  
FROM farmaco f
WHERE lower(f.id::text) LIKE ? OR
    lower(f.nome) LIKE ? OR
    lower(f.prezzo::text) LIKE ? OR
    lower(f.descrizione) LIKE ?