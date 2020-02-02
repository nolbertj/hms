<%@ page import="it.unitn.disi.wp.project.hms.services.PazienteService" %>

<SCRIPT type="text/javascript">
    $(()=> {
        let dt = $("#TAB").DataTable({
            serverSide: true,
            ajax: {
                url: "${cp}/<%=PazienteService.getURL()%>/${USER.id}/esamiPrescritti",
                data: function(dati){ //per comodità filtro i dati prima di caricarli sulla tabella
                    // ma quello che faccio sarà solo stampare su console cosa mi manda il server
                    console.log(dati); //questo è l'oggetto completo che ritorna il server
                    return dati; // dopo aver filtrato i dati li ritorno
                },
                error: (e)=>console.log(e) //se avviene un errore, lo mostro in console
            },
            order: [[4, "desc"]],
            columns: [
                { data: "idPrescrizione", title: "ID", className: 'text-center', orderable: false},
                { data: "codice", title: "Cod. Esame", className: 'text-center' },
                { data: "nome", title: "Nome esame" },
                { data: "area", title: "Area", className: 'text-center' },
                { data: "dataPrescrizione", title: "Data Prescrizione", className: 'text-center', render: function(data,type,row,meta){
                        return parseAndShowDates(row["dataPrescrizione"]);
                    } },
                { data: "prescrivente", title: "Medico Prescrittore", className: 'text-center' },
                { data: undefined, title: "Stato", className: "text-center", orderable:false, searchable:false,
                    render:function(data,type,row,meta) {
                        let span = document.createElement("span");
                        span.style.fontWeight = "bold";
                        span.className = "status";
                        if (row["dataErogazione"] === null ||
                            row["dataErogazione"]==='' ||
                            row["dataErogazione"]==='undefined' ||
                            row["dataErogazione"]===undefined) {
                            span.style.color = "red";
                            span.innerText = "Non erogato";
                        }
                        else {
                            span.style.color = "green";
                            span.innerText = "Erogato";
                        }
                        return span.outerHTML;
                    }
                },
                { data: "dataErogazione", title: "Data erogazione", className: 'text-center', render: function(data,type,row,meta){
                        return parseAndShowDates(row["dataErogazione"]);
                    } }
            ]
        });

        if ($(window).width() < 992){
            dt.colReorder.move(0,3);
            dt.colReorder.move(0,1);
        }
    });
</SCRIPT>