/*

    ---------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
SELECT
    p.*,
    c2.nome AS citta_res,
    c1.nome AS citta_l,
    c2.n_provincia as prov ,
    u.avatar_filename as avatar_path
FROM paziente AS p
    JOIN utente AS u ON u.email = p.email
    JOIN citta AS c1 ON p.luogo_nascita=c1.id
    JOIN (
        SELECT c.*, p.nome AS n_provincia
        FROM citta c
        JOIN provincia AS p ON c.provincia = p.id
    ) AS c2 ON p.residenza = c2.id
WHERE p.id = ?