var tit, person, currentID, time, flag = true;
function Mesload() {
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
                title: "编号",
                field: 'mid',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '消息标题',
                field: 'massageTitle',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '发布人',
                field: 'pusher',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '发送时间',
                field: 'pushTime',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '推送对象',
                field: 'pushObject',
                align: 'center',
                valign: 'middle'
            },
            {
                title: '推送内容',
                field: 'pushContent',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row,index) {
                    let showContent = value.substr(0,4)+"……";
                    var e = '<a href="#" onclick="showContentDetail(\'' + value + '\',this)">'+ showContent +'</a> ';
                    return e;
                }
            },
            {
                title: '推送状态',
                field: 'messageStatus',
                align: 'center',
                valign: 'middle'
            }
        ]
    });
}

function showContentDetail(value,obj){
    let flag = false;
    layer.open({
        type: 1,
        title:"消息详情",
        area: ['50%', '50%'],
        shadeClose: true, //点击遮罩关闭
        content: '\<\div style="padding:20px;">'+value+ '\<\/div>',
        end:function () {
            if(flag){
                location.reload();
            }
        }
    });
    let mid = $(obj).parent().parent().children("td").eq(0).html();
    let username = $(obj).parent().parent().children("td").eq(4).html();
    let status = $(obj).parent().parent().children("td").eq(6).html();
    $.ajax({
        url: '/changeMessageStatus',
        type: 'PUT',
        data:{
            mid : mid,
            username: username,
            status : status,
        },
        dataType: 'json',
        success: function (response) {
            if(response != null){
                if (response.code != 1) {
                    alert(response.msg)
                }else{
                    flag = true;
                }
            }
        },
        error: function (err) {
        }
    });
}

function add() {
    openlayer()
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
        area: ['70%', '80%'],
        shadeClose: true,
        closeBtn: 1,
        content: 'mesage_tail.html'
        //iframe的url
    });
}





