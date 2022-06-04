$(function () {


    $("#u131_input").change(function () {
        var choice = $("#u131_input").val();
        var name = $("#option").val();
        if (choice == "历史（来自MongoDB）") {
            $.ajax({
                url: 'http://localhost:8080/selectData',
                data: 'name=' + name,
                type: 'put',
                success: function (msg) {
                    $("#data").empty();
                    if (msg.msg == "没有数据") {
                        alert(msg.msg);
                    } else {

                        var CGQS = msg.msg;
                        var nums = msg.msg.length;
                        for (var i = 0; i <= nums; i++) {
                            var tr = '<tr>'
                                + '<td>'
                                + '<input type="text" disabled="disabled" value="' + msg.msg[i].name + '" style="width: 100px">' +
                                '</td>'
                                + '<td>' + '<input type="text"  value="' + msg.msg[i].updateTime + '" style="width: 200px">' + '</td>' +
                                '<td>'
                                + '<input  disabled="disabled" type="text"  value="' + msg.msg[i].value + '" style="width: 100px">' +
                                '</td>';

                            $("#data").append(tr);
                        }
                    }


                },
                error: function () {
                    alert("失败")
                },
                dataType: 'json'
            })
        }else if(choice == "实时（来自MQTT）"){
            $("#data").empty();
        }
    })

    $("#option").change(function () {

        var name = $("#option").val();
        var choice = $("#u131_input").val();
        if (choice == "历史（来自MongoDB）") {
            $.ajax({
                url: 'http://localhost:8080/selectData',
                data: 'name=' + name,
                type: 'put',
                success: function (msg) {
                    $("#data").empty();
                    if (msg.msg == "没有数据") {
                        alert(msg.msg);
                    } else {
                        var CGQS = msg.msg;
                        var nums = msg.msg.length;
                        for (var i = 0; i <= nums; i++) {
                            var tr = '<tr>'
                                + '<td>'
                                + '<input type="text" disabled="disabled" value="' + msg.msg[i].name + '" style="width: 100px">' +
                                '</td>'
                                + '<td>' + '<input type="text"  disabled="disabled" value="' + msg.msg[i].updateTime + '" style="width: 200px">' + '</td>' +
                                '<td>'
                                + '<input  disabled="disabled" type="text"  value="' + msg.msg[i].value + '" style="width: 100px">' +
                                '</td>';

                            $("#data").append(tr);
                        }
                    }


                },
                error: function () {
                    alert("失败")
                },
                dataType: 'json'
            })
        }else if(choice == "实时（来自MQTT）"){
            $("#data").empty();
        }
    })


    $.ajax({
        url: 'http://localhost:8080/selectCGQ',
        type: 'PUT',
        success: function (msg) {
            var CGQS = msg.CGQS;
            var nums = msg.CGQS.length;

            for (var i = 0; i <= nums; i++) {
                var id = msg.CGQS[i].id;
                var name = msg.CGQS[i].name;
                var updateTime = msg.CGQS[i].updateTime;
                var status = msg.CGQS[i].status;
                var type = msg.CGQS[i].type;
                var tr = '<tr>'
                    + '<td>'
                    + '<input type="text" disabled="disabled" value="' + id + '" style="width: 100px">' +
                    '</td>'
                    + '<td>' + '<input type="text"  value="' + name + '" style="width: 100px">' + '</td>' +
                    '<td>'
                    + '<input  disabled="disabled" type="text"  value="' + updateTime + '" style="width: 150px">' +
                    '</td>';

                if (status == "在线") {
                    tr = tr + '<td>'
                        + '<select  style="width: 150px">' +
                        '<option value="是" selected="selected">是</option>' +
                        '<option value="否" >否</option>' +
                        '</select>' +
                        '</td>';
                } else {
                    tr = tr + '<td>'
                        + '<select  style="width: 150px">' +
                        '<option value="是" >是</option>' +
                        '<option value="否" selected="selected">否</option>' +
                        '</select>' +
                        '</td>';
                }

                if (type == "数值测量值型") {
                    tr = tr + '<td>'
                        + '<select    style="width: 120px">' +
                        '<option value="数值测量值型" selected="selected">数值测量值型</option>' +
                        '<option value="开关状态型" >开关状态型</option>' +
                        '<option value="地理位置定位型" >地理位置定位型</option>' +
                        '<option value="文本预警消息型" >文本预警消息型</option>' +
                        '</select>' +
                        '</td>'
                }

                if (type == "开关状态型") {
                    tr = tr + '<td>'
                        + '<select    style="width: 120px">' +
                        '<option value="数值测量值型" >数值测量值型</option>' +
                        '<option value="开关状态型" selected="selected" >开关状态型</option>' +
                        '<option value="地理位置定位型" >地理位置定位型</option>' +
                        '<option value="文本预警消息型" >文本预警消息型</option>' +
                        '</select>' +
                        '</td>'
                }

                if (type == "地理位置定位型") {
                    tr = tr + '<td>'
                        + '<select  style="width: 120px">' +
                        '<option value="数值测量值型" >数值测量值型</option>' +
                        '<option value="开关状态型"  >开关状态型</option>' +
                        '<option value="地理位置定位型" selected="selected" >地理位置定位型</option>' +
                        '<option value="文本预警消息型" >文本预警消息型</option>' +
                        '</select>' +
                        '</td>'
                }

                if (type == "文本预警消息型") {
                    tr = tr + '<td>'
                        + '<select style="width: 120px">' +
                        '<option value="数值测量值型" >数值测量值型</option>' +
                        '<option value="开关状态型"  >开关状态型</option>' +
                        '<option value="地理位置定位型"  >地理位置定位型</option>' +
                        '<option value="文本预警消息型"  selected="selected">文本预警消息型</option>' +
                        '</select>' +
                        '</td>'
                }


                tr = tr + '<td>'
                    + '<input type="button"  class="modify" value="修改" style="width: 100px">' +
                    '</td>'
                    + '<td>'
                    + '<input type="button"  class="delete" value="删除" style="width: 100px">' +
                    '</td>'
                ;

                tr = tr + "</tr>";

                var option = '<option  value="' + name + '">' + name + '</option>'
                $("#option").append(option);
                $("#Mytable").append(tr);


            }

        },
        error: function () {
            alert("失败")
        },
        dataType: 'json'
    })


    $("#u129").click(function () {
        var name = $("#u127_input").val();
        var status = $("#u132_input").val();
        var type = $("#u128_input").val();

        if (name == "设备名称") {
            alert("设备名称不能为空哦！")
            return false;
        }


        if (confirm("你确定要添加吗？")) {
            $.ajax({
                url: 'http://localhost:8080/addCGQ',
                data: 'name=' + name + '&status=' + status + '&type=' + type,
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