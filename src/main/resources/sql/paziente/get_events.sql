/*
    Ritorna tutti gli appuntamenti che il paziente potrà vedere.
    Di conseguenza, verranno inviate alcuni attributi dei medici
    -------------------------------------------------------------
    @author Nolbert Juarez <nolbert.juarezvera@studenti.unitn.it>
*/
-----------------------------------------------------------
----------- appuntamenti con medici specialisti -----------
SELECT a.*,
       ms.nome || ' ' || ms.cognome as nome_medico,
       ms.sesso as sesso_medico
FROM appuntamento AS a
JOIN medico_specialista AS ms ON ms.id = a.id_medico
WHERE NOT ((timestamp_end <= ?) OR (timestamp_start >= ?))
    AND is_visita_specialistica IS TRUE
    AND id_paziente = ?
-----------------------------------------------------------
UNION
----------- appuntamenti con medico di base  --------------
SELECT a.*,
       mb.nome || ' ' || mb.cognome as nome_medico,
       mb.sesso as sesso_medico
FROM appuntamento AS a
JOIN medico_base AS mb ON mb.id = a.id_medico
WHERE NOT ((timestamp_end <= ?) OR (timestamp_start >= ?))
    AND is_visita_base IS TRUE
    AND id_paziente = ?