/*

    ---------------------------------------------------------------------
    @author Nolbert Juarez <nolbert.juarezvera@studenti.unitn.it>
*/
INSERT INTO foto_paziente(id_paziente, filename, data) VALUES (?,?,?);
UPDATE utente
SET avatar_filename = ?
WHERE email IN (
   SELECT utente.email
   FROM utente
   JOIN paziente ON utente.email = paziente.email
   WHERE paziente.id = ?
);