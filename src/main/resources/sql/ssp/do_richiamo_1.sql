/* query per avere il sesso di un certo esame

   @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
   */
SELECT es.for_sesso FROM esame_specialistico es
WHERE es.codice=?