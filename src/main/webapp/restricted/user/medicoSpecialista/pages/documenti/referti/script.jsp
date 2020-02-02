<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.RefertoServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.services.MedicoSpecialistaService" %>

<SCRIPT type="text/javascript">
    $(function() {
        $("#TAB").DataTable({
            serverSide: true,
            ajax: {
                url: "${cp}/<%=MedicoSpecialistaService.getURL()%>/referti",
                data: function(dati) {
                    console.log(dati);
                    return dati;
                },
                error: (e)=>console.log(e)
            },
            order: [[3, "desc"]],
            columns: [
                { data: "idEsame", title: "Cod.", className: 'text-center'},
                { data: "nomeEsame", title: "Esame" },
                { data: "medicoPrescrivente", title: "Medico prescrittore" },
                { data: "dataErogazione", title: "Data erogazione", render: function(data,type,row,meta){
                        return parseAndShowTimestamp(row["dataErogazione"]);
                    } },
                { data: "paziente", title: "Paziente"},
                { data: undefined, title: "Referto", className: 'text-center', orderable: false, searchable: false,
                    render : function(data,type,row,meta) {
                        return (
                            '<a href="${cp}/<%=RefertoServlet.getURL()%>?id=' + row["idEsame"] +
                                    '&idPaziente=' + row["idPaziente"] + '">' +
                                '<button title="Visualizza referto online" class="btn btn-primary" type="button">' +
                                    '<i class="fas fa-eye"></i> Visualizza' +
                                '</button>' +
                            '</a>'
                        );
                    }
                }
            ]
        });
    });
</SCRIPT>