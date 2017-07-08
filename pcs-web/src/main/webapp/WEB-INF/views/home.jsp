<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Homepage</title>

    <link rel="stylesheet" type="text/css" href="/src/main/webapp/WEB-INF/resources/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="/src/main/webapp/WEB-INF/resources/css/metisMenu.css">
    <link rel="stylesheet" type="text/css" href="/src/main/webapp/WEB-INF/resources/css/sb-admin-2.css">
    <link rel="stylesheet" type="text/css" href="/src/main/webapp/WEB-INF/resources/css/font-awesome.css">
    <link rel="stylesheet" type="text/css" href="/src/main/webapp/WEB-INF/resources/css/base.css">

    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap.js"></script>
    <script src="/js/metisMenu.js"></script>
    <script src="/js/sb-admin-2.js"></script>
    <script src="/js/base.js"></script>
    <script src="/js/jquery.cookie.js"></script>
    <script src="/js/listener.js"></script>
    <script src="/js/home.js"></script>
</head>

<body>
<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Security Module</a>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <!-- /.dropdown -->
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a href="<c:url value="#" />"><i class="fa fa-user fa-fw"></i>User profile</a>
                    </li>
                    <li class="divider"></li>
                    <li id="logout"><a href="<c:url value="/logout"/>"><i class="fa fa-sign-out fa-fw"></i>
                        Logout</a>
                    </li>
                </ul>
                <!-- /.dropdown-user -->
            </li>
            <!-- /.dropdown -->
        </ul>
        <!-- /.navbar-top-links -->
    </nav>

    <!-- Header -->
    <div class="row">
        <div class="col-lg-12">
            <c:if test="${'ROLE_ADMIN' == userService.getCurrentUser().getRole().getAuthority()}">
                <h1 class="page-header">Hello, admin ${user.name}!</h1>
            </c:if>

            <c:if test="${'ROLE_USER' == userService.getCurrentUser().getRole().getAuthority()}">
                <h1 class="page-header">Hello, user ${user.name}!</h1>
            </c:if>
        </div>
        <!-- /.col-lg-12 -->
    </div>
</div>
</div>

</body>
</html>