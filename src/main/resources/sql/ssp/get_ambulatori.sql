WITH origin1 as (SELECT ssp.provincia as prov_ssp FROM servizio_sanitario ssp WHERE ssp.id=?)
SELECT a.id as id, a.denominazione as nome, a.indirizzo AS indirizzo, c.nome AS citta, pr.nome as provincia, a.contatto_telefonico AS contatto_principale
	FROM ambulatorio a
	JOIN citta c ON c.id=a.citta
    JOIN provincia pr ON pr.id=c.provincia
    JOIN origin1 o1 ON pr.id=o1.prov_ssp
where lower(a.denominazione ) LIKE ? OR
      lower (a.indirizzo) LIKE ? OR
      lower (c.nome) LIKE ?
