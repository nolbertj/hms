<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.RicettaServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.services.FarmaciaService" %>

<SCRIPT type="text/javascript">
    $(()=> {
        var idPaziente = 0, pazienti = [], URLs = {
            serviceURL: '${cp}/<%=FarmaciaService.getURL()%>',
            searchPazienteURL: '${cp}/<%=FarmaciaService.searchForSuggestionBoxURL()%>',
            ricetteURL: '${cp}/<%=FarmaciaService.getRicettePazienteURL()%>'
        };

        $("#inputPaziente").select2({
            placeholder: "Nome, cognome o codice fiscale",
            minimumInputLength: <%=FarmaciaService.MIN_LENGHT_FOR_SUGGESTION_BOX%>, //nr minimo di caratteri da digitare per inviare la stringa al server
            ajax: {
                delay: 250, // wait 250 milliseconds until the user has finished typing their search term before triggering the AJAX request.
                type: "GET",
                url: function(request) {
                    return URLs.searchPazienteURL + request.term //term è un attributo specifico di Select2 e corrisponde a ciò che viene digitato
                },
                processResults: function(data) { //data è l'oggetto ritornato dal server
                    pazienti = data.results;
                    return {
                        results: [ //results è l'attributo specifico di select2, qui sto ricostruendo la response per dividere i dati nelle optgroup
                            {
                                text: "Nome", //corrisponde al nome della <optgroup> contenente all'interno le varia <option>
                                children: $.map(data.results, function (paziente) { // children: rappresentano ciascuna <option> della sua <optgroup>
                                    return { //results sta per il nome dell'oggetto intecrno a data (ovvero quello creato in Select2Response.java)
                                        id: paziente["id"],
                                        text: paziente["nome"]
                                    }
                                })
                            },
                            {
                                text: "Cognome",
                                children: $.map(data.results, function(paziente) { // "paziente" è un nome generico della funzione scelto per comodità
                                    return {
                                        id: paziente["id"],
                                        text: paziente["cognome"]
                                    }
                                })
                            },
                            {
                                text: "Codice fiscale",
                                children: $.map(data.results, function(paziente) {
                                    return {
                                        id: paziente["id"],
                                        text: paziente["codFiscale"]
                                    }
                                })
                            }
                        ]
                    }
                },
                error: (err)=>console.log(err)
            }
        });

        $("#TAB").DataTable({
            destroy: true,
            processing: false,
            serverSide: true,
            ajax: {
                url: URLs.ricetteURL + idPaziente,
                data: function(dati) {
                    //console.log(dati);
                    return dati;
                },
                error: (err)=>console.log(err)
            },
            columns: [
                {
                    data: "codice", //nome attributo nel bean
                    title: "Codice", //nome da dare alla colonna
                    className: "text-center"
                },
                {
                    data: "dataPrescrizione", //nome attributo nel bean
                    title: "Data prescrizione", //nome da dare alla colonna
                    className: "text-center",
                    render: function(data,type,row,meta){
                        return parseAndShowDates(row["dataPrescrizione"]);
                    }
                },
                {
                    data: "medicoPrescrittore",
                    title: "Medico prescrivente",
                    className: "text-center"
                },
                {
                    data: "totale",
                    title: "Nr. Farmaci",
                    className: "text-center"
                },
                {
                    data: undefined,
                    title: "Azioni",
                    className: "text-center",
                    orderable: false,
                    render: function(data,type,row,meta){
                        return (
                            '<a href="${cp}/<%=RicettaServlet.getURL()%>?id=' + row["codice"] +
                                '&idPaziente=' + idPaziente + '">' +
                                '<button title="Visualizza ricetta" class="btn btn-primary" type="button">' +
                                    '<i class="fas fa-eye"></i>&nbsp;Visualizza' +
                                '</button>' +
                            '</a>'
                        );
                    }
                }
            ]
        });

        $("#inputPaziente").on('select2:select', function(selection) {
            if($("#ricetteCollapse").hasClass("show")) {
                $("#ricetteCollapse").removeClass("show");
            };
            $("#ricetteCollapse").addClass("show");
            idPaziente = selection.params.data.id; <%-- prende l'id che è nascosto da select2 --%>
            let beanPaziente = pazienti.filter(p => p["id"] === idPaziente)[0]; <%-- prendo il bean dall'array --%>
            let container = $("#select2-inputPaziente-container")[0]; <%-- prendo il container di select2 --%>
            setTimeout(()=> {
                container.lastChild.nodeValue = ( <%-- e inserisco tutti i dati del bean --%>
                    beanPaziente["nome"] + ' ' + beanPaziente["cognome"]+ ', C.F: ' + beanPaziente["codFiscale"]
                )
            }, 10);

            $("#TAB").DataTable().ajax.url(URLs.ricetteURL + idPaziente).load(); <%-- ricarico la datatable --%>
            //console.log("ID paziente selezionato = " + idPaziente);
        });

        $("#inputPaziente").on('select2:unselect', function() {
            //console.log("paziente " + idPaziente + " deselezionato.");
            if($("#ricetteCollapse").hasClass("show")) {
                $("#ricetteCollapse").removeClass("show");
            };
            if($("#ricetteCollapse").hasClass("show")) {
                $("#ricetteCollapse").removeClass("show");
            };

            idPaziente = 0;
            $("#TAB").DataTable().ajax.url(URLs.ricetteURL + idPaziente).load();
        });

        $("#cleanBTN").on('click',function() {
            if($("#ricetteCollapse").hasClass("show")) {
                $("#ricetteCollapse").removeClass("show");
            };
            if($("#ricetteCollapse").hasClass("show")) {
                $("#ricetteCollapse").removeClass("show");
            };
            idPaziente = 0; pazienti = [];
            $("#inputPaziente").val(null).trigger("change"); // per ritornare allo stato iniziale, cancello il paziente selezionato
            $("#TAB").DataTable().ajax.url(URLs.ricetteURL + idPaziente).load();
            //console.log("ricerca paziente e tabella puliti.");
        });

        $(".card-title").on('click',(event)=> {
            if(idPaziente===undefined || idPaziente===0)
                event.stopPropagation();
        });

    });
</SCRIPT>
