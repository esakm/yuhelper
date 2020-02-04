var lastReq = new XMLHttpRequest();
var searchBar = document.getElementById("course-search-input");

var goDown = function(){
    let autocompleteItems = document.getElementsByClassName("list-group-item");
    let activeItem = document.getElementsByClassName("autocomplete-active");
    if(autocompleteItems.length === 0){
        return;
    }else if(autocompleteItems.length === 1){
        autocompleteItems[0].className = "list-group-item list-group-item-primary list-group-item-action flex-column active autocomplete-active";
        return;
    }else{
        if(activeItem.length === 0){
            autocompleteItems[0].className = "list-group-item list-group-item-primary list-group-item-action flex-column active autocomplete-active";
            return;
        }
        for(let i = 0; i < autocompleteItems.length; i++){
            if(autocompleteItems.item(i) === activeItem[0]){
                if(i === autocompleteItems.length - 1){
                    activeItem[0].className = "list-group-item list-group-item-primary list-group-item-action flex-column";
                    // console.log(autocompleteItems);
                    autocompleteItems[0].className = "list-group-item list-group-item-primary list-group-item-action flex-column active autocomplete-active";
                    return false;
                }else{
                    autocompleteItems[i + 1].className = "list-group-item list-group-item-primary list-group-item-action flex-column active autocomplete-active";
                    activeItem[0].className = "list-group-item list-group-item-primary list-group-item-action flex-column";
                    return false;
                }
            }
        }
    }
};

var goUp = function(){
    let autocompleteItems = document.getElementsByClassName("list-group-item");
    let activeItem = document.getElementsByClassName("autocomplete-active");
    if(autocompleteItems.length === 0){
        return;
    }else if(autocompleteItems.length === 1){
        autocompleteItems[0].className = "list-group-item list-group-item-primary list-group-item-action flex-column active autocomplete-active";
        return;
    }else{
        if(activeItem.length === 0){
            autocompleteItems[0].className = "list-group-item list-group-item-primary list-group-item-action flex-column active autocomplete-active";
            return;
        }
        for(let i = 0; i < autocompleteItems.length; i++){
            if(autocompleteItems.item(i) === activeItem[0]){
                if(i === 0){
                    activeItem[0].className = "list-group-item list-group-item-primary list-group-item-action flex-column";
                    // console.log(autocompleteItems);
                    autocompleteItems[autocompleteItems.length - 1].className = "list-group-item list-group-item-primary list-group-item-action flex-column autocomplete-active active";
                    return false;
                }else{
                    activeItem[0].className = "list-group-item list-group-item-primary list-group-item-action flex-column";
                    autocompleteItems[i - 1].className = "list-group-item list-group-item-primary list-group-item-action flex-column active autocomplete-active";
                    return false;
                }
            }
        }
    }
};

var goToCourse = function(){
    let activeItem = document.getElementsByClassName("autocomplete-active");
    if(activeItem.length !== 0){
        window.location.href = activeItem[0].href;
    }
};
(function () {
    searchBar.addEventListener("keydown", function (e) {
        switch(e.keyCode){
            case 40:
                goDown();
                break;
            case 38:
                e.preventDefault();
                goUp();
                break;
            case 13:
                e.preventDefault();
                goToCourse();
                break;
        }
    })
})();


document.getElementById("course-search-input").addEventListener("input", function(){
    lastReq.abort();

    var xhr = new XMLHttpRequest();
    xhr.overrideMimeType("application/json");
    xhr.open("GET", "/courses/autocomplete?course=" + document.getElementById("course-search-input").value);
    xhr.send();
    lastReq = xhr;
    xhr.onload = function () {
        if(xhr.status !== 200){
            return;
        }else{
            let oldNodes = document.getElementsByClassName("autocomplete-item");
            if(oldNodes.length > 0){
                oldNodes = Array.prototype.slice.call(oldNodes);
                oldNodes.forEach( function (node) {
                    node.parentNode.removeChild(node);
                });
            }
            JSON.parse(xhr.response).forEach( function (course){
                let mainNewNode = document.createElement('div');
                mainNewNode.className = "row autocomplete-item";
                let newNode = document.createElement('a');
                newNode.href = "/" + encodeURI("course?q=" + course['coursePK']['courseCode'] + ' ' + course['coursePK']['credits']);
                newNode.className = "list-group-item list-group-item-primary list-group-item-action flex-column";
                let headingNode = document.createElement('h5');
                headingNode.className = "mb-1";
                headingNode.innerText = course['coursePK']['courseCode'] + ' ' + course['coursePK']['credits'];
                newNode.appendChild(headingNode);
                let containerNode = document.createElement('p');
                containerNode.className = "mb-1";
                containerNode.innerText = course['name'];
                newNode.appendChild(containerNode);
                mainNewNode.appendChild(newNode);
                document.getElementById('autocomplete-container').appendChild(mainNewNode);
            });
        }
    };
});


