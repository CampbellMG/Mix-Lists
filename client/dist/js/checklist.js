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
        .then(function (board) {
            httpGetAsync(
                "https://tysz6ea8ki.execute-api.ap-southeast-2.amazonaws.com/dev/checklists?apiKey=6890595d33d989e3c7c373b127a68cb7&token=31568b46ca91bfd8779b12e9e5eb01daf2d6a7ae3255a76002448b0086634f59&boardId=" + board.id,
                renderChecklists
            )
        })
});

function renderChecklists(checklists) {
    checklists = JSON.parse(checklists);
    var keys = Object.keys(checklists.checklists);

    for (var i = 0; i < keys.length; i++) {
        var group = document.createElement("optgroup");
        group.setAttribute("label", keys[i]);

        for (var j = 0; j < checklists.checklists[keys[i]].length; j++) {
            var list = checklists.checklists[keys[i]][j];
            var option = document.createElement("option");

            option.appendChild(document.createTextNode(list));
            option.setAttribute("value", list);

            group.appendChild(option);
        }

        window.availableLists.appendChild(group)
    }
}

function httpGetAsync(url, callback, method, body) {
    method = typeof method !== 'undefined' ? method : 'GET';
    body = typeof body !== 'undefined' ? body : null;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            callback(xmlHttp.responseText);
    };
    xmlHttp.open(method, url, true);
    xmlHttp.send(body);
}