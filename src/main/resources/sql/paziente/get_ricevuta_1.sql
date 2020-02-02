/*

    ---------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
WITH
origin AS (
    SELECT p.id_pagamento,
           re.data_erogazione,
           p.is_esame,
           p.is_ricetta,
           p.data_pagamento,
           re.id_ricetta as id_causale,
           r.id_paziente as paziente,
           m.metodo
    FROM pagamento AS p
    JOIN ricetta_erogata AS re ON re.id_ricetta = p.id_prestazione
    JOIN ricetta AS r ON r.id_ricetta = re.id_ricetta
    JOIN metodo_pag AS m ON m.id = p.metodo
    WHERE p.is_ricetta IS TRUE AND p.id_pagamento = ? AND r.id_paziente = ?
),
origin2 AS (
    SELECT o1.id_causale, 3+sum(f.prezzo) as importo
    FROM farmaco AS f
    JOIN ricetta_farmaco AS rf ON f.id = rf.id_farmaco
    JOIN origin AS o1 ON o1.id_causale = rf.id_ricetta
    GROUP BY o1.id_causale
)
---------------------------------------------------------------------
SELECT o1.id_pagamento,
       o1.data_erogazione,
       o1.is_esame,
       o2.importo,
       o1.is_ricetta,
       o1.data_pagamento,
       o1.id_causale,
       o1.paziente,
       o1.metodo
FROM origin AS o1
JOIN origin2 AS o2 ON o1.id_causale = o2.id_causale