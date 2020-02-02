<SCRIPT type="text/javascript">
    $(function() {

        $("#TAB").addClass("table-sm");

        $("#TAB").DataTable({
            dom: "", // senza nessun pulsante
            responsive: true,
            order: [[0,"desc"]],
            columnDefs: [
                {
                    orderable: false, //nessuna colonna sarà ordinabile (quindi nessuna colonna avrà l'icon di ordinamento
                    targets: "_all"
                }
            ]
            //dal momento che non si tratta di molti dati, la tabella verrà riempita dal bean Ricetta
            //la dichiarazione di datatable va comunque lasciata per renderla responsive in automatico
        });

        //Questa riga sottostante toglie l'icona dell'ordinamento alla prima colonna.
        $("#TAB thead th:first").removeClass();

        if($(window).width() < 992) {
            $("#qr-code").parent().addClass("text-center");
            let title = document.getElementsByTagName("h4")[0]; //PAGATO
            title.classList.add("text-center");
        }

    });
//======================================================================================================================
</SCRIPT>