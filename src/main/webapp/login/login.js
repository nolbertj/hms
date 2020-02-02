function showPassword() {
    let x = document.getElementById("pswdInput");
    if(x.type === 'password') x.type = 'text';
    else if(x.type === 'text') x.type = 'password';
}

function checkForm() {
    let selectMenu = document.getElementById("userRole"),
        selectedOption = selectMenu.options[selectMenu.selectedIndex].value;

    if (selectedOption === 'none') {
        selectMenu.style.borderColor = "red";
        return false;
    }
    else return true;
}

function resetBorder(event) {
    if(!(event.target.value === 'none'))
        document.getElementById("userRole").style.border = "";
}