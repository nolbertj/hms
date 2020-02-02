with origin as (select id from paziente where email=?)
select * from paziente_medico pm join origin o on pm.id_paziente=o.id where pm.id_medico_base=? and pm.al is null