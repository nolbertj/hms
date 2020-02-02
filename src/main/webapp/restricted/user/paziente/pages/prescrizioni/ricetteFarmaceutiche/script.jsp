<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.RicettaServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.services.PazienteService" %>

<SCRIPT type="text/javascript">
    $(()=> {
        let dt = $("#TAB").DataTable({
            processing: false,
            serverSide: true,
            ajax: {
                url: "${cp}/<%=PazienteService.getURL()%>/${USER.id}/ricetteFarmaceutiche",
                data: function(dati){
                    //console.log(dati);
                    return dati;
                },
                error: (e)=>console.log(e)
            },
            order: [[4,"desc"]],
            columns: [
                { data: "codice", title: "Codice prescrizione", className: "text-center" },
                { data: "dataPrescrizione", title: "Data prescrizione", className: "text-center", render: function(data,type,row,meta){
                        return parseAndShowTimestamp(row["dataPrescrizione"]);
                    } },
                { data: "medicoPrescrittore", title: "Medico prescrittore" },
                { data: "totale", title: "NrF", className: "text-center" },
                { data: "dataErogazione", title: "Data erogazione", className: "text-center", render: function(data,type,row,meta){
                        return parseAndShowTimestamp(row["dataErogazione"]);
                    }},
                { data: "azioni", title: "Azioni", className: "text-center", orderable: false,
                    render : function(data,type,row,meta) {

                        let params=[
                            {
                                name: "referto",
                                value: "false"
                            },
                            {
                                name: "ricetta",
                                value: "true"
                            },
                            {
                                name: "pagamenti",
                                value: "false"
                            }
                        ];
                        $('#PDFGen').on('submit', () =>{ //listen for submit event
                            $.each(params, function(i,param){
                                $('<input>').attr('type', 'hidden')
                                    .attr('name', param.name)
                                    .attr('value', param.value)
                                    .appendTo('#PDFGen');
                            });

                            return true;
                        });
                        let downloadBTN = (
                            '<button title="Scarica ricetta" type="submit" class="btn btn-success" ' +
                                    'form="PDFGen" name="idRicetta" value="'+ row["codice"] + '">' +
                                '<i class="fas fa-download"></i> ' +
                            '</button>'
                        );

                        let urlRicetta = '<%=RicettaServlet.getURL()%>';

                        let visualizzaBTN = (
                            '<a href="' + '${cp}/' + urlRicetta + '?id=' + row["codice"] + "&idPaziente=" + '${USER.id}' + '">' +
                                '<button title="Visualizza ricetta online" class="btn btn-primary" type="button">' +
                                    '<i class="fas fa-eye"></i> ' +
                                '</button>' +
                            '</a>'
                        );

                        let buttonsContainer = (
                            '<div class="btn-group shadow-sm" role="group">' +
                                downloadBTN + visualizzaBTN +
                            '</div>'
                        );

                        return buttonsContainer;
                    }
                }
            ]
        });

        if ($(window).width() < 992){
            dt.colReorder.move(0,1);
        }
    });
</SCRIPT>