var paziente = [
        {
            id: "dashboardPaziente",
            title: "Dashboard",
            text: "Interfaccia di presentazione che permette al paziente di collegarsi direttamente alle pagine più frequenti."
        },
        {
            id: "profiloPaziente",
            title: "Profilo",
            text: "Informazioni personale del paziente e possibilità di cambiare la password"
        },
        {
            id: "modificaProfilo",
            title: "Modifica profilo",
            text: "Da qui il paziente può modificare i contatti (principale) e di emergenza e caricare una nuova foto profilo"
        },
        {
            id: "modificaMedico",
            title: "Cambia medico di base",
            text: "Il paziente può selezionare un nuovo medico di base"
        },
        {
            id: "esamiPrescrittiPaziente",
            title: "Esami prescritti",
            text: "Il paziente può vedere la lista degli esami prescritti, sia da un medico di base che dal servizio sanitario a cui appartiene"
        },
        {
            id: "ricette",
            title: "Ricette farmaceutiche",
            text: "La lista delle ricette farmaceutiche prescritte a un paziente"
        },
        {
            id: "ricetta",
            title: "Ricetta farmaceutica",
            text: "Le informazioni su una ricetta farmaceutica: data di prescrizione e erogazione, la farmacia che l'ha erogata e i farmaci prescritti nella ricetta"
        },
        {
            id: "refertiPaziente",
            title: "Referti",
            text: "Lista dei referti disponibili per il paziente"
        },
        {
            id: "referto",
            title: "Referto",
            text: "Le informazioni su un referto per il paziente: data di erogazione esame, anamnesi, conclusioni e medico erogante. Sia in PDF che via web"
        },
        {
            id: "esamiPrescrivibiliPaziente",
            title: "Esami prescrivibili",
            text: "Mostra una elenco dei tutti gli esami prescrivibili in base al sesso del paziente"
        },
        {
            id: "documentiPaziente",
            title: "Altri documenti",
            text: "Presenta la possibilit&agrave; di salvare altre tipologie di documenti personali legati alla salute."
        },
        {
            id: "pagamenti",
            title: "Lista pagamenti",
            text: "La lista dei pagamenti di esami e farmaci da fare e quelli fatti"
        },
        {
            id: "ambulatoriPaziente",
            title: "Lista ambulatori",
            text: "Mostra una lista di ambulatori presenti nella provincia di residenza del paziente."
        },
        {
            id: "calendario",
            title: "Calendario appuntamenti",
            text: "Espone lo storico degli appuntamenti passati e quelli avvenire."
        }
    ],
    medicoBase = [
        {
            id: "dashboardMedicoB",
            title: "Dashboard",
            text: "Interfaccia di presentazione che permette al medico di base di collegarsi direttamente alle pagine più frequenti."
        },
        {
            id: "profiloMedicoB",
            title: "Profilo",
            text: "Presenta i dati anagrafici, l'ambulatorio di riferimento cui si presta servizio e la possiblit&agrave " +
                "di cambiare la propria password."
        },
        {
            id: "pazienti",
            title: "Lista pazienti",
            text: "Elenco dei pazienti seguiti dal medico di base"
        },
        {
            id: "prescriviEsame",
            title: "Prescrivi esame",
            text: "Possibilità di prescrivere un esame a uno dei pazienti che il medico di base segue. Il paziente e l'esame vengono selezionati con una suggestion box"
        },
        {
            id: "prescriviRicetta",
            title: "Prescrivi ricetta farmaceutica",
            text: "Possibilità di prescrivere una ricetta farmaceutica a uno dei pazienti che il medico di base segue. Il paziente e i farmaci vengono selezionati con una suggestion box"
        },
        {
            id: "esamiPrescrivibiliMB",
            title: "Esami prescrivibili",
            text: "Mostra una dataset di tutti gli esami prescrivibili."
        },
        {
            id: "farmaci",
            title: "Lista farmaci",
            text: "Mostra un dataset di tutti i farmaci disponibili, con descrizione e prezzo suggerito."
        },
        {
            id: "appuntamenti",
            title: "Gestione appuntamenti",
            text: "Mostra un calendario interattivo che permette di visualizzare, effettuare una prenotazione, cancellazione o modifica " +
                "di un appuntamento (solo nella versione web). Nella versiona mobile sar&agrave; possibile solo " +
                "visualizzare gli appuntamenti."
        }
    ],
    admin = [
        {
            id: "profiloAdmin",
            title: "Profilo",
            text: "Presenta esclusivamente la possibilit&agrave; di cambiare la propria password."
        },
        {
            id: "queryTool",
            title: "Query Tool",
            text: "Presenta la possibilit&agrave; di effettuare delle query e di ottenere la risposta dei dati in una tabella " +
                "dove al suo interno sar&agrave; possibile effettuare un ordinamento istantaneo in base alla colonna scelta, " +
                "nonchè cercare immediatamente, tramite l'apposita casella, una specifica stringa."
        }
    ],
    medicoSpecialista = [
        {
            id: "dashboardMedicoSpec",
            title: "Dashboard",
            text: "Interfaccia di presentazione che permette al medico specialista di collegarsi direttamente alle pagine più frequenti."
        },
        {
            id: "profiloMedicoSpec",
            title: "Profilo",
            text: "Presenta i dati anagrafici e la possiblità di cambiare la propria password."
        },
        {
            id: "refertiMS",
            title: "Lista referti",
            text: "Presenta il dataset di tutti i referti erogati dal medico specialista."
        },
        {
            id: "cercaPaziente",
            title: "Cerca paziente",
            text: "Interfaccia per cercare un paziente con suggestion box. è restituita una card di anagrafica del paziente e l'elenco degli esami e ricette che sono stati prescritti al paziente"
        },
        {
            id: "compilaReferto",
            title: "Compila referto",
            text: "Interfaccia per compilare un referto, una volta selezionato il paziente e l'esame con suggestion box. è presente anche un box per dire se il paziente paga o meno l'esame"
        }
    ],
    farmacia = [
        {
            id: "dashboardFarmacia",
            title: "Dashboard",
            text: "Interfaccia di presentazione che permette alla farmacia di collegarsi direttamente alle pagine più frequenti."
        },
        {
            id: "profiloFarmacia",
            title: "Profilo",
            text: "Dati sintetici della farmacia e possibilità di cambiare la password"
        },
        {
            id: "erogaRicetta",
            title: "Eroga ricetta",
            text: "Interfaccia per erogare una ricetta. Una volta selezionato il paziente, compare l'elenco delle ricette inevase del paziente"
        }
    ],
    ssp = [
        {
            id: "dashboardSsp",
            title: "Dashboard",
            text: "Interfaccia di presentazione che permette al servizio sanitario di collegarsi direttamente alle pagine più frequenti."
        },
        {
            id: "profiloSsp",
            title: "Profilo",
            text: "Dati sintetici del servizio sanitario provinciale (tra cui numero di pazienti, di medici di base e specialisti presenti sul territorio) e possibilità di cambiare la password"
        },
        {
            id: "richiami",
            title: "Lista richiami",
            text: "L'elenco dei richiami prescritti dal servizio sanitario, con il range dell'età"
        },
        {
            id: "prescriviRichiamo",
            title: "Prescrivi richiamo",
            text: "Possibilità di prescrivere un richiamo, selezionando un esame con suggestion box e specificando età di inizio e fine"
        },
        {
            id: "generaReport",
            title: "Genera Report",
            text: "Generare un report in formato excel dei farmaci erogati in una certa giornata"
        },
        {
            id: "compilaRefertoSsp",
            title: "Compila referto",
            text: "Interfaccia per compilare un referto, una volta selezionato il paziente e l'esame con suggestion box. è presente anche un box per dire se il paziente paga o meno l'esame"
        },
        {
            id: "ambulatoriSsp",
            title: "Lista ambulatori",
            text: "Lista degli ambulatori presenti sul territorio della provincia del servizio sanitario"
        }
    ];