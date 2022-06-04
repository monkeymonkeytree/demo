$(function () {
    $("#u23_input").change(function () {
        $.ajax({
            url: 'http://localhost:8080/regist',
            data: 'username=' + $("#u23_input").val(),
            type: 'PUT',
            success: function (msg) {
                $("#msg").text(msg.msg);
            },
            error: function () {
                alert("失败")
            },
            dataType: 'json'
        })
    })

    $("#u27_input").blur(function () {

        var pwd = $("#u24_input").val();
        var rpwd = $("#u27_input").val();
        if (pwd != rpwd) {
            $("#pwdmsg").text("两次密码输入不一致");
        } else {
            $("#pwdmsg").text("");
        }


    })


    $("#regist").click(function () {
        var email = $("#u29_input").val();
        var pwd = $("#u24_input").val();
        var rpwd = $("#u27_input").val();
        var username = $("#u23_input").val();
        if ($("#msg").val() == "用户名已存在") {
            return false;
        }

        if (username == "用户名") {
            alert("用户名为空");
            $("#msg").val("用户名不能为空");
            return false;
        }

        if (email == null) {

            return false;
        }

        if (rpwd == "重复密码") {
            $("#pwdmsg").text("请确认密码");
            return false;
        }

        if (pwd == "密码") {
            return false;
        }

        if(pwd!=rpwd){
            $("#pwdmsg").text("密码不一致");
            return false;
        }

    })


})