<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>LittleWeb</title>
    <link href="dist/css/bootstrap.css" rel="stylesheet">
    <link href="docs-assets/css/docs.css" rel="stylesheet">
    <script src="dist/js/jquery.js"></script>
    <script src="dist/js/bootstrap-dropdown-v1.js"></script>
</head>
<body>
  <header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner">
    <div class="container">
        <div class="navbar-header">
            <a href="index.jsp" class="navbar-brand">LittleWeb</a>
        </div>
        <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
            <ul class="nav navbar-nav">
                <li class="dropdown">
              			<a href="#" class="dropdown-toggle" data-toggle="dropdown">切换环境<b class="caret"></b></a>
		             	<ul class="dropdown-menu">
			                <li><a href = "http://ceshi.alpha.dp:8080"> DEV 环境</a></li>
			                <li><a href = "http://192.168.199.120/"> BETA 环境</a></li>
		              	</ul>
		        </li>		            
                <li>
                    <a href="yunma.jsp">云马</a>
                </li>
                <li>
                    <a href="ymm.jsp">运满满</a>
                </li>
                <li>
                    <a href="new.jsp">探索业务</a>
                </li>

            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="help.jsp">帮助</a>
                </li>
            </ul>
        </nav>
    </div>
</header>
</body>

<!-- <div id="footer">
   <div class="container">
   		<center><p class="text-muted">© DP-QA 2013-2014</p></center>
   	</div>
</div> -->


</html>