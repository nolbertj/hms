/*
    Ritorna tutti gli appuntamenti del medico di base con anche i
    principali dati del paziente. Se quest'ultimi non sono disponibili
    viene ritornata una stringa vuota per evitare errori sulla lettura
    della ResultSet quando la colonna è vuota
    -------------------------------------------------------------
    @author Nolbert Juarez <nolbert.juarezvera@studenti.unitn.it>
*/
SELECT a.id,a.id_medico,a.timestamp_start,a.timestamp_end,a.resource,
       CASE
           WHEN a.motivo IS NULL THEN ''
           ELSE a.motivo
       END AS motivo,
       CASE
           WHEN a.id_paziente IS NULL THEN ''
           ELSE p.nome || ' ' || p.cognome
       END AS paziente,
       CASE
           WHEN a.id_paziente IS NULL THEN ''
           ELSE p.codice_fiscale
       END,
       CASE
            WHEN a.id_paziente IS NULL THEN 0
            ELSE p.id
       END AS id_paziente
FROM appuntamento AS a
LEFT OUTER JOIN paziente AS p ON p.id = a.id_paziente
WHERE NOT ((timestamp_end <= ?) OR (timestamp_start >= ?))
    AND is_visita_base IS TRUE AND id_medico = ?