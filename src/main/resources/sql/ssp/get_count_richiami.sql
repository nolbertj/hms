/*
    Query per ottenere la lista dei richiami
    --------------------------------------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
select count(er.id) from esame_richiamo er
    JOIN esame_specialistico es on er.cod_esame = es.codice
    JOIN specialita s on es.area = s.id
WHERE er.id_ssp=? AND(
    lower(er.id::text) LIKE ? OR
    lower(er.richiesto_il::text) LIKE ? OR
    lower(es.nome) LIKE ? OR
    lower(s.nome) LIKE ?
    );
