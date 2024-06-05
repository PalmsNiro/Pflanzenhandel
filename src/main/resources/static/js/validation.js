document.addEventListener("DOMContentLoaded", function() {
    document.querySelector("form").addEventListener("submit", function(event) {
        var password = document.getElementById("password").value;
        var repeatPassword = document.getElementById("repeatPassword").value;
        if (password !== repeatPassword) {
            alert("Passwords do not match!");
            event.preventDefault();
        }
    });
});