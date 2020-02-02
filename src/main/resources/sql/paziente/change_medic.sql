/*
    ---------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
UPDATE paziente_medico SET al=? WHERE id_paziente=?;
INSERT INTO paziente_medico(id_paziente, id_medico_base, dal) VALUES (?,?,?);