/*

    ---------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/

SELECT p.*, c2.nome AS citta_res, c1.nome AS citta_l, c2.n_provincia as prov ,  u.avatar_filename as avatar_path, u.ruolo
FROM paziente p
    JOIN utente as u on u.email = p.email
    JOIN citta c1 ON p.luogo_nascita=c1.id
    JOIN (SELECT c.*, p.nome as n_provincia
      FROM citta c
      JOIN provincia p ON c.provincia=p.id) AS c2 ON p.residenza = c2.id
WHERE p.email = ?