/*
    Query per capire se un esame erogato è stato richiesto da ssp con richiamo o meno. Questo
    farà capire se bisogna inserire la voce in lista pagamenti
    -------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
select ep.is_ssp from esame_prescritto ep
where ep.id=? and ep.id_paziente=?