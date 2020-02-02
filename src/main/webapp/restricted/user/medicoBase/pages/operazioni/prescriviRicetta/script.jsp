<%@ page import="it.unitn.disi.wp.project.hms.services.MedicoBaseService" %>
<%@ page import="it.unitn.disi.wp.project.hms.services.OpenService" %>

<SCRIPT type="text/javascript">
    //==============================================================================================================
    var CP = '${cp}/', idPaziente = undefined, pazienti = [], paziente = undefined, idFarmaco = undefined, farmaci = [],
        URLs = {
            serviceURL:         CP + '<%=MedicoBaseService.getURL()%>/',
            searchPazienteURL:  CP + '<%=MedicoBaseService.getURL()%>/${USER.id}/pazienti/',
            searchFarmacoURL:   CP + '<%=OpenService.getFarmaciURL()%>/'
        };
    //==============================================================================================================
    $(function() {
        let boxPaziente = $("#suggestionPaziente"), form = "#prescriviRicettaForm",
            boxFarmaciParams = {
                placeholder: "Nome o descrizione",
                minimumInputLength: 3,
                ajax: {
                    delay: 250,
                    type: "GET",
                    url: (request)=> { return URLs.searchFarmacoURL + request.term },
                    processResults: (data)=> {
                        return {
                            results: $.map(data.results, (f)=> {
                                return {
                                    id: f["codice"],
                                    text: f["nome"] + ' ' + f["descrizione"]
                                }
                            })
                        }
                    },
                    error: (err)=>console.log(err)
                }
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
        $(".boxFarmaci").select2(boxFarmaciParams); // init the first farmaci suggestion box
        $(".boxFarmaci").on('select2:select', (selection)=> { // handler event for nodes that have the class 'boxFarmaci'
            idFarmaco = selection.params.data["id"];

            farmaci.push(idFarmaco); // push the id on the array
            console.log(farmaci); // print's the updated array

            document.getElementById("addFarmacoBTN").disabled = false;

            let divFarmaco = $(selection.delegateTarget).parent().parent().parent().parent();
            if($(".boxFarmaci").length > 1) {
                divFarmaco.find('.removeBTN').css("display","unset");
                divFarmaco.find('.selectContainer').removeClass("mb-4").addClass("mb-0");
            } else {
                divFarmaco.find('.selectContainer').removeClass("mb-0").addClass("mb-4");
            }
        });
        $(".boxFarmaci").val(null).trigger('change');
        $(".boxFarmaci").on('select2:unselect', (selection)=> {
            let idFarmacoToRemove = selection.params.data["id"];
            farmaci.splice(farmaci.findIndex(i=> i===idFarmacoToRemove),1);
            idFarmaco = undefined;
            console.log(farmaci);
        });
        //==============================================================================================================
        $("#addFarmacoBTN").on('click',()=> {
            $(".boxFarmaci").find('button').first().remove();

            if($(".boxFarmaci").hasClass("select2-hidden-accessible")) { // checks if select2 is enable
                $(".boxFarmaci").select2('destroy');
                let nrFarmaci = $(".boxFarmaci").length, clone = $(".divFarmaco").first().clone(true);
                $("#containerFarmaci").append(clone);
                clone.find('select').attr('id','suggestionFarmaci' + nrFarmaci);
                clone.find('option').remove().end();
                clone.find('.removeBTN').css("display","unset");

                $(".boxFarmaci").select2(boxFarmaciParams); // re-init the select2 params because destroyed sopra
            }
        });
        //==============================================================================================================
        $(form).on('submit', ()=> { // set the params that need to be send on server-side
            let descrizione = $("#descrizione").val();
            if (descrizione.length > 0 && idPaziente && farmaci.length>0) { // check if variables exists (not undefined)
                addParamToForm("farmaci",JSON.stringify(farmaci),form);
                addParamToForm("idPaziente",idPaziente,form);
                return true; // with true the form do the POST method on server-side
            } else {
                alert("E' richiesto il paziente, almeno un farmaco e una descrizione della ricetta da prescrivere!");
                return false; // with false, the form doesn't send the params; the html page doesn't chan
            }
        });
        //==============================================================================================================
    });
    //==============================================================================================================
    function removeFarmaco(event) {
        if($(".boxFarmaci").length > 1) {
            let divFarmaco = $(event).parent().parent(); // the button is under 2 node's, so we must go to the previouses nodes
            if(divFarmaco.hasClass("divFarmaco")) { // prevents the clone of an empty select2 or unenabled select2
                let idFarmacoToRemove = divFarmaco.find('select').val(); // get the value selected in <select> tag
                if(idFarmacoToRemove) { // prevents bug for empty <select> tag
                    farmaci.splice(farmaci.findIndex(i=> i===idFarmacoToRemove),1);
                    console.log(farmaci);

                    divFarmaco.find('boxFarmaci').select2('destroy');
                }
                divFarmaco.remove(); // remove node from the DOM
            }
        } else {
            alert("impossibile eliminare l'ultima suggestion box rimasta!");
        }
    }
</SCRIPT>