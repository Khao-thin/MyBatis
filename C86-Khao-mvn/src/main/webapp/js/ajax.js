//封装好的ajax代码
function post(url,param,callback){
	var xhr;
	try{
		xhr=new XMLHttpRequest();
	}catch(e){
		xhr=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xhr.onreadystatechange=function(){
		if(xhr.readyState==4&&xhr.status==200){
			callback(xhr.responseText);
		}
	}
	xhr.open("POST",url);//开启连接 第三个参数true同步|false异步
	//如果是文件上传方式，请求不要设置Content-type
	if(!(param instanceof FormData)){
		//POST提交数据的编码方式
		xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	}
	//post提交数据的编码方式
	xhr.send(param);
}