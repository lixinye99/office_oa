/**
 * Created by Administrator on 2016/8/4.
 */
// 加载数据
var name, type,currentID;
function Myfileload() {
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
                checkbox:"true",
                field: 'ID',
                align: 'center',
                valign: 'middle'
            },
            {
                title: "文件名称",
                field: 'class',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '文件类型',
                field: 'sex',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '上传时间',
                field: 'sex',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '文件大小',
                field: 'sex',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '文件格式',
                field: 'sex',
                align: 'center',
                valign: 'middle'
            },
           
            {
                title: '操作',
                field: '',
                align: 'center',
                formatter: function (value, row) {
                    var e = '<button button="#" mce_href="#" onclick="del(\'' + row.WORKRECORDID + '\')">删除</button> '
                    var d = '<button button="#" mce_href="#" onclick="down(\'' + row.WORKRECORDID + '\')">下载</button> ';

                    return e + d;
                }
            }
        ]
    });
    getData();
    getType();

}
// 查询数据
function getData() {

}
//初始化类型下拉菜单
function getType() {

}
// 添加操作
function add(id) {
    openlayer()

}
// 编辑操作
function edit(id) {
    openlayer()
}
// 删除操作
function del(id) {

}
// 下载操作
function down(id) {

}
function getCurrentID(id) {
    return currentID;
}
// 弹出框
var lyrId;
function openlayer(id){
   lyrId=  layer.open({
        type: 2,
        title: '添加信息',
        shadeClose: true,
        shade: 0.5,
        skin: 'layui-layer-rim',
//            maxmin: true,
        closeBtn:2,
        area: ['80%', '90%'],
        shadeClose: true,
        closeBtn: 2,
        content: 'safe_tail01.html'
        //iframe的url
    });
}

// 关闭弹出框
function closeLayer(){
    layer.close(lyrId);
}




