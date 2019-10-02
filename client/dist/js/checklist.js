/* global TrelloPowerUp */

var t = TrelloPowerUp.iframe();
var trelloContext;

window.estimate.addEventListener('submit', function (event) {
    event.preventDefault();
});

function appendList() {
    var ul = window.selectedLists;
    var li = document.createElement("li");
    var selectedItem = window.availableLists;

    li.appendChild(document.createTextNode(selectedItem.selectedOptions[0].text));
    li.setAttribute("id", selectedItem.value);
    ul.appendChild(li);
}

function createMergeList(){
    var listItems = window.selectedLists.getElementsByTagName("li");
    var listIds = [];

    for(var i = 0; i < listItems.length; i++){
        listIds.push(listItems[i].getAttribute('id'));
    }

    mergeChecklists(trelloContext.card, 'New List', listIds)
}

t.render(function () {
    t.sizeTo(document.body);
    trelloContext = t.getContext();
    console.log(trelloContext);
    loadChecklists(trelloContext.board)
});

function loadChecklists (boardId) {
    httpAsync(
        "https://tysz6ea8ki.execute-api.ap-southeast-2.amazonaws.com/dev/checklists?apiKey=&token=&boardId=" + boardId,
        renderChecklists
    )
}

function mergeChecklists(boardId, name, listIds){
    httpAsync(
        "https://tysz6ea8ki.execute-api.ap-southeast-2.amazonaws.com/dev/merge?apiKey=&token=",
        function(){},
        'POST',
        {
            destinationCardId: boardId,
            title: name,
            checklistIds: listIds
        }
    )
}

function renderChecklists(checklistJSON) {
    var checklistObject = JSON.parse(checklistJSON);
    var checklists = checklistObject.checklists;

    for (var i = 0; i < checklists.length; i++) {
        var checklist = checklists[i];
        var group = document.createElement("optgroup");
        var option = document.createElement("option");

        group.setAttribute("label", checklist.cardName);

        option.appendChild(document.createTextNode(checklist.name));
        option.setAttribute("value", checklist.id);

        group.appendChild(option);

        window.availableLists.appendChild(group)
    }
}

function httpAsync(url, callback, method, body) {
    method = typeof method !== 'undefined' ? method : 'GET';
    body = typeof body !== 'undefined' ? JSON.stringify(body) : null;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            callback(xmlHttp.responseText);
    };
    xmlHttp.open(method, url, true);
    xmlHttp.send(body);
}