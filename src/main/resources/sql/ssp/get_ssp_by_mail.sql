/*

    ---------------------------------------------------------------------
    @author 
*/

WITH origin1 as(SELECT pr.id, count(*) as numero_pazienti from paziente p 
   JOIN citta c ON p.residenza=c.id
   JOIN provincia pr ON c.provincia=pr.id
   GROUP BY pr.id),
origin2 as(SELECT pr.id, count(*) as numero_mb from medico_base mb
   JOIN ambulatorio a ON mb.ambulatorio=a.id
   JOIN citta c ON a.citta=c.id
   JOIN provincia pr ON c.provincia=pr.id
   GROUP BY pr.id),
origin3 as (SELECT pr.id, count(*) as numero_ms from medico_specialista ms
   JOIN medicospec_ambulatorio msa ON ms.id=msa.id_medico_spec
   JOIN ambulatorio a ON msa.id_ambulatorio=a.id
   JOIN citta c ON a.citta=c.id
   JOIN provincia pr ON c.provincia=pr.id
   GROUP BY pr.id)
SELECT ssp.id, ssp.nome, ssp.email, ssp.numero_telefono, ssp.abbreviazione, p.nome as nome_provincia, p.sigla as sigla_provincia, o1.numero_pazienti, o2.numero_mb, o3.numero_ms, u.ruolo, u.avatar_filename as avatar_path from servizio_sanitario ssp
   JOIN provincia p ON ssp.provincia=p.id
   JOIN origin1 o1 ON o1.id=ssp.provincia
   JOIN origin2 o2 ON o2.id=ssp.provincia
   JOIN origin3 o3 ON o3.id=ssp.provincia
   JOIN utente u ON ssp.email=u.email
where ssp.email=?