function logout() {
    let con = confirm("真的要退出?");
    if (con === true) {
        $.get("/api/user.json?action=logout", function () {
            location.href = "/login.html";
        });
    }
}