/*

    -------------------------------------------------------------
    @author Nolbert Juarez <nolbert.juarezvera@studenti.unitn.it>
*/
INSERT INTO appuntamento (
    id_medico,
    is_visita_base,
    is_visita_specialistica,
    timestamp_start,
    timestamp_end
)
VALUES(?,true,false,?,?)