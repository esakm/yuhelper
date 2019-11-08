(function () {
    var currentURL = window.location.href;
    var url = new URL(currentURL);
    var c = url.searchParams.get("error");
    if (c != null && c === 'true') {
        var alert = document.getElementById("login-alert");
        alert.className += " error_active";
    } else {
        var alert = document.getElementById("login-alert");
        alert.className += " error_inactive";
    }
})();