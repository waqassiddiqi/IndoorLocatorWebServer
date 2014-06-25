<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<%
	String mapId = request.getParameter("mapId");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Live Tracking</title>
<style type="text/css" media="all">@import "css/marker.css";</style>
<link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.10.4.css" />
<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4.js"></script>
<script type="text/javascript" src="js/jquery.fs.zoetrope.min.js"></script>
<script type="text/javascript" src="js/toe.min.js"></script>
<script type="text/javascript" src="js/imgViewer.min.js"></script>
<script type="text/javascript" src="src/imgNotes.js"></script>
</head>
<body>

	<div id="imgdiv" style="text-align: center">
		<img id="image" style="border: 1px solid #000000; padding: 20px;" width="50%"  />
	</div>

	<script type="text/javascript">
	
		var url = '<%= request.getScheme() + "://" + request.getServerName() + ":" 
			+ request.getServerPort() + request.getContextPath() %>/api/map/<%= request.getParameter("mapId") %>';
			
		var trackingUrl = '<%= request.getScheme() + "://" + request.getServerName() + ":" 
			+ request.getServerPort() + request.getContextPath() %>/api/history/live/<%= request.getParameter("mapId") %>';
	
		jQuery(document).ready(function(){
			
			$.getJSON(url, function(data) {
				console.log(data);
				
				if(data) {
					document.title = 'Live Tracking - ' + data.mapName;
					$('#image').attr('src', 'mapuploads/' + data.mapURL);										
					
				}
	        });
			
			$.getJSON(trackingUrl, function(data) {
				
				console.log(data);
				
				if(data) {
					
					var arr = [];
					var point = 0.2;
					var index = 1;
					for(h in data.history) {
						console.log(data.history[h]);
						
						arr.push({
					        x: point * index++,
					        y: 0.5,
					        note: '<center><b>' + data.history[h].user.name + '</b><br/></center>Task: <br/><span "text-size:1pt">Last seen: ' + data.history[h].date + '</span>'	
					    });
					}
					
					console.log(arr);
					buildMap(arr);
				}
	        });
		});
		
		function buildMap(data) {
			
			var $img = $("#image").imgNotes({
				onShow: $.noop,
				onAdd: function() {
						this.options.vAll = "bottom";
						this.options.hAll = "middle";
						var elem = $(document.createElement('span')).addClass("marker black").html(this.noteCount).attr("title", "");
						var self = this;
						$(elem).tooltip({
								content: function() {
											return $(elem).data("note");
										}
						});
						return elem;
				} 
			});
			
			$img.one("load",function(){
				$img.imgNotes("import", data);
			});
		}
	</script>
	
</body>
</html>