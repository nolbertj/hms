/*
    Query per cancellare una ricetta se non si è riusciti a inserire i farmaci
    -------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
DELETE FROM ricetta WHERE id_ricetta=?;