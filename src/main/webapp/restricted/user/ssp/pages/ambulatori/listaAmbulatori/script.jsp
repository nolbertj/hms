<%@ page import="it.unitn.disi.wp.project.hms.services.PazienteService" %>
<%@ page import="it.unitn.disi.wp.project.hms.services.SspService" %>

<SCRIPT type="text/javascript">

    $(function() {

        $("#TAB").DataTable({
            serverSide: true,
            ajax: {
                url: "${cp}/<%=SspService.getURL()%>/${USER.id}/listaAmbulatori",
                data: function(dati){ //per comodità filtro i dati prima di caricarli sulla tabella
                    // ma quello che faccio sarà solo stampare su console cosa mi manda il server
                    console.log(dati); //questo è l'oggetto completo che ritorna il server
                    return dati; // dopo aver filtrato i dati li ritorno
                },
                error: (e)=>console.log(e) //se avviene un errore, lo mostro in console
            },
            order: [[2, "desc"]],
            columns: [
                { data: "denominazione", title: "Ambulatorio", className: 'text-center'},
                { data: "indirizzo", title: "Indirizzo", className: 'text-center', orderable:false},
                { data: "citta", title: "Citta" },
                { data: "provincia", title: "Provincia", className: 'text-center', orderable:false },
                { data: "contattoTelefonico", title: "Contatto principale", className: 'text-center', orderable:false}
            ]
        });

    });

</SCRIPT>
