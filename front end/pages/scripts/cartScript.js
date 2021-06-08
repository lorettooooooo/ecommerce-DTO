//inizializzo la pagina
let loadPage = () => {
    document.getElementById("pageTitle").innerHTML = ("FakeWebsite");
    fetch('http://localhost:8080/api/cart/myCart', {
        method: 'POST',
        headers: headers,
        redirect: 'follow'
    })
        .then(response => response.json())
        .then((data) => {
            console.log(data)
            let tableBody = document.createElement("tbody")
            tableBody.id = "cartBody";
            let rowCounter = 0;
            let priceTot = 0
            if (data.cartArticles.length == 0) {
                console.log("carrello vuoto")
                document.getElementById("cartTable").style.display = "none";
                let noCart = document.createElement('h1')
                noCart.innerText = "Il carrello è vuoto"
                document.getElementById("cartDiv").appendChild(noCart)
            } else {
                data.cartArticles.forEach(element => {
                    console.log(element)
                    let row = tableBody.insertRow(rowCounter)
                    row.insertCell(0).innerHTML = "<button type='button' onclick='deleteCartArticle(" + element.articleId + ")'>x</button>"
                    row.insertCell(1).innerHTML = element.name
                    row.insertCell(2).innerHTML = element.code
                    row.insertCell(3).innerHTML = element.quantity
                    row.insertCell(4).innerHTML = element.price
                    row.insertCell(5).innerHTML = "<button type='button' onclick='addArticle(" + element.articleId + ")'>Aggiungi al carrello</button><button type='button' onclick='removeArticle(" + element.articleId + ")'>rimuovi dal carrello</button>"
                    rowCounter++;
                    priceTot += element.quantity * element.price
                })
                let oldCart = document.getElementById("cartBody")
                oldCart.parentNode.replaceChild(tableBody, oldCart)
                let row = tableBody.insertRow(rowCounter);
                row.insertCell(0).innerHTML = "<button type='button' onclick='deleteCart()'>elimina carrello</button>"
                row.insertCell(1).innerHTML = null
                row.insertCell(2).innerHTML = "prezzo totale:"
                row.insertCell(3).innerHTML = priceTot
                row.insertCell(4).innerHTML = "<button type='button' onclick='submitCart()'>Procedi all'acquisto</button>"
            }
        })
}

//serve per confermare il carrello e trasformarlo in ordine
let submitCart = () => {
    fetch('http://localhost:8080/api/orders/submitOrder', {
        method: 'POST',
        headers: headers,
        redirect: 'follow'
    }).then((response) => {
        console.log(response)
        location.reload()
    })
}

//cancella tutto il carrello
let deleteCart = () => {
    fetch('http://localhost:8080/api/cart/deleteCart', {
        method: 'POST',
        headers: headers,
        redirect: 'follow'
    }).then((response) => {
        console.log(response);
        location.reload()
    })
}

//cancella solo la riga dell'articolo selezionata
let deleteCartArticle = (articleId) => {
    fetch('http://localhost:8080/api/cart/deleteCartArticle?articleId=' + articleId, {
        method: 'POST',
        headers: headers,
        redirect: 'follow'
    }).then((response) => {
        console.log(response);
        location.reload()
    })
}