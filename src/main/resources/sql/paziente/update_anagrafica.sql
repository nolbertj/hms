/*

    ---------------------------------------------------------------------
    @author Nolbert Juarez <nolbert.juarezvera@studenti.unitn.it>
*/
UPDATE paziente
SET contatto_emergenza = ?,
    contatto_riferimento = ?
WHERE id = ?