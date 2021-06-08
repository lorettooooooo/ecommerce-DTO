let loadPage = () => {
    document.getElementById("username").innerText = sessionStorage.getItem("username")
}

let usernameChange = () => {
    document.getElementById("userNameError").innerText = "";
    newUsername = document.getElementById("newUsername").value;
    if (newUsername.length == 0 || newUsername.indexOf(' ') >= 0) {
        document.getElementById("userNameError").innerText = "Username non valido";
    } else {
        console.log("va buono")
        fetch('http://localhost:8080/api/user/changeUsername?newUsername=' + newUsername, {
            method: 'POST',
            headers: headers,
            redirect: 'follow'
        })
            .then((response) => {
                if (response.status == 226) {
                    document.getElementById("userNameError").innerText = "Username già in uso";

                } else {
                    response.json()
                    .then(data => {
                        sessionStorage.setItem("username", data.username)
                        window.location.reload()
                    })
                }
            })

    }
}

let passwordChange = () => {
    document.getElementById("passwordError").innerText = "";
    let oldpwd = document.getElementById("oldPwd").value;
    let password1 = document.getElementById("newPwd1").value;
    let password2 = document.getElementById("newPwd2").value;
    console.log("sto facendo partire")
    if (!(password1 === password2)) {
        console.log("pwd diverse")
        document.getElementById("passwordError").innerText = "le password nuove inserite non corrispondono";
    } else if (password1.length == 0 || password1.indexOf(' ') >= 0) {
        document.getElementById("passwordError").innerText = "inserire una password valida"
    } else {
        fetch('http://localhost:8080/api/user/changePassword', {
            method: 'POST',
            headers: headers,
            redirect: 'follow',
            body: JSON.stringify({
                oldPassword: oldpwd,
                newPassword: password1
            })
        })
            .then(response => {
                if (response.status == 405) {
                    document.getElementById("passwordError").innerText = "password errata"
                }
            })
    }
}

let accountDelete = () => {
    if (confirm('sei sicuro/a?')) {
        sessionStorage.setItem("auth-token", null)
        sessionStorage.setItem("username", null)
        fetch('http://localhost:8080/api/user/deleteUser', {
            method: 'POST',
            headers: headers,
            redirect: 'follow'
        })
            .then(response => response.json())
            .then(data =>{
                console.log(data)
                console.log(data.type)
                if (data == true) {
                    sessionStorage.setItem("auth-token", null)
                    sessionStorage.setItem("username", null)
                    window.location.reload()
                }
            })
    }
}