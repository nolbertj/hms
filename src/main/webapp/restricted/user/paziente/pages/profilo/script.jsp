<%@ page import="it.unitn.disi.wp.project.hms.services.PazienteService" %>

<SCRIPT type="text/javascript">
    $(function() {
        $("#TAB").DataTable({
            serverSide: true,
            ajax: {
                url: "${cp}/<%=PazienteService.getURL()%>/${USER.id}/avatars",
                data: function (dati) {
                    console.log(dati);
                    return dati;
                },
                error: (e)=>console.log(e)
            },
            order: [[1,"desc"]],
            columns: [
                { data: "filename", title: "Foto", orderable: false,
                    render : function(data,type,row,meta) {
                        let path = '${img}' + '/restricted/' + '${userFoldername}' + '/${USER.getUsername()}/' + row["filename"].toString();
                        let img = new Image();
                        img.setAttribute("src", path);
                        img.setAttribute("alt","User photo of " + row["timeStamp"]);

                        return img.outerHTML;
                    }
                },
                { data: "timeStamp", title: "Data caricamento", width: '40%',
                    render : function(data,type,row,meta) {
                        return parseAndShowTimestamp(row["timeStamp"]);
                    }
                }
            ]
        });
    });
</SCRIPT>
