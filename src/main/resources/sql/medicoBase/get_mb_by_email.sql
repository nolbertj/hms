select mb.*, a.denominazione as nome_ambulatorio, a.indirizzo as indirizzo_amb, a.contatto_telefonico as tel_amb,
       c1.nome as nome_citta, c2.nome as citta_nascita, p2.nome as provincia_nascita, p.nome as nome_provincia,
       ssp.nome as servizio_sanitario, u.ruolo, u.avatar_filename as avatar_path
from medico_base mb
    JOIN ambulatorio a ON a.id=mb.ambulatorio
    JOIN citta c1 ON c1.id=a.citta
    JOIN citta c2 ON c2.id=mb.luogo_nascita
    JOIN provincia p ON p.id=c1.provincia
    JOIN provincia p2 ON c2.provincia=p2.id
    JOIN servizio_sanitario ssp ON p.id=ssp.provincia
    JOIN utente u ON u.email=mb.email
WHERE mb.email=?