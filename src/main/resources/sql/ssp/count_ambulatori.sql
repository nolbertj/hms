WITH origin1 as (SELECT ssp.provincia as prov_ssp FROM servizio_sanitario ssp WHERE ssp.id=?)
SELECT COUNT(a.id) AS counter
         FROM ambulatorio a
         JOIN citta c ON c.id=a.citta
         JOIN provincia pr ON pr.id=c.provincia
         JOIN origin1 o1 ON pr.id=o1.prov_ssp
where lower(a.denominazione ) LIKE ? OR
        lower (a.indirizzo) LIKE ? OR
        lower (c.nome) LIKE ? OR
        lower (pr.nome) LIKE ?