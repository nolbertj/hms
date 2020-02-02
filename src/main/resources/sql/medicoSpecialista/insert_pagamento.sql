/*
    Query per inserire un esame in lista pagamenti. viene fatta solo se l'esame erogato è prescritto da un medico
    -------------------------------------------------------------
    @author Nolbert Juarez <nolbert.juarezvera@studenti.unitn.it>
*/
INSERT INTO pagamento(is_esame, is_ricetta, id_prestazione,data_pagamento,metodo)
VALUES(true,false,?,?,?);