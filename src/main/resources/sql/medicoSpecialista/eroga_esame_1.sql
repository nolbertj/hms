/*
    Query per erogare un esame/generare/inserire un referto
    -------------------------------------------------------------
    @author Nolbert Juarez <nolbert.juarezvera@studenti.unitn.it>
*/
INSERT INTO esame_erogato (
    id,id_erogatore,data_erogazione,anamnesi,conclusioni,is_medico,is_ssp)
VALUES (?,?,?,?,?,?,?);