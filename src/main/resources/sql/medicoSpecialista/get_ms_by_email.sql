SELECT ms.id, ms.email, ms.nome, ms.cognome, ms.data_nascita, ms.codice_fiscale,  
      ms.prezzo_visita, ms.sesso, u.ruolo, u.avatar_filename, c.nome AS luogo_nascita,
      p.nome AS provincia, s.nome AS specialita
FROM medico_specialista AS ms
    JOIN utente AS u ON u.email = ms.email
    JOIN citta AS c ON c.id = ms.luogo_nascita
    JOIN provincia p ON c.provincia = p.id
    JOIN specialita s ON ms.specialita = s.id
WHERE u.email = ?