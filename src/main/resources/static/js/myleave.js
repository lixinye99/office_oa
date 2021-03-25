function add() {
    openlayer()
}

function del() {
    var row = $("#table").bootstrapTable('getSelections');
    var lidList = [];
    if(row.length != 0){
        for(var i=0;i<row.length;i++){
            lidList.push(row[i].lid)
        }
        $.ajax({
            url: '/deleteLeaveInfo',
            type: 'DELETE',
            data:{
                lidList:lidList,
            },
            traditional:true,
            dataType: 'json',
            success: function (result) {
                if (result.code == 1) {
                    alert("删除成功！")
                    window.location.reload();
                } else {
                    alert(result.msg)
                }
            },
            error: function (err) {
            }
        });
    }else{
        alert("请选择要删除的记录")
        return;
    }
}

function examine(obj) {
    var row = $("#table").bootstrapTable('getSelections');
    var lidList = [];
    var lid = $(obj).parent().parent().children("td").eq(1).html();
    var status = $(obj).html();
    if(row.length == 0 && (status == "通过" || status == "不通过")){
        lidList.push(lid)
    }else if(row.length != 0 && (status == "全部通过" || status == "全部不通过")){
        for(var i=0;i<row.length;i++){
            lidList.push(row[i].lid)
        }
    }else {
        alert("选择方式出现错误")
        return;
    }

    $.ajax({
        url: '/updateLeaveInfoStatus',
        type: 'PUT',
        data:{
          lidList:lidList,
          status:status
        },
        traditional:true,
        dataType: 'json',
        success: function (result) {
            if (result.code == 1) {
                alert("修改成功！")
                window.location.reload();
            } else {
                alert(result.msg)
            }
        },
        error: function (err) {
        }
    });
}


function openlayer(id){
    layer.open({
        type: 2,
        title: '添加信息',
        shadeClose: true,
        shade: 0.5,
        skin: 'layui-layer-rim',
//            maxmin: true,
        closeBtn:1,
        area: ['50%', '70%'],
        shadeClose: true,
        closeBtn: 1,
        content: 'myleave_tail.html'
        //iframe的url
    });
}





