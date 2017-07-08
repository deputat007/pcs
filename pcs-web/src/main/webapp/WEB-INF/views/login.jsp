<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Login</title>

    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="/css/metisMenu.css">
    <link rel="stylesheet" type="text/css" href="/css/sb-admin-2.css">
    <link rel="stylesheet" type="text/css" href="/css/font-awesome.css">
    <link rel="stylesheet" type="text/css" href="/css/base.css">
    <link rel="stylesheet" type="text/css" href="/css/login.css">

    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap.js"></script>
    <script src="/js/metisMenu.js"></script>
    <script src="/js/sb-admin-2.js"></script>
    <script src="/js/base.js"></script>
    <script src="/js/jquery.cookie.js"></script>
    <script src="/js/listener.js"></script>
    <script src="/js/home.js"></script>
    <script src="/js/https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.16.0/jquery.validate.js"></script>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Please Sign in</h3>
                </div>
                <div class="panel-body">
                    <form:form role="form" method="POST" modelAttribute="loginForm">
                        <fieldset>
                            <c:if test="${not empty successMessage}">
                                <div class="alert alert-success">
                                    <span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
                                    <spring:message code="${successMessage}"/>
                                </div>
                            </c:if>
                            <c:if test="${not empty informationMessage}">
                                <div class="alert alert-warning">
                                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                    <spring:message code="${informationMessage}"/>
                                </div>
                            </c:if>
                            <c:if test="${not empty loginError}">
                                <div class="alert alert-danger">
                                    <span class="glyphicon glyphicon-minus-sign" aria-hidden="true"></span>
                                    <spring:message code="${loginError}"/>
                                </div>
                            </c:if>
                            <c:if test="${not empty errorMessages}">
                                <div class="alert alert-danger">
                                    <c:forEach items="${errorMessages}" var="errorItem">
                                        <p><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                                ${errorItem.getDefaultMessage()}</p>
                                    </c:forEach>
                                </div>
                            </c:if>
                            <div class="form-group">
                                Login
                                <form:input path="login" class="form-control" placeholder="Your login"
                                            type="email"/>
                            </div>
                            <div class="form-group">
                                Password
                                <form:input path="password" class="form-control" placeholder="Your password"
                                            name="password" type="password" value=""/>
                            </div>
                            <input type="submit" class="btn btn-lg btn-success btn-block" value="Login" id="loginbtn"/>
                        </fieldset>
                    </form:form>
                    <div style="margin-top:8px;"><a href="<c:url value="../register"/>">Create Account</a></div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>