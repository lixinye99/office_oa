
let roleData = [];
function Roleload() {
    change();
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
                title: '角色ID',
                field: 'rid',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '角色名称',
                field: 'roleName',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '角色简称',
                field: 'roleNameZh',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '操作',
                align: 'center',
                formatter: function (value, row) {
                    var d = '<button class="btn btn-danger" mce_href="#" onclick="delRole(this)">删除</button> '
                    var l = '<button type="button" class="btn btn-info" onclick="edit(this)">修改</button> '
                    return d + l ;
                }
            }
        ]
    });
    getRoleTableData();
}

function getRoleTableData() {
    $.ajax({
        type: "GET",
        url: "/admin/getAllRole" ,
        dataType: "json",
        success: function (result) {
            roleData = result.data
            if (result.code == 1) {
                const TableData = result.data;
                $('#table').bootstrapTable("load", TableData);
            }
        }
    })
}

//删除角色
function delRole(obj) {
    let id = $(obj).parent().parent().children("td").eq(0).html();
    $.ajax({
        url: "/admin/deleteRole/" + id,
        type: "DELETE",
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                alert("删除成功！")
                window.location.reload();
            } else {
                alert(result.msg)
            }
        }
    })
}

function queryRoleByRoleNameZh() {
    $("#inputRoleName")
}

function edit(obj) {
    let id = $(obj).parent().parent().children("td").eq(0).html();
    roleData.forEach(function (item) {
        if(item.rid == id){
            $("#rid").val(item.rid)
            $("#roleName").val(item.roleName);
            $("#roleNameZh").val(item.roleNameZh);
            $("#roleIntroduction").val(item.roleIntroduction);
        }
    });
}

//保存
function changeRole() {
    $("#roleChange").ajaxSubmit({
        url:"/admin/updateRole",
        type:"PUT",
        dataType:"text",
        success:function (data) {
            const json = $.parseJSON(data);
            alert(json.msg);
            if(json.code == 1){
                window.location.reload();
            }
        },
        error:function () {

        },
        clearForm: false,
        resetForm: false
    },null,"text",null)
    return false;
}

//取消
function checkCancel() {
    $("#rid").val("")
    $("#roleName").val("");
    $("#roleNameZh").val("");
    $("#roleIntroduction").val("");
}

function openlayer() {
    layer.open({
        type: 2,
        title: '角色信息',
        shadeClose: true,
        shade: 0.5,
        skin: 'layui-layer-rim',
        closeBtn: 2,
        area: ['80%', '90%'],
        shadeClose: true,
        closeBtn: 2,
        content: 'role_tail.html'
    });
    
}



