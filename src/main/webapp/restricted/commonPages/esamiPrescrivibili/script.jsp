<%@ page import="it.unitn.disi.wp.project.hms.services.OpenService" %>

<SCRIPT type="text/javascript">
    $(()=> {
        var jsonData;

        let dt = $("#TAB").DataTable({
            serverSide: true,
            ajax: {
                url: "${cp}/<%=OpenService.getEsamiPrescrivibiliURL()%>",
                data: (dati)=> {
                    //console.log(dati);
                    return dati;
                },
                error: (e)=>console.log(e)
            },
            columns: [
                { data: "codice", title: "Codice", className: "text-center" },
                { data: "nome", title: "Esame" },
                { data: "area", title: "Area", className: "text-center" },
                { data: "prezzo", title: "Prezzo", className: "text-center" , render: (data,type,row,meta)=> {
                        let span = document.createElement("span");
                        // metto il prezzo con la valuta
                        let prezzo = parseFloat(row["prezzo"]).toFixed(2); // faccio cast da string a float con 2 decimali
                        span.innerHTML = prezzo.toString() + ' &euro;';
                        return span.outerHTML;
                    }
                },
                { data: undefined, title: "Descrizione", className: "text-center",
                    render: (data,type,row,meta)=> {
                        if($(window).width() < 992)
                            return row["descrizione"];
                        else {
                            return (
                                '<button title="Visualizza dettagli" type="button" class="btn btn-primary" id="visualizzaBTN">' +
                                    '<i id="iconcinaVISUALIZZA" class="fas fa-eye"></i> ' + 'Visualizza dettagli' +
                                '</button>'
                            );
                        }
                    }
                }
            ],
            pageLength: 100
        });
        // ogni volta che i dati vengono aggiornati, aggiorno anche la variabile jsonData
        // in modo che gli script sottostanti possano eseguirsi tranquillament (perchè hanno bisogna dei dati json delle tabelle)
        dt.on( 'xhr', ()=> jsonData = dt.ajax.json().data);

        if( $(window).width() < 992) {
            dt.colReorder.move(0,1);
        }

        let lastColumnName = dt.column(4).header();

        if($(window).width() > 992) { // scripts only for DESKTOP screen

            $(lastColumnName).html('Azioni');

            // Array to track the ids of the details displayed rows
            let detailRows = [];

            $('#TAB tbody').on('click', '#visualizzaBTN', function (event) {
                let riga;
                if(event.target.id === "visualizzaBTN"){
                    riga = event.target.parentNode._DT_CellIndex["row"];
                } else if(event.target.id === "iconcinaVISUALIZZA"){
                    riga = event.target.parentNode.parentNode._DT_CellIndex["row"];
                }

                if (riga >= 0) {
                    //prendo la prima colonna della tabella (column(0)) e il valore (rappresentato da riga)
                    let id = dt.column(0).data()[riga];
                    //console.log("id = " + id);
                    //restituisce l'indice dell'elemento che ha come campo "id" l'id dell'ultima riga
                    let indiceElem = jsonData.findIndex((item, i)=> {
                        return item.codice === id;
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
                            //console.log(tr);
                            row.child([
                                '<td class="bottomRow">' + jsonData[indiceElem]["descrizione"] + '</td>'
                            ]).show();

                            // Add to the 'open' array
                            if (idx === -1) {
                                detailRows.push(tr.attr('id'));
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
//======================================================================================================================
</SCRIPT>