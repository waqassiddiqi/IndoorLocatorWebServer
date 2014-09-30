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
			+ request.getServerPort() + request.getContextPath() %>/api/location/map/<%= request.getParameter("mapId") %>';
	
			
		var naturalWidth;
		var naturalHeight;
		
		jQuery(document).ready(function(){
			
			$.getJSON(url, function(data) {
				console.log(data);
				
				if(data) {
					document.title = 'Locations on Map - ' + data.mapName;
					$('#image').attr('src', 'mapuploads/' + data.mapURL);
					
					
					
				}
	        });
			
			$.getJSON(trackingUrl, function(data) {
				
				var image = new Image();
				image.src = $('#image').attr("src");
				
				console.log('width: ' + image.naturalWidth + ' and height: ' + image.naturalHeight);
				console.log(data);
				
				if(data) {
					
					var arr = [];
					
					console.log($.isArray(data.location));
					
					if($.isArray(data.location) == false ) {
						
						console.log(data.location);
						
						arr.push({
					        x: data.location.mapXcord / image.naturalWidth,
					        y: data.location.mapYcord / image.naturalHeight,
					        note: '<center><b>' + data.location.symbolicID + '</b></center>'	
					    });
						
					} else {
						for(h in data.location) {
							console.log(data.location[h]);
							
							arr.push({
						        x: data.location[h].mapXcord / image.naturalWidth,
						        y: data.location[h].mapYcord / image.naturalHeight,
						        note: '<center><b>' + data.location[h].symbolicID + '</b></center>'	
						    });
						}
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