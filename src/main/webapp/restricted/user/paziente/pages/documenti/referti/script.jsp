<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.RefertoServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.services.PazienteService" %>

<SCRIPT type="text/javascript">

    $(function() {

        $("#TAB").DataTable({
            serverSide: true,
            ajax: {
                url: "${cp}/<%=PazienteService.getURL()%>/${USER.id}" + "/referti",
                data: function(dati){ //per comodità filtro i dati prima di caricarli sulla tabella
                    // ma quello che faccio sarà solo stampare su console cosa mi manda il server
                    console.log(dati); //questo è l'oggetto completo che ritorna il server
                    return dati; // dopo aver filtrato i dati li ritorno
                },
                error: (e)=>console.log(e) //se avviene un errore, lo mostro in console
            },
            order: [[3, "desc"]],
            columns: [
                { data: "idEsame",              title: "Cod.",               className: 'text-center', orderable: false },
                { data: "nomeEsame",            title: "Esame" },
                { data: "medicoPrescrivente",   title: "Medico prescrittore",className: 'text-center' },
                { data: "dataErogazione",     title: "Data erogazione",    className: 'text-center', render: function(data,type,row,meta){
                    return parseAndShowTimestamp(row["dataErogazione"]);
                    } },
                { data: "medicoEsecutore",      title: "Medico esecutore",   className: 'text-center' },
                { data: undefined, searchable: false, title: "Risultati",    className: 'text-center', orderable: false,
                    render: function(data,type,row,meta) {
                        /*
                        * Affinchè il pulsante richiami la PDFServlet e dia dei parametri, deve essere
                        * di tipo type="submit". Per passare i parametri alla servlet sarà sufficiente
                        * porre la coppia [name,value].
                        *
                        * NOTA: l'attributo "form" si può anche omettere dal momento che nella pagina html
                        * è presente un solo form. Nel caso in cui fosse presenti più form,
                        * si rammenta di specificare il form a cui farà riferimento il bottone altrimenti
                        * al click, i parametri verranno passati al primo form (quello dichiarato più in alto).
                        * Quindi per comodità specificare sempre il form!
                        *
                        * ATTENZIONE: non serve passare l'id dell'utente in sessione in questi parametri
                        * altrimenti l'utente potrà fare "ispeziona pagina html" e mettere l'id di un altro
                        * utente. Nella servlet sarà sifficiente prendere l'id dell'utente tramite gli attributi
                        * di sessione, ovvero:
                        * Integer idUtente = ((Paziente)request.getSession().getAttribute(Attr.USER)).getId();
                        */
                        let params=[
                            {
                                name: "referto",
                                value: "true"
                            },
                            {
                                name: "ricetta",
                                value: "false"
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
                            '<button title="Scarica referto" type="submit" class="btn btn-success" ' +
                                    'form="PDFGen" name="idEsame" value="'+ row["idEsame"] + '">' +
                                '<i class="fas fa-download"></i> ' +
                            '</button>'
                        ); // NOTA BACK-END: se la servlet non esiste, al click non succede nulla

                        /*NOTA sull'attribute type: può essere omesso ma in questo caso
                        * qui sotto va lasciato perchè se no viene chiamato l'attributo type più vicion,
                        * ovvero quello del form che ha type=submit. Quindi per rendere il pulsante
                        * un pulsante-hyperlink, va specificato type=button affinchè l'href funzioni!
                        */

                        let urlReferto = '<%=RefertoServlet.getURL()%>';

                        let visualizzaBTN = (
                            '<a href="${cp}/' + urlReferto + '?id=' + row["idEsame"] + '&idPaziente='
                                + '${USER.id}' + '">' +
                                '<button title="Visualizza referto online" class="btn btn-primary" type="button">' +
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

    });

</SCRIPT>