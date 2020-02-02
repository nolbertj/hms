# Healthcare management System

## PROJECT AND REMOTE DATABASE ONLINE UNTIL AUGUST 2020
Open online deployed project [here](https://hms-unitn.herokuapp.com/).

Login accounts: [default password: password]
* Paziente: asia.fosc@yopmail.com [password: Password2]
* Medico specialista: mina.semi@yopmail.com
* Medico di base: lea.burg@yopmail.com
* Farmacia: ALLANGELO@yopmail.com
* Servizio Sanitario Provinciale: azienda.provinciale.servizisanitari@yopmail.com
* Amministratore: admin@email.it
<br><sup>[you can find another users with a simple query]</sup>

<sup>ATTENTION: due to problem's with the `Context path`, all the filters were disabled (commented) in the web.xml'. If a hyperlink doesn't work, please write manually the path in the url bar. If you deploy the project locally, enable filters uncommenting the filters and filter-mapping in the `web.xml`.</sup>

## Descrizione

Questo sito web consente di avere accesso alle prestazioni sanitarie prescritte ed erogate, oltre che ad alcune informazioni riguardanti i pazienti.

Al servizio possono accedere pazienti, medici di base, medici specialisti, farmacie e dipendenti del sistema sanitario provinciale.

<sup>NOTA: Per scelta implementativa, un medico di base / specialista non può essere anche un paziente</sup>

Utilizziamo un database remoto, con host su [AWS RDS](https://aws.amazon.com/it/rds/). Lo schema del database è il più possibile normalizzato, 
anche se in alcune parti si è dovuto optare per delle variabili booleane a indicare quale tabella fare i join.

Per riempire il database abbiamo utilizzato vari dataset (perlopiù delle PA italiane), adattandoli (cioè estraendo alcuni dati) per le nostre esigenze:
* Per le province italiane il [sito dell'Istat](https://www.istat.it/storage/codici-unita-amministrative/Codici-statistici-e-denominazioni-delle-ripartizioni-sovracomunali.zip)
* Per i comuni italiani [questo repo da github](https://github.com/matteocontrini/comuni-json) 
    * Abbiamo inserito solo i comuni presenti attualmente su territorio italiano, mettendo negli id più bassi quelli della regione Trentino-Alto Adige  
* Per i nomi delle persone questo [generatore di anagrafiche](http://www.gaffuri.it/generatore/)
    * Per quanto riguarda luoghi di nascita e di residenza e codici fiscali, li abbiamo corretti mettendo i codici catastali dei nuovi comuni, nati da fusioni negli ultimi anni
* Per l'elenco delle farmacie abbiamo inserito solo quelle delle province di trento e bolzano da [questo](http://www.dati.salute.gov.it/imgs/C_17_dataset_5_download_itemDownload0_upFile.CSV)
    * I numeri di telefono delle farmacie sono inventati
* Per i farmaci, abbiamo utilizzato i dataset dell'aifa, classi A e H per nome comerciale, [qui](http://www.agenziafarmaco.gov.it/content/dati-sulle-liste-dei-farmaci-open-data)
* Per gli esami specialistici, [questo dataset](https://www.dati.lombardia.it/Sanit-/Transcodifica-Codici-prestazioni/7ugz-vcug) della regione Lombardia
    * Da qui abbiamo anche ricavato anche le possibili aree di specializzazione degli esami specialistici
* Ogni medici specialista ha un'area di specializzazione, che determina gli esami da lui erogabili
* Per le sedi delle ambulatori, abbiamo selezionato alcune città trentine e altoatesine a caso, indicando per ognuna una via reale della città
* Abbiamo inserito due sistemi sanitari provinciali: Trento (APSS) e Bolzano (ASDAA)

Le mail sono tutte di [yopmail.com](http://www.yopmail.com/it/), che è un provider di email temporanee, che conserva le mail inviate per 8 giorni.
Quindi sono visualizzabili le mail mandate a tutti gli utenti iscritti al servizio. Le mail vengono inviate da una apposita casella gmail (hms.unitn)


# Funzionalità

> Per tutti
- Poter abilitare il ricordami che, tramite cookie, ricorda l'utente all'ingresso successivo nel sito web, mandando direttamente alla dashboard l'utente
    * La funzione remember me è stata implementata seguendo [questo pattern](https://paragonie.com/blog/2015/04/secure-authentication-php-with-long-term-persistence)
- Poter resettare la password quando viene dimenticata
    * Viene mandata via mail la nuova password provvisoria da utilizzare per loggarsi
- Modificare la password del proprio profilo (una volta fatto il login)
    * Il vincolo di default corrisponde ad una password di minimo 8 caratteri e massimo 32, con una lettera maiuscola, minuscola un numero.
    <br><sup>È comunque possibile cambiare tali impostazioni facilmente tramite il corrispondente file *.properties* (descrizione a seguire)<sup>
    * Quando modificata, viene inviata una mail al paziente con notifica di corretto cambiamento password


> Paziente

- Visualizzare le proprie informazioni personali:
    * Nome, cognome, codice fiscale e sesso di nascita
    * Data e luogo di nascita
    * Città e provincia di residenza
    * Email, numero di telefono principale e di emergenza
    * Foto profilo
    * Elenco delle foto profilo caricate nel tempo con data di scatto
- Cambiare foto profilo:
    * Quando è caricata una nuova foto, viene automaticamente letta la data di scatto dai dati interni (EXIF) della fotografia
- Visualizzare il proprio medico di base:
    * Nome, cognome, foto profilo
    * Sede dell'ambulatorio
    * Numero di telefono
    * Email
- Scegliere e/o cambiare il medico di base:
    * Nella finestra di scelta compaiono solo i medici di base della provincia (diversi da quello attuale) 
- Visualizzare gli esami prescritti in forma tabellare:
    * Id prescrizione
    * Nome dell'esame
    * Area di specializzazione dell'esame
    * Data prescrizione
    * Medico prescrittore
    * Stato (erogato/non erogato)
    * Data erogazione
- Visualizzare le ricette farmaceutiche prescritte:
    * Una ricetta può contenere più farmaci
    * PDF scaricabile della ricetta, con codice QR
    * Se visualizzata via web, la ricetta contiene:
        * Id della ricetta
        * Codice QR riassuntivo della ricetta
        * Stato della ricetta
        * Data prescrizione
        * Medico prescrittore
        * Nome e codice fiscale del paziente
        * Motivazione
        * Tabella con i farmaci prescritti nella ricetta
        * Data erogazione e farmacia erogante (nome e città)
- Visualizzare i referti degli esami erogati
    * PDF scaricabile del referto
    * Se l'esame è prescritto dal servizio sanitario provinciale con un richiamo, il suo nome compare come medico prescrittore
    * Se l'esame è erogato dal servizio sanitario provinciale con un richiamo, il suo nome compare come medico erogatore
- Visualizzare gli esami che possono essere prescritti
    * Se donna non ci saranno gli esami che possono essere prescritti solo a un uomo
- Visulizzare la lista dei propri pagamenti in forma tabellare
    * PDF scaricabile della tabella (tasto esporta)
- Visualizzare l'elenco degli ambulatori della provincia
- Visualizzare in un calendario le date in cui sono stati fatti degli esami specialistici e prescritte le ricette
- Viene mandata una mail nel caso in cui:
    * Un esame viene prescritto
    * Un esame viene erogato, con generazione del referto
    * Una ricetta farmaceutica viene prescritta
    * Una ricetta farmaceutica viene erogata
    * Un pagamento è effettuato

> Medico di base

- Visualizzare le proprie informazioni personali:
    * Nome, cognome, codice fiscale e sesso di nascita
    * Data, luogo e provincia di nascita
    * Email e numero di telefono principale
    * Foto profilo
    * Servizio Sanitario provinciale presso il quale è accreditata
    * Numero di pazienti di cui è medico
    * Nome e sede dell'ambulatorio in cui esercita
- Visualizzare la lista dei farmaci prescrivibili e esami prescrivibili
- Visualizzare elenco dei propri pazienti
- Visualizzare la scheda personale dei propri pazienti, con elenco degli esami e delle ricette
- Prescrivere esami ai propri pazienti
    * Suggestion box per selezionare paziente e esame
- Prescrivere ricette farmaceutiche ai propri pazienti
    * Suggestion box per selezionare paziente e farmaci

> Medici Specialisti

- Visualizzare le proprie informazioni personali:
    * Nome, cognome, codice fiscale e sesso di nascita
    * Data, luogo e provincia di nascita
    * Email
    * Foto profilo
    * Area di specializzazione
- Visualizzare i referti eseguiti
- Visualizzare la scheda di un qualunque paziente iscritto al sistema
    * Suggestion box per selezionare il paziente
- Erogare esami a un qualunque paziente iscritto al sistema
    * Si può includere o meno il pagamento del ticket. In caso di mancato inserimento, comparirà come non pagato al paziente
    * Suggestion box per selezionare paziente e esame
        * Gli esami che compaiono nella suggestion box sono solamente quelli non ancora erogati al paziente della stessa area di specializzazione del medico

> Farmacia
- Visualizzare le proprie informazioni personali:
    * Nome, Comune e provincia
    * Partita IVA
    * Email
    * Foto profilo (stock uguale per tutti)
- Erogare una ricetta farmaceutica di un qualunque paziente:
    * Si può includere o meno il pagamento del ticket. In caso di mancato inserimento, comparirà come non pagato al paziente
    * Suggestion box per selezionare il paziente 
    
> Servizio Sanitario Provinciale

- Visualizzare le proprie informazioni personali:
    * Nome abbreviato e completo, provincia
    * Email
    * Foto profilo
    * Numero di telefono
    * Numero di pazienti
    * Numero di medici di base presenti sul territorio di appartenenza
    * Numero di medici specialisti che visitano in un ambulatorio presente nel territorio di appartenenza
- Erogare esami a un qualunque paziente iscritto al sistema
    * Si può includere o meno il pagamento del ticket. In caso di mancato inserimento, comparirà come non pagato al paziente
    * Suggestion box per selezionare paziente e esame
        * Gli esami che compaiono nella suggestion box sono solamente quelli non ancora erogati al paziente di alcune aree di specializzazione (quelle che nella realtà sono erogati da infermieri o tecnici e che non sono laureati in medicina)
- Generare un report in formato excel di tutti i farmaci erogati ai pazienti della provincia sede del servizio sanitario provinciale
- Prescrivere un richiamo per età
    * Se esame solo per donne, sarà prescritto solo alle donne
- Visualizzare l'elenco dei richiami prescritti
- Visualizzare l'elenco degli esami prescrivibili e dei farmaci prescrivibili
- Visualizzare l'elenco degli ambulatori presenti sulla provincia


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

- ### Preparation

  1. **Clone** this repo on your host using [Git](https://git-scm.com)

     ```console
     $ git clone https://gitlab.com/unitnAB.it/webprog/HMS.git
     ```

  2. **Change** current working **directory**

     ```console
     $ cd HMS
     ```

  3. Create the war file

     ```console
     $ mvn clean install package
     ```

  4. Change current directory

     ```console
     $ cd target
     ```

  5. Copy the war file **HMS.war**

     ```console
     $ ls
     ```

  6. Paste the war file in the folder **CATALINA_HOME/webapps**

     ```console
     $ CATALINA_HOME/webapps
     ```

  7. Change current directory
     ```console
     $ cd CATALINA_HOME/bin
     ```
  8. Start Tomcat server

     > Windows user

     ```console
     $ ./startup.bat
     ```

     > Linux user

     ```console
     $ ./startup.sh
     ```

- ### Restore the database backup (skip if you connect to the AWS-hosted database)

  > Change datatabase options in the file **database.propreties** before you start the database

- Create the database

  1. Open the SQL Shell
  2. Provide the data requested
  3. Enter the command

     ```console
     $ CREATE DATABASE [name]
     ```

     > Where [name] is the database name provided in **database.properties**

  4. Open command line window
  5. Go to PostgreSQL bin folder

     ```console
     $ PG_HOME/bin
     ```

  6. Restore the database
     ```console
        $ psql -U [username] -d [name] -f backup.sql
     ```

## Properties files

| Properties                | Descrizione          |
| ----------------------- | ------------------------------------------------------------------------------------------------- |
| avatar.properties   | contiene le proprietà di configurazione perr le immagini caricate dall'utente |
| database.properties  | contiene le proprietà di configurazione per la connessione al database |
| passwordRequirements.properties | contiene le proprietà di configurazione di come deve essere una password |
| pdf.properties | contiene la massima dimensione accettabile per i file pdf |
| qr.properties | contiene l'estensione che dovranno avere i QR code |


## Built With

- [Java](https://www.java.com) - General purpose computer-programming language
- [Maven](https://maven.apache.org/) - Dependency Management
- [Git](https://git-scm.com) - Distributed version control system
- [Tomcat](https://tomcat.apache.org) - Web Server

> Java Dependencies

| Dependency                                                                                            | Description                                                                                                                                                                                                                |
| ----------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [Java Servlet](https://www.oracle.com/technetwork/java/index-jsp-135475.html)                         | Java servlets can respond to many types of requests, they most commonly implement web containers for hosting web applications on web servers and thus qualify as a server-side servlet web API                             |
| [JSP](https://www.oracle.com/technetwork/java/jsp-138432.html)                                                                 | JavaServer Pages (JSP) technology provides a simplified, fast way to create dynamic web content. JSP technology enables rapid development of web-based applications that are server- and platform-independent |
| [JSTL](https://www.oracle.com/technetwork/java/jstl-137486.html)                                                                 | The JavaServer Pages Standard Tag Library (JSTL) encapsulates as simple tags the core functionality common to many Web applications. JSTL has support for common, structural tasks such as iteration and conditionals, tags for manipulating XML documents, internationalization tags, and SQL tags. It also provides a framework for integrating existing custom tags with JSTL tags.  |
| [Annotation Api](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/package-summary.html) | Provides library support for the Java programming language annotation facility                                                                                                                                             |
| [Apache Commons Configuration](https://commons.apache.org/configuration/)                             | The Commons Configuration software library provides a generic configuration interface which enables a Java application to read configuration data from a variety of sources                                                |
| [Apache Commons IO](https://commons.apache.org/io/)                                                   | Commons IO is a library of utilities to assist with developing IO functionality                                                                                                                                            |
| [Java Mail](https://javaee.github.io/javamail/)                                             | The JavaMail API provides a platform-independent and protocol-independent framework to build mail and messaging applications. The JavaMail API is available as an optional package for use with the Java SE platform and is also included in the Java EE platform.|
| [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/)                                                | PostgreSQL JDBC Driver (PgJDBC for short) allows Java programs to connect to a PostgreSQL database using standard, database independent Java code                                                                          |
| [JBCrypt](https://github.com/jeremyh/jBCrypt)                                                         | jBCrypt is a Java™ implementation of OpenBSD's Blowfish password hashing code                                                                                                                                              |
| [FAST JSON](https://github.com/alibaba/fastjson)                                                      | Fastjson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object.                                             |
| [REST API](https://restfulapi.net/)                                                                   | Descrivono una serie di linee guida e di approcci che definiscono lo stile con cui i dati vengono trasmessi                                                                                                                |
| [SCALR Image](http://javadox.com/org.imgscalr/imgscalr-lib/4.2/org/imgscalr/Scalr.html)               | imgscalr is an simple and efficient best-practices image-scaling and manipulation library implemented in pure Java                                                                                                         |
| [Metadata Extractor](https://drewnoakes.com/code/exif/)               | metadata-extractor lets you access the metadata in digital images and video via a simple API|
| [Apache log4j](https://logging.apache.org/log4j/2.x/)               | Log4J è una libreria Java sviluppata dalla Apache Software Foundation che permette di mettere a punto un ottimo sistema di logging per tenere sotto controllo il comportamento di una applicazione, sia in fase di sviluppo che in fase di test e messa in opera del prodotto finale|
| [IText 7](https://itextpdf.com/en)                                                           | Itext 7 is a powerful PDF Toolkit for PDF generation, PDF programming, handling & manipulation. It is the preferred PDF engine for Java and .NET developers, small Enterprises as well as large Corporations and Government institutions. |
| [Daypilot Lite](https://java.daypilot.org/lite/)                                                           | DayPilot Lite is an open-source version of DayPilot. It can help you build calendar, personal scheduling, and resource booking applications. |
| [Apache POI](https://poi.apache.org/)                                                                 | The Java API for Microsoft Documents|
| [Google ZXing QR Code](https://www.callicoder.com/generate-qr-code-in-java-using-zxing/)              | library to generate QR codes for our application                                                                                                                                                                           |  |

> Javascript libraries

| Library                                                                 | Description                                                                                                                                                                       |
| ----------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [cookieconsent](https://cookieconsent.osano.com/)                       | Cookie Consent is a lightweight JavaScript plugin for alerting users about the use of cookies on your website, it is designed to help you quickly comply with the EU Cookie Law   |
| [datatables](https://datatables.net/)                                   | DataTables is a table enhancing plug-in for the jQuery Javascript library, adding sorting, paging and filtering abilities to plain HTML tables with minimal effort                |
| [jquery](https://jquery.com/)                                           | It makes things like HTML document traversal and manipulation, event handling, animation, and Ajax much simpler with an easy-to-use API that works across a multitude of browsers |
| [select2](https://select2.org/)                                      | Select2 gives you a customizable select box with support for searching, tagging, remote data sets, infinite scrolling, and many other highly used options.|
| [moment.js](https://momentjs.com/)                                      | A lightweight JavaScript date library for parsing, validating, manipulating, and formatting dates                                                                                 |

## Versioning

We use [Git](https://git-scm.com) for versioning.

## Authors

- **Alessandro Brighenti** - alessandro.brighenti@studenti.unitn.it - 193256
- **Nolbert Juarez** - nolbert.juarezvera@studenti.unitn.it - 193610
- **Alessandro Tomazzolli** - a.tomazzolli@studenti.unitn.it - 195254

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

© HMS 2020
