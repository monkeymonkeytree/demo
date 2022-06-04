$(function () {


    $.ajax({
        url: 'http://localhost:8080/selectAll',
        type: 'put',
        success: function (msg) {
            var users = msg.users;
            var nums = msg.users.length;

            for (var i = 0; i <= nums; i++) {
                var id = msg.users[i].id;
                var tr = '<tr>' + '<td>'
                    + '<input type="text" disabled="disabled" value="' + id + '" style="width: 100px">' +
                    '</td>' +
                    '<td>' +
                    '<input type="text" value="' + msg.users[i].username + '" style="width: 100px">' +
                    '</td>' +
                    '<td>' +
                    '<input type="text" value="' + msg.users[i].email + '" style="width: 150px">'
                    + '</td>'
                    + '<td>'
                    + '<input type="text" value="' + msg.users[i].age + '" style="width: 100px">'
                    + "</td>"
                ;

                if (msg.users[i].role == "管理员") {
                    tr = tr +
                        '<td>' +
                        '  <select class="role" >' +
                        '            <option  value="普通用户">普通用户</option>' +
                        '            <option  selected="selected" value="管理员">管理员</option>' +
                        '            <option  value="开发者">开发者</option>' +
                        '  </select>' +
                        '</td>'
                        + "<td>"
                } else if (msg.users[i].role == "普通用户") {
                    tr = tr +
                        '<td>' +
                        '  <select>' +
                        '            <option selected="selected" value="普通用户">普通用户</option>' +
                        '            <option   value="管理员">管理员</option>' +
                        '            <option  value="开发者">开发者</option>' +
                        '  </select>' +
                        '</td>'
                        + "<td>"
                } else if (msg.users[i].role == "开发者") {
                    tr = tr +
                        '<td>' +
                        '  <select class="role" >' +
                        '            <option  value="普通用户">普通用户</option>' +
                        '            <option   value="管理员">管理员</option>' +
                        '            <option selected="selected" value="开发者">开发者</option>' +
                        '  </select>' +
                        '</td>'
                        + "<td>"
                }

                tr = tr
                    + '<input type="text" value="' + msg.users[i].password + '" style="width: 100px">'
                    + "</td>" +
                    '            <td>' +
                    '                <input class="modify" id="modify' + i + '" type="button" style="width: 50px" value="修改">' +
                    '            </td>\n' +
                    '            <td>\n' +
                    '                <input class="delete" id="delete' + i + '" style="width: 50px" type="button" value="删除">' +
                    '            </td>'

                    + "</tr>";

                $("#table").append(tr);
            }

        },
        error: function () {
            alert("失败")
        },
        dataType: 'json'
    })


    $("#u97").click(function () {
        if (confirm("你确定要添加吗?")) {
            $.ajax({
                url: 'http://localhost:8080/insert',
                data: 'username=' + $("#u92_input").val() + "&email=" + $("#u93_input").val() + "&age=" + $("#u94_input").val() + "&role=" + $("#u95_input").val() + "&password=" + $("#u96_input").val(),
                type: 'PUT',
                success: function (msg) {
                    alert(msg.msg);

                },
                error: function () {
                    alert("失败")
                },
                dataType: 'json'
            })
        }
        location.reload();

    })


})



