/*
    Prima Query per prescrivere una ricetta
    -------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
INSERT INTO ricetta (
    id_paziente, id_medico_base, descrizione, data_prescrizione)
VALUES (?,?,?,?) RETURNING id_ricetta;