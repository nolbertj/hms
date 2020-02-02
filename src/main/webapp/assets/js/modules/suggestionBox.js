/***********************************************************************************
 * Code extracted from an official Select2 issue on GitHub
 * https://github.com/select2/select2/issues/3153
 ************************************************************************************/
var query = {}; //serve per salvare temporaneamente quanto digitato

//aggiunge una classe alla porzioni di testo che corrisponde a quanto digitato
//es: "si" --> sottilinea "si" in "asia".
//Posso cambiare il css/sottolineatura tramite classe ".select2-rendered__match" nel file css
function markMatch (text, term) {

    text = text.toString(); // prevents numbers parsing errors

    // Find where the match is
    var match = text.toUpperCase().indexOf(term.toUpperCase());

    var $result = $('<span></span>');

    // If there is no match, move on
    if (match < 0) {
        return $result.text(text);
    }

    // Put in whatever text is before the match
    $result.text(text.substring(0, match));

    // Mark the match
    var $match = $('<span class="select2-rendered__match"></span>');
    $match.text(text.substring(match, match + term.length));

    // Append the matching text
    $result.append($match);

    // Put in whatever is after the match
    $result.append(text.substring(match + term.length));

    return $result;
}
/***********************************************************************************/
/************************************************************************************
 * Default options of all select2 boxes
 ***********************************************************************************/
$.fn.select2.defaults.set("minimumInputLength",1);
$.fn.select2.defaults.set("allowClear",true);
$.fn.select2.defaults.set("templateResult",function(item) {
    // No need to template the searching text
    if (item.loading) {
        return item.text;
    }
    let term = query.term || '';
    let $result = markMatch(item.text, term); //mi serve per sottolineare le lettere digitate
    return $result;
});
$.fn.select2.defaults.set("language", {
    noResults: ()=>{return 'Nessuna corrispondenza trovata'},
    searching: function (params) {
        // Intercept the query as it is happening
        query = params;
        return 'Ricerca in corso...';
    }
});