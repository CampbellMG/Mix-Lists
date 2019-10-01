/* global TrelloPowerUp */

var t = TrelloPowerUp.iframe();

function appendList() {
    var ul = window.selectedLists;
    var li = document.createElement("li");
    li.appendChild(document.createTextNode(window.availableLists.value));
    li.setAttribute("id", window.availableLists.value);
    ul.appendChild(li);
}

window.estimate.addEventListener('submit', function (event) {
    event.preventDefault();
    console.log(window.list.getElementsByTagName("li"))
});

t.render(function () {
    t.sizeTo(document.body);
    return t.board('all')
        .then(function (cards) {
            console.log(cards);
        })
});

function httpGetAsync(url, callback)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText);
    }
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
}