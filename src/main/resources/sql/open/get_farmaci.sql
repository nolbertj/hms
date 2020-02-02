select f.id, f.nome, f.prezzo, f.descrizione from farmaco f  
WHERE lower(f.id::text) LIKE ? OR
    lower(f.nome) LIKE ? OR
    lower(f.prezzo::text) LIKE ? OR
    lower(f.descrizione) LIKE ?