/*
    Query per prescrivere un esame
    -------------------------------------------------------------
    @author Nolbert Juarez <nolbert.juarezvera@studenti.unitn.it>
*/
INSERT INTO esame_prescritto (
    id_prescrivente, id_paziente, annotazioni,
    codice_esame, is_medico, data_prescrizione)
VALUES (?,?,?,?,?,?);