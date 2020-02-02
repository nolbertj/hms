console.log("loading psw.js ...");
//======================================================================================================================
let minLength, maxLength, atLeastOneNumber, atLeastOneMaiusc, atLeastOneMinusc, atLeastOneSpecialCharacter, acceptSpecialCharacters;

function initPasswordAttributes(min, max, num, maiusc, minusc, oneSpecial, special) {
    minLength = min;
    maxLength = max;
    atLeastOneNumber = num;
    atLeastOneMaiusc = maiusc;
    atLeastOneMinusc = minusc;
    atLeastOneSpecialCharacter = oneSpecial;
    acceptSpecialCharacters = special;
}

function atLeastOneNum(str) {
    return /[0-9]/.test(str);
}

function atLeastOneUpper(str) {
    return /[A-Z]/.test(str);
}

function atLeastOneLower(str) {
    return /[a-z]/.test(str);
}

function atLeastOneSpecialChar(str) {
    return /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/.test(str.toString());
}

function checkPasswordRequirments(password){
    if( minLength === undefined || minLength === null ||
        maxLength === undefined || maxLength === null ||
        atLeastOneNumber === undefined || atLeastOneNumber === null ||
        atLeastOneMaiusc === undefined || atLeastOneMaiusc === null ||
        atLeastOneMinusc === undefined || atLeastOneMinusc === null ||
        atLeastOneSpecialCharacter  === undefined || atLeastOneSpecialCharacter === null ||
        acceptSpecialCharacters === undefined || acceptSpecialCharacters === null
    ) {
        console.log("inizializza script psw.js col metodo initPasswordAttributes()!");
        return false;
    }

    if(password.length>=minLength && password.length<=maxLength){
        let p = password;
        if(acceptSpecialCharacters) {

            // T F F F
            if(atLeastOneNumber && !atLeastOneMaiusc && !atLeastOneMinusc && !atLeastOneSpecialCharacter){
                if(atLeastOneNum(p)) return true;
                else return false;
            }

            // T T F F
            else if(atLeastOneNumber && atLeastOneMaiusc && !atLeastOneMinusc && !atLeastOneSpecialCharacter){
                if(atLeastOneNum(p) && !atLeastOneLower(p)) return true;
                else return false;
            }

            // T T T F
            else if(atLeastOneNumber && atLeastOneMaiusc && atLeastOneMinusc && !atLeastOneSpecialCharacter){
                if(atLeastOneNum(p) && atLeastOneUpper(p) && atLeastOneLower(p)) return true;
                else return false;
            }

            // T F F T
            else if(atLeastOneNumber && !atLeastOneMaiusc && !atLeastOneMinusc && atLeastOneSpecialCharacter){
                if(atLeastOneNum(p) && atLeastOneSpecialChar(p)) return true;
                else return false;
            }

            // T F T F
            else if(atLeastOneNumber && !atLeastOneMaiusc && atLeastOneMinusc && !atLeastOneSpecialCharacter){
                if(atLeastOneNum(p) && atLeastOneLower(p)) return true;
                else return false;
            }

            // T T F T
            if(atLeastOneNumber && atLeastOneMaiusc && !atLeastOneMinusc && atLeastOneSpecialCharacter){
                if(atLeastOneNum(p) && atLeastOneUpper(p) && atLeastOneSpecialChar(p)) return true;
                else return false;
            }

            // T F T T
            if(atLeastOneNumber && !atLeastOneMaiusc && atLeastOneMinusc && atLeastOneSpecialCharacter){
                if(atLeastOneNum(p) && atLeastOneLower(p) && atLeastOneSpecialChar(p)) return true;
                else return false;
            }

            // T T T T
            else if(atLeastOneNumber && atLeastOneMaiusc && atLeastOneMinusc && atLeastOneSpecialCharacter){
                if(atLeastOneNum(p) && atLeastOneUpper(p) && atLeastOneLower(p) && atLeastOneSpecialChar(p)) return true;
                else return true;
            }

            // F F F F
            else if(!atLeastOneNumber && !atLeastOneMaiusc && !atLeastOneMinusc && !atLeastOneSpecialCharacter){
                return true;
            }

            // F T F F
            else if(!atLeastOneNumber && atLeastOneMaiusc && !atLeastOneMinusc && !atLeastOneSpecialCharacter){
                if(atLeastOneUpper(p)) return true;
                else return false;
            }

            // F T T F
            else if(!atLeastOneNumber && atLeastOneMaiusc && atLeastOneMinusc && !atLeastOneSpecialCharacter){
                if(atLeastOneUpper(p) && atLeastOneLower(p)) return true;
                else return false;
            }

            // F F F T
            else if(!atLeastOneNumber && !atLeastOneMaiusc && !atLeastOneMinusc && atLeastOneSpecialCharacter){
                if(atLeastOneSpecialChar(p)) return true;
                else return false;
            }

            // F F T F
            else if(!atLeastOneNumber && !atLeastOneMaiusc && atLeastOneMinusc && !atLeastOneSpecialCharacter){
                if(atLeastOneLower(p)) return true;
                else return false;
            }

            // F T F T
            else if(!atLeastOneNumber && atLeastOneMaiusc && !atLeastOneMinusc && atLeastOneSpecialCharacter){
                if(atLeastOneUpper(p) && atLeastOneSpecialChar(p)) return true;
                else return false;
            }

            // F F T T
            else if(!atLeastOneNumber && !atLeastOneMaiusc && atLeastOneMinusc && atLeastOneSpecialCharacter){
                if(atLeastOneLower(p) && atLeastOneSpecialChar(p)) return true;
                else return false;
            }

            // F T T T
            else if(!atLeastOneNumber && atLeastOneMaiusc && atLeastOneMinusc && atLeastOneSpecialCharacter){
                if(atLeastOneUpper(p) && atLeastOneLower(p) && atLeastOneSpecialChar(p)) return true;
                else return false;
            }

            else return false;
        }
        else {
            if(!atLeastOneSpecialChar(p)){
                // T T T
                if(atLeastOneNumber && atLeastOneMaiusc && atLeastOneMinusc){
                    if(atLeastOneNum(p) && atLeastOneUpper(p) && atLeastOneLower(p)) return true;
                    else return false;
                }

                // T T F
                else if(atLeastOneNumber && atLeastOneMaiusc && !atLeastOneMinusc){
                    if(atLeastOneNum(p) && atLeastOneUpper(p)) return true;
                    else return false;
                }

                // T F F
                else if(atLeastOneNumber && !atLeastOneMaiusc && !atLeastOneMinusc){
                    if(atLeastOneNum(p)) return true;
                    else return false;
                }

                // T F T
                else if(atLeastOneNumber && !atLeastOneMaiusc && atLeastOneMinusc){
                    if(atLeastOneNum(p) && atLeastOneLower(p)) return true;
                    else return false;
                }

                // F T T
                else if(!atLeastOneNumber && atLeastOneMaiusc && atLeastOneMinusc){
                    if(atLeastOneUpper(p) && atLeastOneLower(p)) return true;
                    else return false;
                }

                // F F T
                else if(!atLeastOneNumber && !atLeastOneMaiusc && atLeastOneMinusc){
                    if(atLeastOneLower(p)) return true;
                    else return false;
                }

                // F T F
                else if(!atLeastOneNumber && atLeastOneMaiusc && !atLeastOneMinusc){
                    if(atLeastOneUpper(p)) return true;
                    else return false;
                }

                // F F F
                else if(!atLeastOneNumber && !atLeastOneMaiusc && !atLeastOneMinusc){
                    return true;
                }

                // F F T
                else if(!atLeastOneNumber && !atLeastOneMaiusc && atLeastOneMinusc){
                    if(atLeastOneLower(p)) return true;
                    else return false;
                }

                else return false;
            }
            else return false;
        }
    }

}

