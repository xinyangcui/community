$(function(){
    $("form").submit(check_data);
    $("input").focus(clear_error);
    // 点击表单 uploadForm 的点击按钮时，触发 upload 函数
    $("#uploadForm").submit(upload);
});

function check_data() {
    var pwd1 = $("#new-password").val();
    var pwd2 = $("#confirm-password").val();
    if(pwd1 != pwd2) {
        $("#confirm-password").addClass("is-invalid");
        return false;
    }
    return true;
}

function clear_error() {
    $(this).removeClass("is-invalid");
}

function upload() {
    // // 发送AJAX请求之前,将CSRF令牌设置到请求的消息头中.
    // var token = $("meta[name='_csrf']").attr("content");
    // var header = $("meta[name='_csrf_header']").attr("content");
    // $(document).ajaxSend(function(e, xhr, options){
    //     xhr.setRequestHeader(header, token);
    // });



    $.ajax({
        url: "http://upload-z1.qiniup.com",
        method: "post",
        processData: false, // 不要把表单内容转换成字符串
        contentType: false, // 不要 Jquery 自动设置上传的类型
        data: new FormData($("#uploadForm")[0]),
        success: function(data) {
            if (data && data.code == 0) {
                // 更新头像访问路径
                $.post(
                    CONTEXT_PATH + "/user/header/url",
                    {
                        "fileName": $("input[name='key']").val()
                    },
                    function (data) {
                        data = $.parseJSON(data);
                        if (data.code == 0) {
                            // 刷新界面
                            window.location.reload();
                        }
                        else {
                            alert(data.msg)
                        }
                    }
                )
            }
            else {
                alert("上传失败");
            }
        }

    });
    return false; // 表示事件到此为止，不要再提交表单了了
}