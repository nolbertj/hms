/**
 * La seguente funzione mi permette di impostare dei parametri di default per le Datatable.
 * FONTE: https://datatables.net/examples/advanced_init/defaults.html
 */
$.extend(true,$.fn.dataTable.defaults, {
    processing: true, //mostra un messaggio sullo stato di caricamento. mettere false se non lo si vuole
    colReorder: true, // mi permette di trascinare le colonne col mouse/trascinamento
    info: true, // mostro messaggi sul nr di colonne,righe, elementi filtrati etc nella footer
    pagingType: "full_numbers", // indica in che modo mostrare l'impaginamento
    language: {
        sEmptyTable:      "Nessun dato presente nella tabella",
        sInfo:            "Vista da _START_ a _END_ di _TOTAL_ elementi",
        sInfoEmpty:       "Vista da 0 a 0 di 0 elementi",
        sInfoFiltered:    "(filtrati da _MAX_ elementi totali)",
        sLengthMenu:      "_MENU_",
        sLoadingRecords:  "Caricamento...",
        sProcessing:      "Richiedo dati...",
        sSearch:          "<i class=\"fas fa-search\"></i>",
        searchPlaceholder:"Ricerca",
        sZeroRecords:     "La ricerca non ha portato alcun risultato.",
        oPaginate: {
            sFirst:       "<i class=\"fas fa-angle-double-left\"></i>",
            sPrevious:    "<i class=\"fas fa-angle-left\"></i>",
            sNext:        "<i class=\"fas fa-angle-right\"></i>",
            sLast:        "<i class=\"fas fa-angle-double-right\"></i>"
        },
    },
    dom: 'Blfrtip', // NON TOCCARE, non è capito come organizzare gli elementi della datatable quindi ho lasciato così e spostati con js
    buttons: [
        {
            extend: "colvis", //bottone delle datatables che mi permette di scegliere quali colonne visualizzare
            columns: ":not(.noVis)" //esclusa la colonna con classe .noVis specificata sopra
        }
    ] //quindi come risultato posso scegliere di non visualizzare nessuna colonna eccetto la prima
});