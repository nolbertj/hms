<%@ page import="it.unitn.disi.wp.project.hms.services.SspService" %>
<SCRIPT type="text/javascript">
    $(()=> {
        var idPaziente = undefined, pazienti = [], idPrescrizione = undefined, esami = [], URLs = {
            serviceURL:         '${cp}/<%=SspService.getURL()%>/',
            searchPazienteURL:  '${cp}/<%=SspService.searchForSuggestionBoxURL()%>'
        },  boxPaziente = $("#suggestionPaziente"), boxEsame = $("#suggestionEsame"), form = "#compilaRefertoForm";
        //==============================================================================================================
        boxPaziente.select2({
            placeholder: "Nome, cognome o codice fiscale",
            ajax: {
                delay: 250,
                type: "GET",
                url: (request)=> { return URLs.searchPazienteURL + request.term; },
                processResults: (data)=> {
                    pazienti = data.results; // store all results obtained in 'pazienti' variable
                    return {
                        results: [
                            {
                                text: "Nome", // title of the <optgroup>
                                children: $.map(data.results, (paziente)=> { // build <optgroup> with only "nome"
                                    return {
                                        id: paziente["id"],
                                        text: paziente["nome"]
                                    }
                                })
                            },
                            {
                                text: "Cognome", // title of the <optgroup>
                                children: $.map(data.results, (paz)=> { // build <optgroup> with only "cognome"
                                    return {
                                        id: paz["id"],
                                        text: paz["cognome"]
                                    }
                                })
                            },
                            {
                                text: "Codice fiscale", // title of the <optgroup>
                                children: $.map(data.results, (p)=> { // build <optgroup> with only "codFiscale"
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
            let paziente = pazienti.filter(p => p["id"] === idPaziente)[0]; // get all the params from the 'pazienti' variable build before
            let container = $("#select2-suggestionPaziente-container")[0]; // get the container of the <select> tag
            setTimeout(()=> {
                container.lastChild.nodeValue = ( // change the <select> text after 10ms with all params of 'paziente'
                    paziente["nome"] + ' ' + paziente["cognome"] + ', C.F.: ' + paziente["codFiscale"]
                )
            }, 10);
        });
        boxPaziente.val(null).trigger('change'); // clear the <select> tag
        boxPaziente.on('select2:unselect', ()=> {
            pazienti = []; idPaziente = undefined; // reset the variables
        });
        //==============================================================================================================
        let esame;
        boxEsame.select2({
            placeholder: "Nome o codice prescrizione",
            ajax: {
                delay: 250,
                type: "GET",
                url: (request)=> {
                    return URLs.searchPazienteURL + idPaziente + '/esami/' + request.term;
                },
                processResults: (data)=> {
                    esami = data.results;
                    return {
                        results: [
                            {
                                text: "Nome",
                                children: $.map(esami, (esame)=> {
                                    return {
                                        id: esame["idPrescrizione"],
                                        text: esame["nome"]
                                    }
                                })
                            },
                            {
                                text: "Area",
                                children: $.map(esami, (esame)=> {
                                    return {
                                        id: esame["idPrescrizione"],
                                        text: esame["area"]
                                    }
                                })
                            },
                            {
                                text: "Cod. Prescrizione",
                                children: $.map(esami, (esame)=> {
                                    return {
                                        id: esame["idPrescrizione"],
                                        text: esame["idPrescrizione"]
                                    }
                                })
                            }
                        ]
                    }
                },
                error: (err)=>console.log(err)
            }
        });
        boxEsame.on('select2:select', (selection)=> {
            idPrescrizione = selection.params.data["id"];
            esame = esami.filter(e => e["idPrescrizione"] === idPrescrizione)[0];
            $("#dataPrescrizione").attr('placeholder',parseAndShowDates(esame["dataPrescrizione"]));
            $("#prescrivente").attr('placeholder',esame["prescrivente"]);
            $("#codPrescrizione").attr('placeholder',esame["idPrescrizione"]);

            document.getElementById("includiPagamento").checked = false;
            document.getElementById("includiPagamento").disabled = false;

            if(esame["prescrittoDaSSP"]){
                document.getElementById("includiPagamento").checked = false;
                document.getElementById("includiPagamento").disabled = true;
            }
        });
        boxEsame.val(null).trigger('change');
        boxEsame.on('select2:unselect', ()=> {
            document.getElementById("includiPagamento").checked = false;
            document.getElementById("includiPagamento").disabled = false;
            esame = undefined; esami = []; idPrescrizione = undefined;
            $("#dataPrescrizione").attr('placeholder','');
            $("#prescrivente").attr('placeholder','');
            $("#codPrescrizione").attr('placeholder','');
        });
        //==============================================================================================================
        $(form).on('submit', ()=> { // set the params that need to be send on server-side
            if (idPrescrizione && idPaziente) { // check if variables exists (not undefined)
                addParamToForm("idPrescrizione",idPrescrizione,form);
                addParamToForm("idPaziente",idPaziente,form);
                addParamToForm("area",esame["area"],form);
                if(document.getElementById("includiPagamento").checked===true)
                    addParamToForm("includiPagamento","on",form);
                else { <%-- server doesn't recive the param name, so we add 'off' with the next script --%>
                    addParamToForm("includiPagamento","off",form);
                }
                return true; // with true the form do the POST method on server-side
            } else {
                alert("E' richiesto il paziente e l'esame!");
                return false; // with false, the form doesn't send the params; the html page doesn't chan
            }
        });
        //==============================================================================================================
    });
</SCRIPT>