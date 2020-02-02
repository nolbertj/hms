with origin as (select mb.id as this_medico from medico_base mb WHERE mb.id=?), 
    origin2 as (select pm.id_paziente from paziente_medico pm JOIN origin o on o.this_medico=pm.id_medico_base WHERE pm.al is null), 
    origin3 as(select r.id_paziente, max(r.data_prescrizione) as ultima_ricetta from ricetta r group by r.id_paziente), 
    origin4 as (select ep.id_paziente, max(ep.data_prescrizione) as ultimo_esame from esame_prescritto ep group by ep.id_paziente) 
select p.id, p.nome, p.cognome, p.codice_fiscale, o4.ultimo_esame, o3.ultima_ricetta from paziente p
    join origin2 o2 on o2.id_paziente=p.id 
    LEFT OUTER JOIN origin3 o3 on o3.id_paziente=p.id 
    LEFT OUTER JOIN origin4 o4 on o4.id_paziente=p.id  
where lower(p.nome) LIKE ? OR
    lower(p.cognome) LIKE ? OR  
    lower (p.cognome || ' ' || p.nome) LIKE ? OR  
    lower (p.nome || ' ' || p.cognome) LIKE ? OR  
    lower(p.codice_fiscale) LIKE ? OR  
    lower(o4.ultimo_esame::text) LIKE ? OR  
    lower(o3.ultima_ricetta::text) LIKE ? 