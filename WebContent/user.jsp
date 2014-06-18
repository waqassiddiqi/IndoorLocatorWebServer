<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Users</title>
<link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.10.4.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui-1.10.4.js"></script>
<script src="js/grid.locale-en.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script type="text/javascript">

	jQuery(document).ready(function(){
		var url = '<%= request.getScheme() + "://" + request.getServerName() + ":" 
				+ request.getServerPort() + request.getContextPath() %>/api/user'; 
		
		jQuery("#list3").jqGrid({
		   	url: url,
			datatype: "json",
		   	colNames:['ID', 'Name'],
		   	colModel:[
		   		{name:'id',index:'id', width:60, sorttype:"int"},
		   		{name:'name',index:'name', width:60}
		   		
		   		
		   	],
		   	jsonReader : { 
		        root: "user"
		   	},
		   	rowNum:20,
		   	rowList:[10,20,30],
		   	pager: '#pager3',
		   	sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    loadonce: true,
		    autowidth: true,
		    caption: "List of all users"
		});
		
	});
</script>
</head>
<body>
	<h2>Users</h2>
	<table id="list3"></table>
	<div id="pager3"></div>
</body>
</html>