<%@ page import="it.unitn.disi.wp.project.hms.services.OpenService" %>

<SCRIPT type="text/javascript">

    $(function() {

        $("#TAB").DataTable({
            serverSide: true,
            ajax: {
                url: "${cp}/<%=OpenService.getFarmaciURL()%>",
                data: function(dati){
                    console.log(dati);
                    return dati;
                },
                error: (e)=>console.log(e)
            },
            order: [[0,"asc"]],
            columns: [
                { "data": "nome", title:"Nome", className: 'text-center' },
                { data: "prezzo", title: "Prezzo", className: "text-center" , render : function(data,type,row,meta){
                        let span = document.createElement("span");
                        // metto il prezzo con la valuta
                        let prezzo = parseFloat(row["prezzo"]).toFixed(2); // faccio cast da string a float con 2 decimali
                        span.innerHTML = prezzo.toString() + ' &euro;';
                        return span.outerHTML;
                    }
                },
                { "data": "descrizione", title:"Descrizione", className: "text-center" }
            ],
            pageLength: 100
        });

    });

</SCRIPT>