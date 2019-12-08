function validate_form() {
    if (document.getElementById("pass1").value != document.getElementById("pass2").value) {
        document.getElementById("output3").innerHTML = "новые пароли не совпадают";
        return false;
    }
    else {
        return true;
    }
}