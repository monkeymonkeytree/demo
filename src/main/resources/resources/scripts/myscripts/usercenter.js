$(function () {
    $.ajax({
        url: 'http://localhost:8080/whoami',
        type: 'PUT',
        success: function (msg) {
            $("#user").val(msg.username);
            $("#u55_input").val(msg.email);
            $("#u57_input").val(msg.age);
            $("#u64_input").val(msg.role);
            // if (msg.role != "管理员") {
            //     $("#u62").remove();
            //     $("#u64").remove();
            //     $("#u65").remove();
            // }


        },
        error: function () {
            alert("失败")
        },
        dataType: 'json'
    })

    $("#u58").click(function () {
        $.ajax({
            url: 'http://localhost:8080/updateUser',
            data: 'username=' + $("#user").val() + "&email=" + $("#u55_input").val() + "&age=" + $("#u57_input").val(),
            type: 'PUT',
            success: function (msg) {
                alert("更新成功！")
            },
            error: function () {
                alert("更新失败！")
            },
            dataType: 'json'
        })
    })


    $("#u60").click(function () {
        alert("我被点了");
    })

    $("#u65").click(function () {
        $.ajax({
            url: 'http://localhost:8080/updateUser',
            data: 'username=' + $("#user").val() + "&role=" + $("#u64_input").val(),
            type: 'PUT',
            success: function (msg) {
                alert("更新成功！请退出重新登录")
            },
            error: function () {
                alert("更新失败！")
            },
            dataType: 'json'
        })
    })


})