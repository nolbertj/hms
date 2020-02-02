/*
    Query per aggiornare un pagamento pendente.
    -------------------------------------------------------------
    @author Nolbert Juarez <nolbert.juarezvera@studenti.unitn.it>
*/
UPDATE pagamento
SET data_pagamento = ?, metodo = ?
WHERE id_prestazione = ? AND id_pagamento = ?