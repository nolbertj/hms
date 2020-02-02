/*
    Query per ottenere la lista dei richiami
    --------------------------------------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
select er.id as id_richiamo, es.nome as nome_esame, es.codice as codice_esame, s.nome as nome_area, er.richiesto_il, er.eta_inizio, er.eta_fine from esame_richiamo er
    JOIN esame_specialistico es on er.cod_esame = es.codice
    JOIN specialita s on es.area = s.id
WHERE er.id_ssp=? AND(
            lower(er.id::text) LIKE ? OR
            lower(er.richiesto_il::text) LIKE ? OR
            lower(es.nome) LIKE ? OR
            lower(s.nome) LIKE ?
    )
