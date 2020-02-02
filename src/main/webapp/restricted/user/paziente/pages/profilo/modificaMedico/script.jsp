<%@ page import="it.unitn.disi.wp.project.hms.services.PazienteService" %>

<SCRIPT type="text/javascript">

    $(function() {
        let dt = $("#TAB").DataTable({
            serverSide: true,
            ajax: {
                url: "${cp}/<%=PazienteService.getURL()%>/${USER.id}/medici",
                data: function(dati){ //per comodità filtro i dati prima di caricarli sulla tabella
                    // ma quello che faccio sarà solo stampare su console cosa mi manda il server
                    console.log(dati); //questo è l'oggetto completo che ritorna il server
                    return dati; // dopo aver filtrato i dati li ritorno
                },
                error: (e)=>console.log(e) //se avviene un errore, lo mostro in console
            },
            order: [[2, "asc"]],
            columns: [
                { data: "id", visible: "false", searchable: "false", orderable:false},
                { data: "nome", title: "Nome", className: 'text-center', orderable: false},
                { data: "cognome", title: "Cognome", className: 'textCenter' },
                { data: "dataNascita", title: "Data di nascita", className: 'textCenter', render: function(data,type,row,meta){
                        return parseAndShowDates(row["dataNascita"]);
                    }},
                { data: "sesso", title: "Sesso", className: 'textCenter' },
                { data: "ambulatorio.denominazione", title: "Ambulatorio", className: 'textCenter' },
                { data: "ambulatorio.indirizzo", title: "Indirizzo", className: 'textCenter', orderable: false },
                { data: "ambulatorio.contattoTelefonico", title: "Telefono", className: 'textCenter', orderable: false, searchable: false},
                { data: undefined, title: "Scegli", className: 'text-center', orderable: false, searchable: false,
                    render : function(data,type,row,meta) {
                        return (
                            '<button title="Scegli Medico" type="submit" class="btn btn-primary choose-btn" ' +
                                    'form="editMedic" name="idMedico" value="'+row["id"] + '">' +
                                '<i class="fas fa-user-md"></i> SCEGLI' +
                            '</button>'
                        );
                    }
                }
            ]
        });
        dt.column(0).visible(false);
    });

</SCRIPT>