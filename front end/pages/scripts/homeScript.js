let loadPage = () => {
    document.getElementById("pageTitle").innerHTML = ("Welcome in FakeWebsite, " + username);
    fetch('http://localhost:8080/api/articles/purchasables', {
        method: 'POST',
        headers: headers,
        redirect: 'follow'
    })
        .then(response => response.json())
        .then((data) => {
            while (data.length > 0){
                let articles;
                if (data.length > 5){
                    articles = data.splice(0, 5)
                } else {
                    articles = data.splice(0, data.length)
                }
                let table = document.createElement("table")
                let imgRow = table.insertRow();
                let nameRow = table.insertRow();
                let priceRow = table.insertRow();
                let buttonRow = table.insertRow();
                articles.forEach(article => {
                    imgRow.insertCell().innerHTML = "<img class='articleImg' src='"+article.image+"'></img>"
                    nameRow.insertCell().innerHTML = article.name
                    priceRow.insertCell().innerHTML = "€ " + article.price
                    buttonRow.insertCell().innerHTML = "<button type='button' onclick='addArticle(" + article.id + ")'>Aggiungi al carrello</button>"
                })
                document.getElementById("tableDiv").appendChild(table)
            }

            let table = document.getElementById("purchasablesTable");
            let rowCounter = 0;
            data.forEach(element => {
                console.log(element)
                let row = table.insertRow(rowCounter)
                row.insertCell(0).innerHTML = "<img class='articleImg' src='"+element.image+"'></img>"
                row.insertCell(1).innerHTML = element.name
                row.insertCell(2).innerHTML = element.code
                row.insertCell(3).innerHTML = element.availability
                row.insertCell(4).innerHTML = element.price
                row.insertCell(5).innerHTML = "<button type='button' onclick='addArticle(" + element.id + ")'>Aggiungi al carrello</button>"
                rowCounter++;
            });
        })
}
