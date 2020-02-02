/*

    ---------------------------------------------------------------------
    @author 
*/

SELECT u.email, u.ruolo, u.avatar_filename, f.id,f.nome, f.partita_iva, c.citta, c.provincia, f.numero_telefono  
FROM farmacia AS f
    JOIN utente AS u ON u.email = f.email
    JOIN (
        SELECT c.id, c.nome as citta, p.nome AS provincia
        FROM citta AS c
        JOIN provincia AS p ON c.provincia = p.id
    ) AS c ON f.citta = c.id
WHERE f.email = ?