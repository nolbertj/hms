function showCookieConsent() {
    if(readCookie("cookieconsent_status")===null || readCookie("cookieconsent_status")===undefined ) {
        window.cookieconsent.initialise({
            "palette": {
                "popup": {
                    "background": "#3296c8"
                },
                "button": {
                    "background": "#fff",
                    "text": "#3296c8"
                }
            },
            "theme": "classic",
            "content": {
                "message": "Questo sito usa alcuni cookie tecnici per migliorare la tua esperienza di navigazione. Continuando a navigare su questo sito accetti il loro impiego.",
                "dismiss": "Chiudi",
                "link": "Maggiori informazioni",
                "href": ""
            }
        });
    }
}