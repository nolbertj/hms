<%@ page import="it.unitn.disi.wp.project.hms.services.MedicoBaseService" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.SchedaPazienteServlet" %>

<SCRIPT type="text/javascript">
    $(function() {

        $("#TAB").DataTable({
            serverSide: true,
            ajax: {
                url: "${cp}/<%=MedicoBaseService.getURL()%>/${USER.id}/pazienti",
                data: function(dati){
                    console.log(dati);
                    return dati;
                },
                error: (e)=>console.log(e)
            },
            order: [[3,"asc"]],
            searchable: false,
            columns: [
                { data: "nome", title: "Nome", class: 'text-center'},
                { data: "cognome", title: "Cognome", class: 'text-center'},
                { data: "codFiscale", title: "Codice Fiscale", class: 'text-center' },
                { data: "ultimaVisita", title: "Ultima visita", class: 'text-center',render: function(data,type,row,meta){
                        return parseAndShowDates(row["ultimaVisita"]);
                    } },
                { data: "ultimaRicetta", title: "Ultima ricetta", class: 'text-center',render: function(data,type,row,meta){
                        return parseAndShowDates(row["ultimaRicetta"]);
                    } },
                { data: undefined, title: "Azioni", className: 'text-center', orderable: false, searchable: false,
                    render : function(data,type,row,meta) {
                        return (
                            '<a target="_blank" href="${cp}/<%=SchedaPazienteServlet.getURL()%>?idPaziente=' + row["id"] + '">' +
                                '<button title="Visualizza scheda paziente" class="btn btn-primary" type="button">' +
                                    '<i class="fas fa-eye"></i>&nbsp;Visualizza scheda' +
                                '</button>' +
                            '</a>'
                        );
                    }
                }
            ]
        });

    });
    //======================================================================================================================
</SCRIPT>