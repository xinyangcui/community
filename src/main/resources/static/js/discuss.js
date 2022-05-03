$(function(){
    $("#topBtn").click(updateTop);
    $("#wonderfulBtn").click(setWonderful);
    $("#deleteBtn").click(setDelete);
});

// 点赞
function like(btn, entityType, entityId, entityUserId, postId) {
    // var token = $("meta[name='_csrf']").attr("content");
    // var header = $("meta[name='_csrf_header']").attr("content");
    // $(document).ajaxSend(function(e, xhr, options){
    //     xhr.setRequestHeader(header, token);
    // });
    $.post(
        CONTEXT_PATH + "/like",
        {"entityType":entityType, "entityId":entityId, "entityUserId":entityUserId, "postId":postId },
        function(data) {
            data = $.parseJSON(data);
            if (data.code == 0) {
                $(btn).children("i").text(data.likeCount);
                $(btn).children("b").text(data.likeStatus == 1 ? '已赞' : '赞');
            }
            else {
                alert(data.msg);
            }

        }
    )
}

// 置顶 or 取消置顶
function updateTop() {
    // var token = $("meta[name='_csrf']").attr("content");
    // var header = $("meta[name='_csrf_header']").attr("content");
    // $(document).ajaxSend(function(e, xhr, options){
    //     xhr.setRequestHeader(header, token);
    // });
    $.post(
        CONTEXT_PATH + "/discuss/top",
        {
            "id": $("#postId").val(),
            // $("#postType").val() 帖子当前的 type
            "type": ($("#postType").val() == 1) ? 0 : 1
        },
        function (data) {
            data = $.parseJSON(data);
            if (data.code == 0) {
                // 偷个懒，直接刷新界面
                window.location.reload();
            }
            else {
                alert(data.msg);
            }
        }
    )
}

// 加精 or 取消加精
function setWonderful() {
    // var token = $("meta[name='_csrf']").attr("content");
    // var header = $("meta[name='_csrf_header']").attr("content");
    // $(document).ajaxSend(function(e, xhr, options){
    //     xhr.setRequestHeader(header, token);
    // });
    $.post(
        CONTEXT_PATH + "/discuss/wonderful",
        {
            "id":$("#postId").val(),
            "status": ($("#postStatus").val() == 1) ? 0 : 1
        },
        function (data) {
            data = $.parseJSON(data);
            if (data.code == 0) {
                // 偷个懒，直接刷新界面
                window.location.reload();
                // // 加精成功后，将加精按钮设置为不可用
                // $("#wonderfulBtn").attr("disabled", "disable")
            }
            else {
                alert(data.msg);
            }
        }
    )
}

// 删除
function setDelete() {
    // var token = $("meta[name='_csrf']").attr("content");
    // var header = $("meta[name='_csrf_header']").attr("content");
    // $(document).ajaxSend(function(e, xhr, options){
    //     xhr.setRequestHeader(header, token);
    // });
    $.post(
        CONTEXT_PATH + "/discuss/delete",
        {"id":$("#postId").val()},
        function (data) {
            data = $.parseJSON(data);
            if (data.code == 0) {
                // 删除成功后，跳转到首页
                location.href = CONTEXT_PATH + "/index";
            }
            else {
                alert(data.msg);
            }
        }
    )
}