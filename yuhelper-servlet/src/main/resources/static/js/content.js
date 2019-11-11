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


