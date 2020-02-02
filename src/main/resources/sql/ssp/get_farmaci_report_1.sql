/*
    Query per ottenere le ricette erogate per un determinato giorno all'interno di una certa provincia.
    Questa query è necessaria per creare il report in formato xlsx
    --------------------------------------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
WITH provincia as (select ssp.provincia from servizio_sanitario ssp where ssp.id=?),
     pazienti as (select p.* from provincia, paziente p
                    JOIN citta c ON c.id=p.residenza
                  WHERE c.provincia=provincia.provincia)
Select re.id_ricetta, re.data_erogazione, f.nome as nome_farmacia, mb.nome || ' ' || mb.cognome as medico_prescrivente,
       p.nome as nome_paziente, p.cognome as cognome_paziente
from ricetta_erogata re
    JOIN farmacia f ON re.id_farmacia=f.id
    JOIN ricetta r ON re.id_ricetta=r.id_ricetta
    JOIN medico_base mb ON mb.id=r.id_medico_base
    JOIN pazienti p ON p.id=r.id_paziente
WHERE re.data_erogazione::date = ?