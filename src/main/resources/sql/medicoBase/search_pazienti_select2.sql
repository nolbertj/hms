SELECT p.id, p.nome, p.cognome, p.codice_fiscale, p.sesso  
FROM paziente_medico AS pm
    JOIN paziente AS p ON pm.id_paziente=p.id
WHERE pm.al IS NULL AND id_medico_base = ? AND (
    lower(p.id::text) LIKE ? OR
    lower(p.nome) LIKE ? OR
    lower(p.cognome) LIKE ? OR
     lower (p.cognome || ' ' || p.nome) LIKE ? OR
     lower (p.nome || ' ' || p.cognome) LIKE ? OR
    lower(p.sesso) LIKE ? OR
    lower(p.codice_fiscale) LIKE ?)