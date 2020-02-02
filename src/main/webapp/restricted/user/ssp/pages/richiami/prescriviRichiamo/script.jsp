<%@ page import="it.unitn.disi.wp.project.hms.services.OpenService" %>
<SCRIPT type="text/javascript">
    $(()=> {
        let codEsame = undefined;
        //==============================================================================================================
        $("#esame").select2({
            placeholder: "Nome, codice o descrizione",
            ajax: {
                delay: 250,
                type: "GET",
                url: (req)=> {
                    return '${cp}/<%=OpenService.getEsamiPrescrivibiliURL()%>/' + req.term;
                },
                processResults: (data)=> {
                    return {
                        results: [
                            {
                                text: "Nome",
                                children: $.map(data.results, (esame)=> {
                                    return {
                                        id: esame["codice"],
                                        text: esame["nome"]
                                    }
                                })
                            },
                            {
                                text: "Area",
                                children: $.map(data.results, (esame)=> {
                                    return {
                                        id: esame["codice"],
                                        text: esame["area"]
                                    }
                                })
                            }
                        ]
                    }
                }
            },
            error: (err)=>console.log(err)
        });

        $("#esame").on('select2:select', (selection)=> { codEsame = selection.params.data["id"] });
        $("#esame").on('select2:unselect', ()=> { codEsame = undefined  });

        $("#prescriviRichiamoForm").on('submit',()=> {
             let response = false;

             if($("#etaFine").val() < $("#etaInizio").val()) {
                 alert("L'eta finale non puo' essere minore di quella iniziale!");
             } else {
                 response = true;
             }

             if(codEsame && response) {
                 console.log(codEsame);
                 addParamToForm("codEsame",codEsame,"#prescriviRichiamoForm");
                 response = true;
             } else {
                 alert("E' richiesto un esame da prescrivere");
                 response = false;
             }
             return response;
        });
        //==============================================================================================================
        if($(window).width() < 992) {
            $(".col-2").removeClass("col-2").addClass("col-6");
        }
    });
</SCRIPT>