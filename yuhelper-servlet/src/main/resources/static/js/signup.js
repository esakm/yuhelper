const clearNodes = function (){
    let oldErrorNodes = document.getElementsByClassName("alert-danger");
    let oldSuccessNodes = document.getElementsByClassName("signup-validation-success-tip");
    if (oldErrorNodes.length > 0) {
        oldErrorNodes = Array.prototype.slice.call(oldErrorNodes);
        oldErrorNodes.forEach(function (node) {
            node.parentNode.removeChild(node);
        });
    }
    if (oldSuccessNodes.length > 0) {
        oldSuccessNodes = Array.prototype.slice.call(oldSuccessNodes);
        oldSuccessNodes.forEach(function (node) {
            node.parentNode.removeChild(node);
        });
    }
};

const signUp = function () {


    clearNodes();
    let valid = true;
    const email = document.getElementById("email-sign-up");
    const username = document.getElementById("username-sign-up");
    const password = document.getElementById("password-sign-up");
    const passwordCheck = document.getElementById("password-check");
    const usernameRegEx = /[._a-zA-Z0-9]{3,24}/g;
    const passwordRegEx = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/g;
    const emailRegEx = /^[_A-Za-z0-9-+](.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$/g;


    if (username.value.match(usernameRegEx) === null || username.value.match(usernameRegEx).length !== 1) {
        let error_node = document.createElement("div");
        error_node.className = "alert alert-danger";
        error_node.innerText = "Please enter a valid username. It must be alpha numeric (A-Z 0-9) and 3 to 24 characters long";
        username.parentNode.appendChild(error_node);
        valid = false;
    }
    if (password.value.match(passwordRegEx) === null || password.value.match(passwordRegEx).length !== 1) {
        let error_node = document.createElement("div");
        error_node.className = "alert alert-danger";
        error_node.innerText = "Please enter a valid password. It must include 1 upper case letter, 1 lower case letter, 1 special character and be at least 8 characters long";
        password.parentNode.appendChild(error_node);
        valid = false;
    }
    if (passwordCheck.value === null || passwordCheck.value !== password.value) {
        let error_node = document.createElement("div");
        error_node.className = "alert alert-danger";
        error_node.innerText = "Please re-enter your password";
        passwordCheck.parentNode.appendChild(error_node);
        valid = false;
    }
    if (email.value.match(emailRegEx) === null || email.value.match(emailRegEx).length !== 1) {
        let error_node = document.createElement("div");
        error_node.className = "alert alert-danger";
        error_node.innerText = "Please enter a valid email";
        email.parentNode.appendChild(error_node);
        valid = false;
    }
    if(valid){
        clearNodes();
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/users/add?username=" + username.value + "&email=" + email.value + "&password=" + encodeURIComponent(password.value) + "&passwordCheck=" + encodeURIComponent(passwordCheck.value), true);
        xhr.send();
        xhr.onload = function () {
            if(xhr.status === 409){
                let error_node = document.createElement("div");
                error_node.className = "signup-validation-error alert alert-danger";
                error_node.innerText = "That username or email is already taken. Please try another one.";
                email.parentNode.parentNode.appendChild(error_node);
            }else{
                let success_node = document.createElement("div");
                success_node.className = "signup-validation-success alert-success alert";
                success_node.innerHTML = "<h4 class=\"alert-heading\">Success!</h4>"+
                    "<p>A verification link has been sent to your email. Please verify your account before logging in.</p>\n" +
                    "<hr>";
                let resend_link = document.createElement("button");
                resend_link.className = "btn btn-primary";
                resend_link.id = "resend-button";
                resend_link.innerText = "Resend verification link";
                resend_link.setAttribute("href", "/users/resend?username=" + encodeURIComponent(username.value));
                resend_link.onclick = reSend;
                success_node.appendChild(resend_link);
                document.getElementById("sign-up-form").appendChild(success_node);
                document.getElementById("signup-submit-button").setAttribute('disabled', 'disabled');
            }
        };

    }
};


const reSend = function(){
    clearNodes();
    var xhr = new XMLHttpRequest();
    xhr.open("GET", document.getElementById("resend-button").getAttribute("href"));
    xhr.send();
    xhr.onload = function () {
        console.log(xhr.status);
        if(xhr.status === 200){
            let sent_node = document.createElement("div");
            sent_node.className = "signup-validation-success-tip text-light";
            sent_node.innerText = "We've sent you another email.";
            document.getElementById("resend-button").appendChild(sent_node);
            return false;
        }
    };
};