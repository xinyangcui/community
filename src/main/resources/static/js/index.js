$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");

	// // 发送AJAX请求之前,将CSRF令牌设置到请求的消息头中.
   // var token = $("meta[name='_csrf']").attr("content");
   // var header = $("meta[name='_csrf_header']").attr("content");
   // $(document).ajaxSend(function(e, xhr, options){
   //     xhr.setRequestHeader(header, token);
   // });

	// 获取标题和内容
	var title = $("#recipient-name").val();
	var tags = $("#tags").val();
	var content = $("#message-text").val();
	// 发送异步请求
	$.post(
		CONTEXT_PATH + "/discuss/add",
		{"title": title, "tags":tags,"content": content},
		// 处理服务端返回的数据
		function (data) {
			// String -> Json 对象
			data = $.parseJSON(data);
			// 在提示框 hintBody 显示服务端返回的消息
			$("#hintBody").text(data.msg);
			// 显示提示框
			$("#hintModal").modal("show");
			// 2s 后自动隐藏提示框
			setTimeout(function(){
				$("#hintModal").modal("hide");
				// 刷新页面
				if (data.code == 0) {
					window.location.reload();
				}
			}, 2000);

		}
	)
}