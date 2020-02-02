SELECT id, nome, cognome, codice_fiscale  
FROM paziente
WHERE lower(nome) LIKE ?
    OR lower(cognome) LIKE ?
    OR lower(nome || ' ' || cognome) LIKE ?
    OR lower(cognome || ' ' || nome) LIKE ?
    OR lower(codice_fiscale) LIKE ?
ORDER BY nome