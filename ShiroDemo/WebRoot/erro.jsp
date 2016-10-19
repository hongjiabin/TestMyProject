<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true" pageEncoding="UTF-8"%>  
<%  
/** 
* 本页面是在客户查找的页面无法找到的情况下调用的 
*/  
response.setStatus(HttpServletResponse.SC_OK);
String path = request.getRequestURI();
path = path.substring(0, path.lastIndexOf("/"));
 %>  
<body>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>错误</title>
        <style type="text/css">
        	/* 错误页面 */
			body.error_page
			{
    			background-color: #00629f;
    			background-image: url(../../images/error.png);
    			background-position: center top;
    			background-repeat: no-repeat;
			}
			body.error_power_page
			{
 			    background-color: #00629f;
			    background-image: url(../../../images/error_power.png);
			    background-position: center 40%;
			    background-repeat: no-repeat;
			}
			
			#error
			{
			    color: #FFF;
			    position: absolute;
				top: 64%;
				left: 15%;
			}
			#error span,#error a
			{
			    color:Yellow;
			}
			#error a:hover
			{
			    color:#FFF;
			}
			
        </style>
        <script language="javascript" type="text/javascript">
            var timer;
            //启动跳转的定时器
            function startTimes() {
                timer = window.setInterval(showSecondes,1000);
            }

            var i = 5;
            function showSecondes() {
                if (i > 0) {
                    i--;
                    document.getElementById("secondes").innerHTML = i;
                }
                else {
                    window.clearInterval(timer);
                    //location.href = "login.html";
                    history.back();
                }
            }

            //取消跳转
            function resetTimer() {
                if (timer != null && timer != undefined) {
                    window.clearInterval(timer);
                    //location.href = "login.html";
                    history.back();
                }
            }
        </script> 
    </head>
    <body class="error_page" onload="startTimes();">
    	<img alt="" src='<%= path %>/images/error.png' style="background-position: center top;background-repeat: no-repeat;padding-left: 15%;">
        <h1 id="error">
	        出现错误，&nbsp;<span id="secondes">5</span>&nbsp;秒后将自动跳转，立即跳转请点击&nbsp;
            <a  href="javascript:resetTimer();">返回</a>
        </h1>
    </body>
</html>