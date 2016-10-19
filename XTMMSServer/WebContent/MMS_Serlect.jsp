<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.xtone.mms.dao.MMSMonthData"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.xtone.mms.model.AddInfo"%>
<%@page import="java.util.List"%>
<%
	String league = request.getParameter("");
	String serverid = request.getParameter("");
	String startcustomtime = request.getParameter("");
	String endcustomtime = request.getParameter("");
	List<AddInfo> list = new ArrayList<AddInfo>();
	MMSMonthData model = new MMSMonthData();
	//list = model.SelectMonthAdd(league, serverid, startcustomtime, endcustomtime);
	list = model.SelectMonthAdd("1254", "102302", "2016-07-20", "2016-08-20");

%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to Tablecloth</title>

<!-- paste this code into your webpage -->
<link href="js-css/tablecloth.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="js-css/tablecloth.js"></script>
<script src="../js-css/jquery-1.7.js"></script>
<!-- end -->

<style>

body{
	margin:0;
	padding:0;
	background:#f1f1f1;
	font:70% Arial, Helvetica, sans-serif; 
	color:#555;
	line-height:150%;
	text-align:left;
}
a{
	text-decoration:none;
	color:#057fac;
}
a:hover{
	text-decoration:none;
	color:#999;
}
h1{
	font-size:140%;
	margin:0 20px;
	line-height:80px;	
}
h2{
	font-size:120%;
}
#container{
	margin:0 auto;
	width:680px;
	background:#fff;
	padding-bottom:20px;
}
#content{margin:0 20px;}
p.sig{	
	margin:0 auto;
	width:680px;
	padding:1em 0;
}
form{
	margin:1em 0;
	padding:.2em 20px;
	background:#eee;
}
</style>

</head>

<body>
<form action="">
	<select>
		<option value="">请选择合作方</option>
	</select>
	<select>
		<option>请选择业务代码</option>
	</select>
</form>
<div id="container">
	<h1>Tablecloth: Example</h1>
	
	<div id="content">


	<!-- all you need with Tablecloth is a regular, well formed table. No need for id's, class names... --> 
	
		<h2>Table with top headings</h2>
	
		<table cellspacing="0" cellpadding="0">
			<tr class='odd'>
				<th class="">province</th>
				<th class="">count</th>
			</tr>
			<%
				int i =1;
				for(AddInfo info : list){
					System.out.println(info.getProvince());
					if(i%2==0){
						%>
						<tr class="odd">
							<td class=""><%=info.getProvince() %></td>
							<td class=""><%=info.getCOUNT() %></td>
						</tr>
						<% 
					}else{
						%>
						<tr class="even">
							<td class=""><%=info.getProvince() %></td>
							<td class=""><%=info.getCOUNT() %></td>
						</tr>
						<% 
					}
				}
			%>
		</table>
	
		
</div>
<p class="sig">Tablecloth is brought to you by <a href="http://www.cssglobe.com">Css Globe</a></p>
	
	
</body>
</html>