$(function() {

    $("#newPwd, #confirmPwd").on('keyup',  function() {
        if (!($("#newPwd").val() === $('#confirmPwd').val())) {
            $("#notEqualPwdMSG").toggleClass("off");
            document.getElementById("confirmPwd").style.borderColor = "red";
            document.getElementById("newPwd").style.borderColor = "red";
        }
        else if($("#newPwd").val() === $('#confirmPwd').val() && !($("#newPwd").val()==="") && !($("#confirmPwd").val()==="")) {
            $("#notEqualPwdMSG").removeClass().addClass("off");
            document.getElementById("confirmPwd").style.borderColor = "green";
            document.getElementById("newPwd").style.borderColor = "green";

            let validate = checkPasswordRequirments($("#newPwd").val());
            console.log(validate);
            if(validate){
                document.getElementById("requirmentsForPassword").style.color = "green";
                document.getElementById("submitPasswordBTN").disabled = false;
            } else if (!validate){
                document.getElementById("requirmentsForPassword").style.color = "red";
                document.getElementById("submitPasswordBTN").disabled = true;
            }
        }
        else {
            document.getElementById("confirmPwd").style.borderColor = "";
            document.getElementById("newPwd").style.borderColor = "";
            document.getElementById("requirmentsForPassword").style.color = "";
            document.getElementById("submitPasswordBTN").disabled = true;
        }
    });

});

//======================================================================================================================
console.log("psw.js loaded successfully");