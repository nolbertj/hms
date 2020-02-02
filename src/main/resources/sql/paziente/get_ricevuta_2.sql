/*

    ---------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
SELECT p.id_pagamento,
       ee.data_erogazione,
       p.is_esame,
       es.prezzo as importo,
       p.is_ricetta,
       p.data_pagamento,
       ee.id as id_causale,
       m.metodo,
       ep.id_paziente as paziente
FROM pagamento AS p
JOIN esame_erogato AS ee ON ee.id = p.id_prestazione
JOIN esame_prescritto AS ep ON ee.id = ep.id
JOIN esame_specialistico AS es ON es.codice = ep.codice_esame
JOIN metodo_pag AS m ON m.id = p.metodo
WHERE p.is_esame IS TRUE AND p.id_pagamento = ? AND ep.id_paziente = ?