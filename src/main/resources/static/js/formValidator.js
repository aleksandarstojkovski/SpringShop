function validateForm(id, value, displayError=true){

    var regex;
    var errorMessage;
    var returnCode=true;

    // empty the error message
    if (displayError){
        var errorMessageDiv = document.getElementById(id + "ErrorMessage");
        errorMessageDiv.innerHTML = "";
    }

    switch(id) {
        case "name":
            regex = /^[A-Za-z]+$/;
            errorMessage = "Name must contain only alphabets characters!";
            break;
        case "surname":
            regex = /^[A-Za-z]+$/;
            errorMessage = "Surname must contain only alphabets characters!";
            break;
        case "username":
            regex = /^[A-Za-z0-9_]+$/;
            errorMessage = "Allowed characters are: [A-Z], [a-z], [0-9] and _";
            break;
        case "password":
            regex = /^[A-Za-z0-9_]+$/;
            errorMessage = "Allowed characters are: [A-Z], [a-z], [0-9] and _";
            break;
        case "confirmPassword":
            regex = /^[A-Za-z0-9_]+$/;
            errorMessage = "Allowed characters are: [A-Z], [a-z], [0-9] and _";
            break;
        default:
            regex = /^[A-Za-z0-9_]+$/;
            errorMessage = "Something went wrong";
            break;
    }

    if (id === "password" || id === "confirmPassword"){

        var password = document.getElementById("password");
        var confirmPassword = document.getElementById("confirmPassword");

        if (password.value.length >0 && confirmPassword.value.length > 0) {
            if (password.value !== confirmPassword.value) {
                errorMessage = "Passwords do not match";
                returnCode = false;
            } else {
                document.getElementById(id + "ErrorMessage").innerText = "";
            }
        }
        if (value.length < 8 || value.length > 15){
            errorMessage = "Password length must be > of 8 characters and < of 15 characters";
            returnCode = false;
        }

    }

    if(regex.test(value) === false || returnCode === false)
    {
        if (displayError){
            var text = document.createTextNode(errorMessage);
            var tag = document.createElement("small");
            tag.style["color"] = "red";
            tag.appendChild(text);
            errorMessageDiv.appendChild(tag);
        }

        returnCode=false;
    }

    return returnCode;

}

function addRemoveButton(){
    var name = document.getElementById("name");
    var surname = document.getElementById("surname");
    var username = document.getElementById("username");
    var password = document.getElementById("password");
    var confirmPassword = document.getElementById("confirmPassword");
    var registerButtonPlaceholder = document.getElementById("registerButtonPlaceholder");
    var button = document.createElement("button");
    button.classList.add("btn");
    button.classList.add("btn-lg");
    button.classList.add("btn-primary");
    button.classList.add("btn-block");
    button.type = "submit";
    button.id = "registerButton";
    button.innerText = "Register";

    if (
        !validateForm(name.id, name.value, false) ||
        !validateForm(surname.id, surname.value, false) ||
        !validateForm(username.id, username.value, false) ||
        !validateForm(password.id, password.value, false) ||
        !validateForm(confirmPassword.id, confirmPassword.value, false)
    ){
        if (registerButtonPlaceholder.childElementCount > 0)
            registerButtonPlaceholder.childNodes.forEach(n => n.remove());
    } else{
        if (registerButtonPlaceholder.childElementCount === 0)
            registerButtonPlaceholder.appendChild(button)
    }
}

window.addEventListener("keyup", addRemoveButton);
window.addEventListener("click", addRemoveButton);