/*
    Query per erogare una ricetta farmaceutica
    -------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
INSERT INTO ricetta_erogata (
    id_ricetta, id_farmacia, data_erogazione)
VALUES (?,?,?);
INSERT INTO pagamento(is_esame, is_ricetta, id_prestazione) VALUES(false,true,?);