
login = () => {
    //  let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    fetch('http://localhost:8080/api/user/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        redirect: 'follow',
        body: JSON.stringify({
            username: document.getElementById("username").value,
            password: password
        })
    })
        .then(response => {
            console.log(response.status)
            let ret;
            if (response.status != 200){
                ret = response
            } else {
                ret = response.json()
            }
            return ret
        })
        .then(data => {
            if (data.status == 500){
                console.log(data.status)
                document.getElementById("loginError").innerHTML = "impossibile connettersi al server"
            } else if (data.status == 400){
                document.getElementById("loginError").innerHTML = "username o password errati"
            } else {
                setLog(data)
                window.location.href = "pages/homePage.html";
            }

        })
        .catch(error => {
            console.log(error)
        })
}

let subscribe = () => {
    document.getElementById('subscrError').innerText = ""
    let username = document.getElementById("subscrUsername").value;
    let password = document.getElementById("subscrPassword").value;
    fetch('http://localhost:8080/api/user/createUserLogin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        redirect: 'follow',
        body: JSON.stringify({
            username: username,
            password: password
        })
    })
        .then(response => {
            console.log (response.status)
            if (response.status == 226) {
                document.getElementById('subscrError').innerText = 'username già usato'
            } else {
              return  response.json()
            }
        })
        .then(data => {
            setLog(data)
            window.location.href = "pages/homePage.html"})
}

let showSubscribe = () => {
    document.getElementById("subscrForm").style.display = 'block';
    document.getElementById("loginForm").style.display = 'none';
}

let showLogin = () => {
    document.getElementById("loginForm").style.display = 'block';
    document.getElementById("subscrForm").style.display = 'none';
}

logincheck = () => {

    if (sessionStorage.getItem('auth-token')) {
        fetch('http://localhost:8080/api/user/tokenCheck', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'auth-token': sessionStorage.getItem('auth-token')
            },
            redirect: 'follow'
        })
        .then(response => response.text())
        .then((data) => {
            console.log(data)
            if (data == false) {} else {
                window.location.href = "pages/homePage.html"}
        })
        .catch(error => {
            console.error(error);
        })
    }
}

let setLog = (data) =>{
    console.log(data.token)
    sessionStorage.setItem("auth-token", data.token)
    sessionStorage.setItem("username", data.username)
}