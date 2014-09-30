<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage</title>
<link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.10.4.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/uploadfile.css" />
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui-1.10.4.js"></script>
<script src="js/jquery.uploadfile.min.js"></script>
<script type="text/javascript">

	jQuery(document).ready(function(){
		var settings = {
			    url: "MapUpload",
			    dragDrop:true,
			    fileName: "file",
			    allowedTypes:"jpg,png,gif",	
			    returnType:"text", 
			    onSuccess: function(files, data, xhr) {
			    	
			    	response = data.split("|");
			    	
			    	console.log(response);
			    	
			    	if(response[0] == "OK") {
			    		sendAddMapRequest($('#mapName').val(), response[1]);	
			    	}
			    },
			    autoSubmit: false			
			}
		
		var uploadObj = $("#mulitplefileuploader").uploadFile(settings);
		
		$("#btnSubmit").click(function() {
			uploadObj.startUpload();
		});
		
		var sendAddMapRequest = function(mapName, mapURL) {
			var jsonData = '{"mapName": "' + mapName + '", "mapURL" : "' + mapURL + '"}';
			   $.ajax
			   ({
			      type: "POST",
			      contentType : 'application/json',
			      url: 'api/map',
			      data: jsonData ,
			      success: function () {
			    	  alert("Map has been added");
			    	  $('#mapName').val('');
			    	  uploadObj.cancelAll();
			      }
			  });
		};
		
		$("#btnSubmitUser").click(function() {
			var jsonData = '{"userName": "' + $('#userName').val() + '", "name" : "' + $('#name').val() + '", "password" : "' + $('#password').val() + '"}';
			   $.ajax
			   ({
			      type: "POST",
			      contentType : 'application/json',
			      url: 'api/user',
			      data: jsonData ,
			      success: function () {
			    	  alert("User has been added");
			    	  $('#userName').val('');
			    	  $('#name').val('');
			      }
			  });
		});
	});
	
</script>
</head>
<body>
	<h2>Add new map</h2>
	
	<div id="mulitplefileuploader">Upload</div>
	<br/>
	Give map a name: <input type="text" id="mapName" /><br/>
	<input type="submit" id="btnSubmit" value="Add Map" />
	
	<div id="status"></div>
	
	<hr />
	<h2>Add new user</h2>
	Username:&nbsp; <input type="text" id="name" /><br/>
	Password:&nbsp; <input type="text" id="password" /><br/>
	Full Name: <input type="text" id="userName" /><br/>
	<input type="submit" id="btnSubmitUser" value="Add User" />
</body>
</html>