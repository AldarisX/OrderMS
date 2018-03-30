function logout() {
    layer.confirm('真的要退出?', {
        btn: ['确定', '取消'],
        icon: 3
    }, function () {
        $.get("/logout", function (data) {
            if (data.result) {
                window.location = data.url;
            } else {
                layer.msg(data.msg, {icon: 5});
            }
        });
    }, function () {
        layer.closeAll();
    });
}