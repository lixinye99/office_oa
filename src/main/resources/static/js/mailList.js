
//初始化函数
function OnLoad() {
    //表格方法
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
                title: '用户Id',
                field: 'uid',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '姓名',
                field: 'username',
                align: 'center',
                valign: 'middle'
            },
            {
                title: "部门名称",
                field: 'departName',
                align: 'center',
                valign: 'middle'
            },

            {
                title: '职务',
                field: 'roleNameZh',
                align: 'center'
            },
            {
                title: '部门电话',
                field: 'connectTelNum',
                align: 'center'
            },
            {
                title: '邮箱地址',
                field: 'email',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '用户备注',
                field: 'remark',
                align: 'center',
                valign: 'middle'
            },
        ]
    });
};








