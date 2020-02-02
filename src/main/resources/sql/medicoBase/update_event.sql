/*

    -------------------------------------------------------------
    @author Nolbert Juarez <nolbert.juarezvera@studenti.unitn.it>
*/
UPDATE appuntamento
SET motivo = ?, id_paziente = ?
WHERE id = ? AND id_medico = ? AND is_visita_base