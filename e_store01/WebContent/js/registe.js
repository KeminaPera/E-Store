$(function(){
	/*查询注册的用户名是否已经存在*/
	$("#username").blur(function(){
		var username = $(this).val();
		if("" == username){
			$("#username_span").html("<font style='color:red; font-size:12px;'>用户名不能为空</font>");
		}else{
			$.post("UserServlet",{method:"findUserByUsername",username:username},function(data){
				$("#username_span").html(data);
			});
		}
	});
	
	
	/*判断用户密码是否符合要求*/
	$("#inputPassword3").keyup(function(){
		var password = $(this).val();
		if(password.length<3 || password == ""){
			$("#password_span").html("<font style='color:red; font-size:12px;'>密码长度不够</font>");
		}else if(password.length >= 3 && password.length <= 7){
			$("#password_span").html("<font style='color:green; font-size:12px;'>密码可用</font>");
		}else{
			$("#password_span").html("<font style='color:red; font-size:12px;'>密码长度太长</font>");
		}
	});
	
	
	/*判断用户两次输入密码是否一致*/
	$("#confirmpwd").blur(function(){
		var password = $("#inputPassword3").val();
		var psw = $(this).val();
		if(password === psw && psw != ""){
			$("#confirmpwd_span").html("<font style='color:green; font-size:12px;'>OK!</font>");
		}else{
			$("#confirmpwd_span").html("<font style='color:red; font-size:12px;'>密码不一致</font>");
		}
	});
	
});


