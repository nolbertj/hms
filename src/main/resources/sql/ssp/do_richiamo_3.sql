/* query per, fatto un richiamo, inserire un richiamo nelle prestazioni di un paziente

   @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
   */
INSERT INTO esame_prescritto(id_prescrivente, id_paziente, data_prescrizione, codice_esame, is_ssp, is_medico) VALUES(?, ?, ?, ?, true, false);