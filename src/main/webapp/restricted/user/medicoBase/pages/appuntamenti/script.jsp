<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.SchedaPazienteServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.services.MedicoBaseService" %>

<SCRIPT type="text/javascript">
    $(()=> {
        let calendar = new DayPilot.Calendar("calendar");
        calendar.backendUrl = '${cp}/${appuntamentiURL}';
        calendar.ajaxError = (err) => document.body.innerHTML=err.responseText;
        calendar.cssOnly = true;
        calendar.locale = "it-it";
        calendar.cssClassPrefix = "calendar";
        calendar.allowEventOverlap = false;

        if($(window).width() < 992) {  // mobile view
            calendar.viewType = "Day";
            calendar.tapAndHoldTimeout = 200;
            calendar.eventTapAndHoldHandling = 'Disabled'; //tenendo premuto lo schermo touch apro il ContextMenu
            calendar.onEventClick = function(args) {
                let data = args.e.data;
                alert(data["text"]+'\n' + data["start"] + "\nC.F.: " + data["resource"]);
            };
        }
        else { // desktop view
            calendar.viewType = "Week";
            calendar.bubble = new DayPilot.Bubble({
                onLoad: function(args) {
                    args.async = true;  // notify manually using .loaded()
                    setTimeout(()=> {
                        let data = args.source.data,
                            nomeCognome = data.tag[0],
                            cf = data["resource"];
                        args.html = (
                            '<span>Paziente:' + nomeCognome + '</span><br>' +
                            '<span>C.F.: ' + cf + '</span>'
                        );
                        args.loaded();
                    }, 10);
                }
            });
            calendar.eventRightClickHandling = "ContextMenu"; // al click eseguo il comando contextMenu (vedi sotto)
            calendar.contextMenu = new DayPilot.Menu({
                items: [
                    {   text:"Prescrivi ricetta",
                        onClick: function(args){
                            window.open("${cp}/${prescriviRicettaURL}","_blank");
                        }
                    },
                    {   text:"Prescrivi esame",
                        onClick: function(args){
                            window.open("${cp}/${prescriviEsameURL}","_blank");
                        }
                    },
                    {   text:"Visualizza scheda paziente",
                        onClick: (args)=> {
                           let idPaziente = args.source.data.tag[1],
                               urlScheda = '${cp}/<%=SchedaPazienteServlet.getURL()%>?idPaziente=';
                           window.open(urlScheda + idPaziente, "_blank");
                        }
                    },
                    {   text:"Modifica appuntamento",
                        onClick: (e)=> {
                            new DayPilot.Modal.prompt("Seleziona il paziente:","",{theme:"modal_rounded"});
                            setTimeout(()=> { // piuttosto che creare una modale, modifico quella di default,
                                $('.modal_rounded_input').remove(); // ma devo aspettare qualche ms per lasciare renderizzare quella di default
                                $('.modal_rounded_content').first().after(
                                    '<div class="selectContainer" style="background: none">' +
                                        '<select id="suggestionPaziente" class="form-control form-control-sm" </select>' +
                                    '</div>'
                                );
                                $(".modal_rounded_buttons").first().prepend(
                                    '<div class="form-group flex-column mt-4">' +
                                        '<label for="motivo">Motivo</label>' +
                                        '<textarea class="form-control" id="motivo" name="motivo"></textarea>' +
                                    '</div>'
                                );
                                let paziente = undefined, idPaziente = undefined, pazienti = [], boxPaziente = $("#suggestionPaziente");
                                boxPaziente.select2({
                                    placeholder: "Nome, cognome o codice fiscale",
                                    ajax: {
                                        delay: 250,
                                        type: "GET",
                                        url: (request)=> { return '${cp}/<%=MedicoBaseService.getURL()%>/${USER.id}/pazienti/' + request.term; },
                                        processResults: (data)=> {
                                            pazienti = data.results; // store all results obtained in 'pazienti' variable
                                            return {
                                                results: [
                                                    {
                                                        text: "Nome", // title of the <optgroup>
                                                        children: $.map(data.results, (pazient)=> { // build <optgroup> with only "nome"
                                                            return {
                                                                id: pazient["id"],
                                                                text: pazient["nome"]
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

                                let motivo = '';
                                $("#motivo").on('keyup',()=> {
                                    motivo = $("#motivo").val();
                                });

                                $(".modal_rounded_ok").first().on('click',()=> {
                                    if(idPaziente && motivo) {
                                        let jsonData = {
                                            "idAppuntamento": e.source.data.id,
                                            "idPaziente": idPaziente,
                                            "motivo": motivo
                                        };
                                        calendar.commandCallBack('update',jsonData);
                                    }
                                });
                            },100);
                        }
                    },
                    {   text: "Modifica motivo",
                        onClick: (e)=> {
                            new DayPilot.Modal.prompt("Motivo:","",{theme:"modal_rounded"});
                            setTimeout(()=> {
                                let style = $(".modal_rounded_input").first().children().attr('style');
                                let textArea = $(document.createElement('textarea')).attr('style',style);
                                textArea.attr('id',"motivo");
                                textArea.attr('class',"form-control");
                                $(".modal_rounded_input").first().children().replaceWith(textArea);

                                let motivo = '';
                                $("#motivo").on('keyup',()=> {
                                    motivo = $("#motivo").val();
                                });

                                $(".modal_rounded_ok").first().on('click',()=> {
                                    if(motivo){
                                        let jsonData = {
                                            "idAppuntamento": e.source.data.id,
                                            "motivo": motivo
                                        }
                                        calendar.commandCallBack('edit',jsonData);
                                    }
                                });
                            },100);
                        }
                    },
                    {   text: "Cancella appuntamento",
                        onClick: (args)=> {
                            if (confirm("Confermi la cancellazione dell'appuntamento?")===true) {
                                let jsonData = {
                                    "idAppuntamento": args.source.data.id
                                }
                                calendar.commandCallBack('delete',jsonData);
                            }
                        }
                    }
                ]
            });
        }

        calendar.crosshairType = "Full";
        calendar.dayBeginsHour = 8;
        calendar.dayEndsHour = 19;
        calendar.businessBeginsHour = 8;
        calendar.businessEndsHour = 19;
        calendar.cellDuration = 30; //minuti
        calendar.columnWidthSpec = "Fixed";
        calendar.columnWidth = 200;
        calendar.timeFormat = "Clock24Hours";
        calendar.heightSpec = "Parent100Pct";
        calendar.height = 200;
        calendar.headerHeight = 40;
        calendar.weekStarts = 0;
        calendar.shadow = 'Fill';
        calendar.timeRangeSelectedHandling = 'CallBack';
        calendar.eventMoveHandling = 'CallBack';
        calendar.eventResizeHandling = 'CallBack';
        calendar.eventClickHandling = 'Disabled';
        calendar.eventDeleteHandling = 'Disabled';
        calendar.eventArrangement = "Full";
        calendar.showToolTip = false;

        calendar.init(); //installo gli attributo dichiarati sopra

        //ciascun pulsante avrà un compito da svolegere lato server tramite gli attributi dayPilot(previous,today,next)
        $("#previous").on('click',(e)=>calendar.commandCallBack('previous',e));
        $("#today").on('click',(e)=>calendar.commandCallBack('today',e));
        $("#next").on('click',(e)=>calendar.commandCallBack('next',e));

        let picker = new DayPilot.DatePicker();
        picker.theme = "datepicker";
        picker.target = "choose";
        picker.locale = "it-it";
        picker.pattern = "dd-MM-yyyy";
        picker.onTimeRangeSelect = (args)=> calendar.commandCallBack('navigate',args);
        picker.init();

        $("#choose").on('click', function() {
            picker.show();
        });

        setTimeout(()=>document.getElementById("today").click(),100);
        //==============================================================================================================
        document.getElementsByClassName("calendar_corner_inner")[0].nextElementSibling.remove(); // rimuovo il logo "demo"
        document.getElementsByClassName("calendar_main")[0].firstElementChild.classList = "table-panel"; // assegno una classe di table.css per non riscriverla
    });
</SCRIPT>