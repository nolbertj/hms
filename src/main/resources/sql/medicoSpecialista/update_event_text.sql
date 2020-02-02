/*

    -------------------------------------------------------------
    @author Nolbert Juarez <nolbert.juarezvera@studenti.unitn.it>
*/
UPDATE appuntamento
SET motivo = ?
WHERE id = ? AND id_medico = ? AND is_visita_specialistica