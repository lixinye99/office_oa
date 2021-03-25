var user, role, currentID, flag = true;
function Personload() {
    $('#table').bootstrapTable({
        method: "get",
        striped: true,
        singleSelect: false,
        dataType: "json",
        pagination: true, //分页
        pageSize: 10,
        pageNumber: 1,
        search: false, //显示搜索框
        contentType: "application/x-www-form-urlencoded",
        queryParams: null,
        columns: [
            {
                title: "用户ID",
                field: 'uid',
                align: 'center',
                valign: 'middle'
            },
            {
                title: "用户名",
                field: 'username',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '角色',
                field: 'role',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '部门',
                field: 'departName',
                align: 'center'
            },
            {
                title: '绑定邮箱',
                field: 'email',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '状态',
                field: 'status',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '备注',
                field: 'remark',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '操作',
                align: 'center',
                formatter: function (value, row) {
                    var e = '<button type="button" class="btn btn-danger" onclick="del(this)">删除</button> ';
                    var d = '<button type="button" class="btn btn-info" onclick="edit(this)">修改</button> ';
                    var l = '<button type="button" class="btn btn-warning" onclick="lock(this)">解锁/锁定</button> '
                    return d + l + e;
                }
            }
        ]
    });
    getData();
}
function getData() {
    $.ajax({
        type: "GET",
        url: "/getAllUserInfo" ,
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                const TableData = result.data;
                $('#table').bootstrapTable("load", TableData);
            }
        }
    })
}

function add() {
    openAdd();
}
function edit(obj) {
    let id = $(obj).parent().parent().children("td").eq(0).html();
    openChange(id);
}

function lock(obj) {
    let id = $(obj).parent().parent().children("td").eq(0).html();
    $.ajax({
        url: '/admin/lockUser/'+id,
        type: 'PUT',
        dataType: 'json',
        success: function (result) {
            if (result.code == 1) {
                alert("锁定成功！")
                window.location.reload();
            } else {
                alert(result.msg)
            }
        },
        error: function (err) {
        }
    });
}
function del(obj) {
    let id = $(obj).parent().parent().children("td").eq(0).html();
    $.ajax({
        url: '/admin/deleteUser/'+id,
        type: 'DELETE',
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
}
function getCurrentID() {
    return currentID;
}
function openAdd(){
    layer.open({
        type: 2,
        title: '添加信息',
        shadeClose: true,
        shade: 0.5,
        skin: 'layui-layer-rim',
        //maxmin: true,
        closeBtn:2,
        area: ['80%', '90%'],
        shadeClose: true,
        closeBtn: 2,
        content: 'person_tail.html',   //iframe的url
    });
}

function openChange(id){
    $.ajax({
        url: '/admin/readyForUpdate/'+id,
        type: 'GET',
        dataType: 'json',
        success: function (result) {
            if (result.code == 1) {
                window.userInfo = result.data;
                layer.open({
                    type: 2,
                    title: '修改用户信息',
                    shadeClose: true,
                    shade: 0.5,
                    skin: 'layui-layer-rim',
//            maxmin: true,
                    closeBtn:2,
                    area: ['80%', '90%'],
                    shadeClose: true,
                    closeBtn: 2,
                    content: 'person_tail_change.html'
                    //iframe的url
                });
            } else {
                alert(result.msg)
            }
        },
        error: function (err) {
        }
    });
}





