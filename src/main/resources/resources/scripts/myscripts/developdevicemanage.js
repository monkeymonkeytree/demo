$(function () {


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
                var option = '<option class="u145_input_option" selected value="' + name + '">' + name + '</option>'
                $("#u145_input").append(option);
                $("#u159_input").append(option);
            }

        },
        error: function () {
            alert("失败")
        },
        dataType: 'json'
    })


    $("#u154").click(function () {

        if (confirm("你确定要添加吗？")) {


            var name = $("#u145_input").val();
            var value = $("#u149_input").val();

            if (name == "温度传感器") {
                var value = $("#u149_input").val();
            } else if (name == "烟雾报警器") {
                var value = $("#u153_input").val();
            } else if (name == "门磁传感器") {
                var value = $("#u151_input").val();
            }
            if (value == "") {
                alert("value不能为空！")
                return false;
            }


            $.ajax({
                url: 'http://localhost:8080/insertCGQ',
                data: 'name=' + name + '&value=' + value,
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
    })


    $("#u164").click(function () {


        var name = $("#u159_input").val();
        var key = $("#u161_input").val();
        var value = $("#u163_input").val();

        var ask = "确定要删除吗？"

        if (key == "" && value == "") {
            ask = "你确定要删除所有" + name + "的值吗?"
        }

        if(key!=""&&value==""){
            alert("value不能为空！");
            return false;
        }

        if(value!=""&&key==""){
            alert("key不能为空");
            return false
        }
        if (confirm(ask)) {
            $.ajax({
                url: 'http://localhost:8080/deleteCGQ',
                data: '&name=' + name + '&key=' + key + '&value=' + value,
                type: 'delete',
                success: function (msg) {
                    alert(msg.msg);
                },
                error: function () {
                    alert("失败")
                },
                dataType: 'json'
            })
        }


    })
})