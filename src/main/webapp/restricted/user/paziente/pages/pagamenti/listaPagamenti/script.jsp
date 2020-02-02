<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.RefertoServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.RicettaServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.RicevutaServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.services.PazienteService" %>

<SCRIPT type="text/javascript">
    var idRicevuta = 0, idCausale = 0;
    function setPaymentData(codRiferimentoPagamento,idPrestazione){
        idRicevuta = codRiferimentoPagamento;
        idCausale = idPrestazione;
    }
    $(()=> {
        let URLs = {
            serviceURL: '${cp}/<%=PazienteService.getURL()%>',
            ricevutaURL:'${cp}/<%=RicevutaServlet.getURL()%>',
            refertoURL: '${cp}/<%=RefertoServlet.getURL()%>',
            ricettaURL: '${cp}/<%=RicettaServlet.getURL()%>'
        }

        setInputFilter(document.getElementById("numeroCarta"), (value)=>{return onlyDigits(value)});
        setInputFilter(document.getElementById("intestatario"), (value)=>{return onlyCharsAndWhiteSpace(value)});

        let years = [], actualYear = new Date().getFullYear();
        for(let i=0; i<=20; i++){
            years[i] = actualYear + i;
        }
        for(let index in years){
            let element = document.createElement("option");
            element.innerHTML = years[index];
            $("#year").append(element);
        }
        for(let i=1; i<=12; i++) {
            let element = document.createElement("option");
            element.innerHTML = (i<10 ? '0'+i:i);
            $("#month").append(element);
        }

        $("#TAB").DataTable({
            serverSide: true,
            ajax: {
                url: URLs.serviceURL + "/${USER.id}/listaPagamenti",
                data: (dati)=> {
                    //console.log(dati);
                    return dati;
                },
                error: (e)=>console.log(e)
            },
            order: [[1,"desc"]],
            columns: [
                { data: "idRicevuta", title: "Cod.", className: "text-center" },
                { data: "causale", title: "Causale", className: "text-center", searchable: false, orderable: false,
                    render: (data,type,row,meta)=> {
                        let prescription = document.createElement("a");
                        if(row["isEsame"]===true || row["isEsame"]==='true') {
                            prescription.href = URLs.refertoURL + '?id=' + row["idCausale"] + '&idPaziente=${USER.id}';
                            prescription.innerText = 'Esame specialistico';
                        }
                        else if(row["isRicetta"]===true || row["isRicetta"]==='true') {
                            prescription.href = URLs.ricettaURL + '?id=' + row["idCausale"] + '&idPaziente=${USER.id}';
                            prescription.innerText = "Ricetta farmaceutica";
                        }
                        return prescription.outerHTML;
                    }
                },
                { data: "dataErogazione", title: "Data erogazione", className: "text-center", render: function(data,type,row,meta){
                        return parseAndShowDates(row["dataErogazione"]);
                    } },
                { data: "importo", title: "Importo", className: "text-center", render: (data,type,row,meta)=> {
                        let span = document.createElement("span");
                        // metto il prezzo con la valuta
                        let prezzo = parseFloat(row["importo"]).toFixed(2); // faccio cast da string a float con 2 decimali
                        span.innerHTML = prezzo.toString() + ' &euro;';
                        return span.outerHTML;
                    }
                },

                { data: undefined, title: "Stato", className: "text-center", searchable: false, orderable: false,
                    render: (data,type,row,meta)=> {
                        let _spa = document.createElement("span");
                        if( row["dataPagamento"]===null ||
                            row["dataPagamento"]==='' ||
                            row["dataPagamento"]==='undefined' ||
                            row["dataPagamento"]===undefined) {
                            _spa.style.color = "red";
                            _spa.innerText = "Non pagato"
                        }
                        else {
                            _spa.style.color = "green";
                            _spa.innerText = "Pagato";
                        }
                        _spa.style.fontWeight = "bold";
                        _spa.className = 'status';
                        return _spa.outerHTML;
                    }
                },
                { data: "dataPagamento", title: "Data pagamento", className: "text-center", searchable:false,render: function(data,type,row,meta){
                        return parseAndShowDates(row["dataPagamento"]);
                    }},
                { data: undefined, title: "Azioni", className: "text-center", orderable: false, searchable: false,
                    render: (data,type,row,meta)=> {

                        let visualizzaBTN = (
                            '<a href="' + URLs.ricevutaURL + '?id=' + row["idRicevuta"] + '&idPaziente=${USER.id}">' +
                                '<button title="VISUALIZZA RICEVUTA" class="btn btn-primary" type="button">' +
                                    '<i class="fas fa-eye"></i> VISUALIZZA' +
                                '</button>' +
                            '</a>'
                        );

                        let payBTN = (
                            '<button title="PAGA ORA" type="button" class="btn btn-danger" ' +
                                    'data-toggle="modal" data-target="#payModal" onclick="setPaymentData(' + row["idRicevuta"] + ','+ row["idCausale"] + ')">PAGA ORA' +
                            '</button>'
                        );

                        let buttonsContainer = '<div class="btn-group shadow-sm" role="group">';
                        if( row["dataPagamento"] === null ||
                            row["dataPagamento"] === '' ||
                            row["dataPagamento"] === 'undefined' ||
                            row["dataPagamento"] === undefined) {
                            buttonsContainer += payBTN;
                        }
                        else buttonsContainer += (visualizzaBTN);

                        buttonsContainer+='</div>';
                        return buttonsContainer;
                    }
                }
            ]
        });

        $("#payForm").on('submit',()=> {
            if(checkCardRequirments()) {
                addParamToForm("cod",idRicevuta,"#payForm");
                addParamToForm("idPrestazione",idCausale,"#payForm");
                return true;
            }
            else return false;
        });

        let params=[
            {
                name: "referto",
                value: "false"
            },
            {
                name: "ricetta",
                value: "false"
            },
            {
                name: "pagamenti",
                value: "true"
            }
        ];
        $('#PDFGen').on('submit', () => { //listen for submit event
            $.each(params, function (i, param) {
                $('<input>').attr('type', 'hidden')
                    .attr('name', param.name)
                    .attr('value', param.value)
                    .appendTo('#PDFGen');
            })
        });

    });

    function checkCardRequirments() {
        let res = false;

        if($("#mastercardRadio").is(":checked") || $("#visaRadio").is(":checked")){

            let nrCard = document.getElementById("numeroCarta"),
                intestatario = document.getElementById("intestatario");

            if(nrCard.value.length === 16)
                res =  true;
            else {
                nrCard.style.borderColor = "red";
                $("#alertCard").toggleClass("off");
            }

            if(!intestatario.value.length > 1)
                res = true;
            else intestatario.style.borderColor = "red";
        }
        else if($("#paypalRadio").is(":checked")){
            res = true;
        }
        else {
            document.getElementById("payMethod").style.color = "red";
        }
        return res;
    }


    let params=[
        {
            name: "referto",
            value: "false"
        },
        {
            name: "ricetta",
            value: "false"
        },
        {
            name: "pagamenti",
            value: "true"
        }
    ];
    $('#PDFGen').on('submit', () => { //listen for submit event
        $.each(params, function (i, param) {
            $('<input>').attr('type', 'hidden')
                .attr('name', param.name)
                .attr('value', param.value)
                .appendTo('#PDFGen');
        })
    });
//======================================================================================================================
</SCRIPT>