WITH origin1 as (SELECT c2.provincia as prov_res FROM paziente p JOIN citta c2 ON p.residenza=c2.id WHERE p.id=?)
SELECT COUNT(a.id) AS counter
         FROM ambulatorio a
         JOIN citta c ON c.id=a.citta
         JOIN provincia pr ON pr.id=c.provincia
         JOIN origin1 o1 ON pr.id=o1.prov_res
where lower(a.denominazione ) LIKE ? OR
        lower (a.indirizzo) LIKE ? OR
        lower (c.nome) LIKE ? OR
        lower (pr.nome) LIKE ?