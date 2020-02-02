<SCRIPT type="text/javascript">
    $(()=> {
        let dt = $("#TAB").DataTable({
            destroy: true,
            serverSide: true,
            ajax: {
                url: URLs.visiteURL,
                data: (dati)=> {
                    //console.log(dati);
                    return dati;
                },
                error: (e)=>console.log(e)
            },
            order: [[3,"desc"]],
            columns: [
                { data: "tipo", title: "Tipo", searchable:false, render: (data,type,row,meta)=> {
                        if(row["tipo"]==='R') return 'Ricetta farmaceutica';
                        else if (row["tipo"]==='E') return 'Esame specialistico';
                        else return '';
                    }
                },
                { data: "idPrescrizione",   title: "Cod.",                  className: "text-center", searchable: false},
                { data: "prescrivente",     title: "Medico prescrittore",   className: "text-center" },
                { data: "dataPrescrizione", title: "Data prescrizione",     className: "text-center",
                    render: (data,type,row,meta)=> {
                        return parseAndShowDates(row["dataPrescrizione"]);
                    }
                },
                { data: "dataErogazione",   title: "Data erogazione",       className: "text-center",
                    render: (data,type,row,meta)=> {
                        return parseAndShowDates(row["dataErogazione"]);
                    }
                },
                { data: undefined,          title: "Descrizione",                className: "text-center",
                    orderable: false, searchable: false, render: (data,type,row,meta)=> {

                        let url, pre;
                        if(row["tipo"] === 'E'){
                            url = URLs.refertoURL;
                            pre = 'Nome esame: ';
                        }

                        else if (row["tipo"] === 'R'){
                            url = URLs.ricettaURL;
                        }

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
                                    '<a target="_blank" href="' + url + '?id=' + row["idPrescrizione"] + '&idPaziente=${paziente.id}">' +
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

                    riga = event.target.parentNode._DT_CellIndex["row"];
                }else if(event.target.id === "iconcinaVISUALIZZA"){

                    riga = event.target.parentNode.parentNode._DT_CellIndex["row"];
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
