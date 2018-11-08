<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<title>上传显示</title>
</head>
<script src="../js/jquery-3.1.1.min.js"></script>
<script>
    function show(name,time,size){
    	$("#modName").val(name);
    	$("#time").val(time);
    	$("#size").val(size);
    }
    function test(id,uuid){
    	console.log(id);
    	console.log(uuid);
    }
	function imgChange(obj) {
		console.log("测试： "+$("#file").val());
		var index = 0;
		var file = document.getElementById("file");
		var container = document.getElementById("#preview");
		for (var i = 0; i < file.files.length; i++) {
			index = index +1;
 			var imgUrl = window.URL.createObjectURL(file.files[i]);
 			var fileName = file.files[i].name;
 			var size = file.files[i].size;
            var date = file.files[i].lastModifiedDate;
            var year = date.getFullYear();
            var month = date.getMonth()+1;
            var day = date.getDate();
            var hours = date.getHours();
            if (hours.toString().length<2) {
				hours = "0"+hours;
			}
            var minutes = date.getMinutes();
            if (minutes.toString().length<2) {
            	minutes = "0"+minutes;
			}
            var second = date.getSeconds();
            if (second.toString().length<2) {
            	second = "0"+second;
			}
            var time = year+"年"+month+"月"+day+"日"+hours+":"+minutes+":"+second;
 			var imgSrc = $("<img id='file"+index+"' src = '"+imgUrl+"' width = '50',height = '25' onclick=show('"+fileName+"','"+time+"','"+size+"');></img><br/>");
 			$("#preview").append(imgSrc);      	
		}
	}
</script>
<body style="font-size: 30px;">
<form action="${pageContext.request.contextPath}/user/uploadImg.do"
    method="post" enctype="multipart/form-data">
    <div class="upload_box"> 
          <b>上传图片</b> 
    <input type="file" name="file" id="file" accept="image/*" multiple="multiple" onchange="imgChange(this);" />
    <!--文件上传选择按钮-->
      <div id="preview">
        <!--文件显示位置-->
      </div>
      <div>
         文件名称:<input id="modName" type="text"/><br/>
         创建时间:<input id="time" type="text" readonly="readonly"/><br/>
         文件大小:<input id="size" type="text" readonly="readonly"/><br/>
         <input type="button" value="提交" onclick="test('123','456');"/>
      </div>
    </div>
</form>
</body>
</html>