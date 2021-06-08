logincheck = () => {
    fetch('http://localhost:8080/api/user/tokenCheck', {
        method: 'POST',
        headers: headers,
        redirect: 'follow'
    })
        .then(response => response.text())
        .then((data) => {
            console.log(data)
            if (data == false) {
                window.location.href = "../index.html"
            } else {loadPage()}
        })
        .catch(error => {
            console.error(error);
        })
}

const addArticle = (articleId) => {
    fetch('http://localhost:8080/api/cart/addArticle?articleId=' + articleId, {
        method: 'POST',
        headers: headers,
        redirect: 'follow'
    })
    .then(response => response.json())
    .then(data => {
        console.log(data)
        loadPage();
    })
}

const removeArticle = (articleId) => {
    fetch('http://localhost:8080/api/cart/removeArticle?articleId=' + articleId, {
        method: 'POST',
        headers: headers,
        redirect: 'follow'
    })
    .then(response => response.json())
    .then(data => {
        console.log(data)
        loadPage();
    })
}
let headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'auth-token': sessionStorage.getItem('auth-token')
}

let username = sessionStorage.getItem("username");

let logout = () =>{
    if (confirm('sei sicuro/a?')) {
        sessionStorage.setItem("auth-token", null)
        sessionStorage.setItem("username", null)
        window.location.reload()
    }


}

