function addNotice() {
    openlayer()
}
function openlayer() {
    layer.open({
        type: 2,
        title: '公告信息',
        shadeClose: true,
        shade: 0.5,
        skin: 'layui-layer-rim',
        closeBtn: 2,
        area: ['98%', '85%'],
        shadeClose: true,
        closeBtn: 2,
        content: 'notice_tail.html'

    });

}





