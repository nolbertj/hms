<%@ page import="it.unitn.disi.wp.project.hms.commons.configs.AvatarProps" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.RefertoServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.RicettaServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.services.MedicoSpecialistaService" %>

<SCRIPT type="text/javascript">
    $(()=> {
        var idPaziente = 0, pazienti = [], boxPaziente = $("#inputPaziente"), URLs = {
            serviceURL:         '${cp}/<%=MedicoSpecialistaService.getURL()%>/',
            searchPazienteURL:  '${cp}/<%=MedicoSpecialistaService.searchForSuggestionBoxURL()%>',
            pazienteURL:        '${cp}/<%=MedicoSpecialistaService.getPazienteURL()%>',
            visiteURL:          '${cp}/<%=MedicoSpecialistaService.getVisitePazienteURL()%>',
            refertoURL:         '${cp}/<%=RefertoServlet.getURL()%>',
            ricettaURL:         '${cp}/<%=RicettaServlet.getURL()%>'
        };
        //==============================================================================================================
        boxPaziente.select2({
            placeholder: "Nome, cognome o codice fiscale",
            ajax: {
                delay: 250, // wait 250 milliseconds until the user has finished typing their search term before triggering the AJAX request.
                type: "GET",
                url: function(request) {
                    return URLs.searchPazienteURL + request.term //term è un attributo specifico di Select2 e corrisponde a ciò che viene digitato
                },
                processResults: function (data) { //data è l'oggetto ritornato dal server
                    pazienti = data.results; // store all results obtained in 'pazienti' variable
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
                                children: $.map(data.results, function(paz) { // "paziente" è un nome generico della funzione scelto per comodità
                                    return {
                                        id: paz["id"],
                                        text: paz["cognome"]
                                    }
                                })
                            },
                            {
                                text: "Codice fiscale",
                                children: $.map(data.results, function(p) {
                                    return {
                                        id: p["id"],
                                        text: p["codFiscale"]
                                    }
                                })
                            }
                        ]
                    }
                },
                error: (err)=>console.log(err)
            }
        });
        boxPaziente.on('select2:select', (selection)=> {
            idPaziente = selection.params.data["id"]; // get the id of the <option> selected
            let beanPaziente = pazienti.filter(p => p["id"] === idPaziente)[0];
            let container = $("#select2-inputPaziente-container")[0];
            setTimeout(()=> {
                container.lastChild.nodeValue = (
                    beanPaziente["nome"] + ' ' + beanPaziente["cognome"]
                )
            }, 10);

            $.ajax({
                type: 'GET',
                url: URLs.pazienteURL + idPaziente, <%-- richiedo bean paziente --%>
                success: (paziente)=> {
                    <%-- scrivo gli attributi del paziente nell'html --%>
                    $("#nomePaziente").text(paziente["nome"] + ' ' + paziente["cognome"]);
                    $("#CFPaziente").text(paziente["codFiscale"]);
                    $("#dataNascitaPaziente").text(parseAndShowDates(paziente["dataNascita"]));
                    $("#luogoNascitaPaziente").text(paziente["luogoNascita"]);
                    $("#sessoPaziente").text(paziente["sesso"]);
                    $("#cittaPaziente").text(paziente["cittaResidenza"]);
                    $("#provinciaPaziente").text(paziente["provincia"]);
                    $("#contattoTelefonicoPaziente").text(paziente["contattoTelefonico"]);
                    $("#emailPaziente").text(paziente["email"]);
                    $("#contattoEmergenzaPaziente").text(paziente["contattoEmergenza"]);

                    $.ajax({
                        type: 'GET', <%-- richiede la foto del paziente --%>
                        url: URLs.serviceURL + "pazienti/" + idPaziente + '/avatar',
                        cache: false, <%-- così evito di riempire la cache di immagini --%>
                        success: (encodedImage)=> {
                            let extension = '<%=AvatarProps.getExtension()%>';
                            if(extension.includes(".") || extension.charAt(0) == '.')
                                extension = extension.substring(1);
                            $("#fotoPaziente").attr('src','data:image/' + extension + ';base64,' + encodedImage);
                        }
                    });
                },
                error: (e)=>console.log(e)
            });

            $("#anagraficaPazienteCard .card-title").trigger('click');

            $("#TAB").DataTable().ajax.url(URLs.visiteURL + idPaziente).load();

            <%-- siccome ci sono troppe colonne, ridimensiono la tabella mandando un click al pulsant "ridimensiona --%>
            if($("#TAB").hasClass("table-sm")===false)
                document.getElementById("compactTableBTN").click();
        });

        let dt=$("#TAB").DataTable({
            destroy: true,
            serverSide: true,
            ajax: {
                url: URLs.visiteURL + idPaziente,
                data: (dati)=> {
                    //console.log(dati);
                    return dati;
                },
                error: (e)=>console.log(e)
            },
            order: [[3,"desc"]],
            columns: [
                { data: "tipo", title: "Tipo", render: (data,type,row,meta)=> {
                        if(row["tipo"]==='R') return 'Ricetta farmaceutica';
                        else if (row["tipo"]==='E') return 'Esame specialistico';
                        else return '';
                    }
                },
                { data: "idPrescrizione",   title: "Cod.",                  className: "text-center" },
                { data: "prescrivente",     title: "Medico prescrittore",   className: "text-center" },
                { data: "dataPrescrizione", title: "Data prescrizione",     className: "text-center",
                    render: (data,type,row,meta)=> {
                        return parseAndShowDates(row["dataPrescrizione"]);
                    } },
                { data: "dataErogazione",   title: "Data erogazione",       className: "text-center",
                    render: (data,type,row,meta)=> {
                        return parseAndShowTimestamp(row["dataErogazione"]);
                    } },
                { data: undefined,          title: "Descrizione",                className: "text-center",
                    orderable: false, searchable: false, render: (data,type,row,meta)=> {

                        let url, pre;
                        if(row["tipo"] === 'E'){
                            url = URLs.refertoURL;
                            pre = 'Nome esame: ';
                        }
                        else if (row["tipo"] === 'R')
                            url = URLs.ricettaURL;

                        let visualizzaBTN = (
                            '<button title="Visualizza descrizione" type="button" class="btn btn-primary" id="visualizzaBTN">' +
                            '<i id="iconcinaVISUALIZZA" class="fas fa-eye"></i>&nbsp;Visualizza' +
                            '</button>'
                        );

                        if($(window).width() < 992 && row["tipo"]==='E' && (row["dataErogazione"]=== undefined || row["dataErogazione"]==='' || row["dataErogazione"]=== null))
                            return pre + row["descrizione"];
                        else{
                            let boolean1=row["dataErogazione"]!== undefined && row["dataErogazione"]!=='' && row["dataErogazione"]!== null && row["tipo"]==='E';
                            let boolean2=row["tipo"]==='R';
                            if(boolean1 || boolean2)
                                return (
                                    '<a target="_blank" href="' + url + '?id=' + row["idPrescrizione"] + '&idPaziente='+ idPaziente+'">' +
                                    visualizzaBTN +
                                    '</a>'
                                );
                            else
                                return visualizzaBTN;
                        }
                    }
                }
            ]
        });

        $('#inputPaziente').val(null).trigger('change');
        $("#inputPaziente").on('select2:unselect', ()=> {
            $("#fotoPaziente").attr('src','${img}/patient.jpg');
            $("#nomePaziente").text("undefined");
            $("#CFPaziente").text("undefined");
            $("#dataNascitaPaziente").text("undefined");
            $("#luogoNascitaPaziente").text("undefined");
            $("#sessoPaziente").text("undefined");
            $("#cittaPaziente").text("undefined");
            $("#provinciaPaziente").text("undefined");
            $("#contattoTelefonicoPaziente").text("undefined");
            $("#emailPaziente").text("undefined");
            $("#contattoEmergenzaPaziente").text("undefined");

            if($("#schedaPazienteCollapse").hasClass("show")) {
                $("#schedaPazienteCollapse").removeClass("show");
            };
            if($("#anagraficaPazienteCollapse").hasClass("show")) {
                $("#anagraficaPazienteCollapse").removeClass("show");
            };

            idPaziente = 0;
            $("#TAB").DataTable().ajax.url(URLs.visiteURL + idPaziente).load();
        });
        //==============================================================================================================
        $(".card-title").on('click',(event)=> {
            if(idPaziente===undefined || idPaziente===0)
                event.stopPropagation();
        });
        //==============================================================================================================

        var jsonData;
        dt.on( 'xhr', ()=> jsonData = dt.ajax.json().data);

        let lastColumnName = dt.column(5).header();

        if($(window).width() > 992) { // scripts only for DESKTOP screen

            $(lastColumnName).html('Descrizione');

            // Array to track the ids of the details displayed rows
            let detailRows = [];

            $('#TAB tbody').on('click', '#visualizzaBTN', function (event) {
                let riga;
                if(event.target.id === "visualizzaBTN"){
                    let tmp=$(event.target.parentNode.parentNode.rowIndex);
                    console.log(tmp);
                    riga=tmp[0]-1;
                    console.log(riga);
                }else if(event.target.id === "iconcinaVISUALIZZA"){
                    let tmp=$(event.target.parentNode.parentNode.parentNode.rowIndex);
                    riga = tmp[0]-1
                    console.log(riga);
                }

                if (riga >= 0) {
                    let id = dt.column(1).data()[riga],
                        type = dt.column(0).data()[riga],
                        dataErog=dt.column(4).data()[riga];
                    if(type==='E' && (dataErog===undefined || dataErog===null ||dataErog==='')) {
                        let indiceElem = jsonData.findIndex((item, i) => {
                            return ((item["idPrescrizione"] === id) && (item["tipo"] === type));
                        });
                        if (indiceElem >= 0) {

                            let tr = $(this).closest('tr');

                            let row = dt.row(tr);

                            let idx = $.inArray(tr.attr('id'), detailRows);

                            if (row.child.isShown()) {

                                row.child.hide();

                                // Remove from the 'open' array
                                detailRows.splice(idx, 1);
                            } else {
                                let pre;
                                if (jsonData[indiceElem]["tipo"] === 'E')
                                    pre = 'Nome esame: ';
                                else if (jsonData[indiceElem]["tipo"] === 'R')
                                    pre = 'Descrizione: ';
                                else pre = '';
                                row.child([
                                    '<td class="bottomRow">' + pre + jsonData[indiceElem]["descrizione"] + '</td>'
                                ]).show();

                                // Add to the 'open' array
                                if (idx === -1) {
                                    detailRows.push(tr.attr('id'));
                                }
                            }
                        }
                    }
                }
            });

            // On each draw, loop over the `detailRows` array and show any child rows
            dt.on('draw', function() {
                $.each(detailRows, (i, id)=> {
                    $('#' + id + '#visualizzaBTN').trigger('click');
                });
            });

            setTimeout(()=>document.getElementById("compactTableBTN").click(),500);
        }
    });
</SCRIPT>
