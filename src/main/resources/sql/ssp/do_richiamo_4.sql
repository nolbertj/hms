/* query per avere i pazienti sottoposto a richiamo

   @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
   */
WITH provincia as (select ssp.provincia from servizio_sanitario ssp where ssp.id=?)
select p.id, p.nome, p.cognome, p.email from paziente p
                     JOIN citta c ON p.residenza=c.id
                     JOIN provincia pr ON pr.provincia=c.provincia
/*per un esame solo femminile metter due volte F, per solo maschile due volte M, per entrambi i sessi M e poi F*/
where (sesso=? OR sesso=?) AND
    (date_part('year', age(now(), p.data_nascita)) >= ? AND date_part('year', age(now(), p.data_nascita)) <= ?);