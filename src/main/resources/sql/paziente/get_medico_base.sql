/*

    ---------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
SELECT mb.nome,
       mb.cognome,
       mb.sesso,
       utente.avatar_filename,
       a.denominazione as ambulatorio,
       a.indirizzo,
       c.nome as citta,
       p.nome as provincia,
       a.contatto_telefonico,
       mb.email
FROM paziente_medico AS pm
JOIN medico_base mb ON pm.id_medico_base = mb.id
JOIN utente ON mb.email = utente.email
JOIN ambulatorio AS a ON mb.ambulatorio = a.id
JOIN citta AS c ON a.citta = c.id
JOIN provincia AS p ON c.provincia = p.id
WHERE pm.id_paziente = ? AND pm.al is NULL