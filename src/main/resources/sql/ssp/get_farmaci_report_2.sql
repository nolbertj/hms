/*
    Query per ottenere i farmaci delle ricette erogate in un determinato giorno
    Questa query è necessaria per creare il report in formato xlsx
    --------------------------------------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
Select rf.id_farmaco, far.nome, far.prezzo from ricetta_farmaco rf
    JOIN farmaco far ON far.id=rf.id_farmaco
WHERE rf.id_ricetta=?