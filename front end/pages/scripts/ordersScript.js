//inizializzo la pagina degli ordini
let loadPage = () => {
    document.getElementById("pageTitle").innerHTML = ("FakeWebsite");
    fetch('http://localhost:8080/api/orders/myOrders', {
        method: 'POST',
        headers: headers,
        redirect: 'follow'
    })
        .then(response => response.json())
        .then((data) => {
            console.log(data)
            if (data.length == 0) {
                document.getElementById("orderTable").style.display = "none"
                let noCart = document.createElement('h1')
                noCart.innerHTML = "Ancora nessun ordine (comincia <a href = 'homePage.html'> qui </a>!)"
                document.getElementById("orderDiv").appendChild(noCart)
            } else {
                let tbody = document.createElement('tbody')
                data.forEach(order => {
                    console.log(order)
                    let row = tbody.insertRow(0);
                    row.insertCell(0).innerHTML = order.orderNumber
                    row.insertCell(1).innerHTML = order.date
                    row.insertCell(2).innerHTML = "<button type='button' onclick='getDetails(" + order.id + ")'>dettagli</button>"
                })
                let table = document.getElementById("orderTable")
                tbody.id = "orderTabBody"
                table.appendChild(tbody)
            }
        })
}

//serve per prendere e controllare i dettagli dell'ordine selezionato
let getDetails = (orderId) => {
    console.log(orderId)
    fetch('http://localhost:8080/api/orders/orderDetail?orderId=' + orderId, {
        method: 'POST',
        headers: headers,
        redirect: 'follow'
    })
        .then(response => response.json())
        .then((data) => {
            console.log(data)
            let oldTBody = document.getElementById("orDetTabBody");
            let newTBody = document.createElement("tbody");
            newTBody.id = ("orDetTabBody")
            let priceTot = 0;
            data.forEach(detail => {
                let row = newTBody.insertRow(0);
                row.insertCell(0).innerHTML = detail.articleName;
                row.insertCell(1).innerHTML = detail.quantity;
                row.insertCell(2).innerHTML = detail.articlePrice;
                priceTot += detail.quantity * detail.articlePrice;
            })
            let row = newTBody.insertRow();
            let priceCell = row.insertCell()
            priceCell.colSpan = "3"
            priceCell.innerHTML = "prezzo totale = " + priceTot;
            let otherRow = newTBody.insertRow();
            let backButton = otherRow.insertCell()
            backButton.colSpan = "3"
            backButton.innerHTML = "<button type='button' onclick='getOrders()'>torna agli ordini</button>"
            oldTBody.parentNode.replaceChild(newTBody, oldTBody);
        })
        .then(() => {
            document.getElementById("orderTable").style.display = 'none';
            document.getElementById("orderDetTable").style.display = 'block';
        })
}

getOrders = () => {
    document.getElementById("orderTable").style.display = 'block';
    document.getElementById("orderDetTable").style.display = 'none';
}