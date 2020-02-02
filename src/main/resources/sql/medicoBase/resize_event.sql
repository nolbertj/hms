/*

    -------------------------------------------------------------
    @author Nolbert Juarez <nolbert.juarezvera@studenti.unitn.it>
*/
UPDATE appuntamento
SET timestamp_start = ?, timestamp_end = ?
WHERE id = ? AND id_medico = ? AND is_visita_base