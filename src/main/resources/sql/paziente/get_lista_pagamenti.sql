/*

    ---------------------------------------------------------------------
    @author Alessandro Brighenti <alessandro.brighenti@studenti.unitn.it>
*/
WITH
origin AS (
    SELECT p.id_pagamento,re.data_erogazione, p.is_esame, p.is_ricetta, p.data_pagamento, re.id_ricetta as id
    FROM pagamento AS p
    JOIN ricetta_erogata AS re ON re.id_ricetta = p.id_prestazione
    JOIN ricetta AS r ON r.id_ricetta = re.id_ricetta
    WHERE p.is_ricetta IS TRUE AND r.id_paziente=?
),
origin2 AS (
    SELECT o1.id as id_ricetta, 3+sum(f.prezzo) as importo
    FROM farmaco AS f
    JOIN ricetta_farmaco AS rf ON f.id = rf.id_farmaco
    JOIN origin AS o1 ON o1.id = rf.id_ricetta
    GROUP BY o1.id
),
origin3 AS (
    SELECT o1.id_pagamento, o1.data_erogazione, o1.is_esame, o2.importo, o1.is_ricetta, o1.data_pagamento, o1.id
    FROM origin o1
    JOIN origin2 o2 ON o1.id=o2.id_ricetta
    UNION
    (
        SELECT p.id_pagamento, ee.data_erogazione, p.is_esame, es.prezzo as importo, p.is_ricetta, p.data_pagamento, ee.id
        FROM pagamento AS p
        JOIN esame_erogato ee ON ee.id = p.id_prestazione
        JOIN esame_prescritto ep ON ee.id = ep.id
        JOIN esame_specialistico es ON es.codice = ep.codice_esame
        WHERE p.is_esame AND ep.id_paziente = ?
    )
)
---------------------------------------------------------------------
SELECT * FROM origin3 AS o3
WHERE
    lower(o3.id_pagamento::text) LIKE ? OR
    lower(o3.data_erogazione::text) LIKE ? OR
    lower(o3.importo::text) LIKE ?