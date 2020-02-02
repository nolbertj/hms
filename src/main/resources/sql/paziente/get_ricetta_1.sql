WITH origin1 as(SELECT r.id_ricetta, re.id_farmacia, p.id as id_paziente, p.nome as nome_paziente, p.cognome as cognome_paziente, p.codice_fiscale as codFiscalePaziente, r.data_prescrizione, r.descrizione, m.id as codice_medico, m.nome || ' ' || m.cognome AS medico_prescrivente, m.sesso as sesso_medico, re.data_erogazione
                FROM ricetta AS r
                         JOIN medico_base m ON m.id=r.id_medico_base
                         JOIN paziente p ON p.id=r.id_paziente
                         LEFT OUTER JOIN ricetta_erogata AS re ON r.id_ricetta = re.id_ricetta
                WHERE r.id_ricetta=? AND r.id_paziente = ?),
     origin2 as (select f.id , f.nome || ' - ' || c.nome as nome_farmacia
                 from farmacia f
                          JOIN citta c ON c.id=f.citta)
SELECT o1.*, o2.nome_farmacia from origin1 o1
                  LEFT OUTER JOIN origin2 o2 ON o1.id_farmacia=o2.id