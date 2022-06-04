$(function () {
    $("#u39").click(function () {
        $.ajax({
            url: 'http://localhost:8080/findpassword',
            data: 'username=' + $("#username").val(),
            type: 'PUT',
            success: function (msg) {
                alert(msg.msg);
            },
            error: function () {
                alert("失败")
            },
            dataType: 'json'
        })
    })

})