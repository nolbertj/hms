<%@ page import="it.unitn.disi.wp.project.hms.services.MedicoBaseService" %>
<%@ page import="it.unitn.disi.wp.project.hms.services.OpenService" %>
<%@ page import="it.unitn.disi.wp.project.hms.persistence.utils.PerGenere" %>

<SCRIPT type="text/javascript">
    $(()=> {
        //==============================================================================================================
        var CP = '${cp}/', idPaziente = undefined, pazienti = [], paziente = undefined, codEsame = undefined, esami = [],
            boxPaziente = $("#suggestionPaziente"), boxEsame = $("#suggestionEsame"), form = "#prescriviEsameForm",
            URLs = {
                serviceURL:         CP + '<%=MedicoBaseService.getURL()%>/',
                searchPazienteURL:  CP + '<%=MedicoBaseService.getURL()%>/${USER.id}/pazienti/',
                searchEsameURL:     CP + '<%=OpenService.getEsamiPrescrivibiliURL()%>/'
            };
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
            paziente = pazienti.filter(p=> p["id"] === idPaziente)[0]; // get all the params from the 'pazienti' variable build before
            let container = $("#select2-suggestionPaziente-container")[0]; // get the container of the <select> tag
            setTimeout(()=> { container.lastChild.nodeValue = ( // change the <select> text after 10ms with all params of 'paziente'
                paziente["nome"] + ' ' + paziente["cognome"] + ', C.F.: ' + paziente["codFiscale"]
            )}, 10);
        });
        boxPaziente.val(null).trigger('change'); // clear the <select> tag
        boxPaziente.on('select2:unselect', ()=> {
            pazienti = []; paziente = undefined, idPaziente = undefined; // reset the variables
        });
        //==============================================================================================================
        boxEsame.select2({
            placeholder: "Nome o area",
            ajax: {
                delay: 250,
                type: "GET",
                url:  (request)=> { return URLs.searchEsameURL + request.term },
                processResults: (data)=> {
                    esami = data.results;
                    let forGender = {
                        F: '<%=PerGenere.PER_GENERE.FEMALE.getChar()%>',
                        M: '<%=PerGenere.PER_GENERE.MALE.getChar()%>',
                        FEMALE : 'FEMALE',
                        MALE: 'MALE',
                        ALL: 'ALL'
                    }
                    return {
                        results: [
                            {
                                text: "Nome",
                                children: $.map(esami, (esame)=> {
                                    if(paziente) {
                                        if( (paziente["sesso"] === forGender.F)
                                            && (forGender.FEMALE === esame["perGenere"] )) {
                                            return {
                                                id: esame["codice"],
                                                text: esame["nome"]
                                            }
                                        } else if( (paziente["sesso"] === forGender.M)
                                            && (forGender.MALE === esame["perGenere"] )) {
                                            return {
                                                id: esame["codice"],
                                                text: esame["nome"]
                                            }
                                        }
                                        if (esame["perGenere"] == forGender.ALL) {
                                            return {
                                                id: esame["codice"],
                                                text: esame["nome"]
                                            }
                                        }
                                    }
                                })
                            },
                            {
                                text: "Area",
                                children: $.map(esami, (esame)=> {
                                    if(paziente) {
                                        if( (paziente["sesso"] === forGender.F)
                                            && (forGender.FEMALE === esame["perGenere"] )) {
                                            return {
                                                id: esame["codice"],
                                                text: esame["area"]
                                            }
                                        } else if( (paziente["sesso"] === forGender.M)
                                            && (forGender.MALE === esame["perGenere"] )) {
                                            return {
                                                id: esame["codice"],
                                                text: esame["area"]
                                            }
                                        }
                                        if (esame["perGenere"] == forGender.ALL) {
                                            return {
                                                id: esame["codice"],
                                                text: esame["area"]
                                            }
                                        }
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
            codEsame = selection.params.data["id"];
        });
        boxEsame.val(null).trigger('change');
        boxEsame.on('select2:unselect', ()=> {
            esami = []; codEsame = undefined;
        });
        //==============================================================================================================
        $(form).on('submit', ()=> { // set the params that need to be send on server-side
            if (codEsame && idPaziente) { // check if variables exists (not undefined)
                addParamToForm("codEsame",codEsame,form);
                addParamToForm("idPaziente",idPaziente,form);
                return true; // with true the form do the POST method on server-side
            } else {
                alert("E' richiesto il paziente e l'esame!");
                return false; // with false, the form doesn't send the params; the html page doesn't chan
            }
        })
        //==============================================================================================================
    });
</SCRIPT>