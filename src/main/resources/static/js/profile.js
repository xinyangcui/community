$(function(){
	$(".follow-btn").click(follow);
});

function follow() {

	// // 发送AJAX请求之前,将CSRF令牌设置到请求的消息头中.
	// var token = $("meta[name='_csrf']").attr("content");
	// var header = $("meta[name='_csrf_header']").attr("content");
	// $(document).ajaxSend(function(e, xhr, options){
	// 	xhr.setRequestHeader(header, token);
	// });
	var btn = this;
	if($(btn).hasClass("btn-info")) {
		// 关注TA
		$.post(
			CONTEXT_PATH + "/follow",
			{"entityType":3, "entityId":$(btn).prev().val()},
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
	} else {
		// 取消关注
		$.post(
			CONTEXT_PATH + "/unfollow",
			{"entityType":3, "entityId":$(btn).prev().val()},
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
}