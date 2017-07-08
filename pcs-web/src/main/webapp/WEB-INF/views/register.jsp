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
    <title>Registration</title>

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
        <div class="col-md-6 col-md-offset-3">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Sign up</h3>
                </div>
                <div class="panel-body">


                    <form:form role="form" method="POST" modelAttribute="registrationForm" class="form-horizontal"
                               htmlEscape="true">
                        <fieldset>

                            <!-- Error messages-->
                            <c:if test="${not empty errorMessage}">
                                <div class="alert alert-danger">
                                    <span class="glyphicon glyphicon-minus-sign" aria-hidden="true"></span>
                                    <spring:message code="${errorMessage}"/>
                                </div>
                            </c:if>
                            <c:if test="${not empty validationErrors}">
                                <div class="alert alert-danger">
                                    <c:forEach items="${validationErrors}" var="errorItem">
                                        <p><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                                ${errorItem.getDefaultMessage()}</p>
                                    </c:forEach>
                                </div>
                            </c:if>

                            <!-- Text input-->
                            <div class="form-group">
                                <form:label path="login" cssClass="col-md-4 control-label">Login</form:label>
                                <div class="col-md-8">
                                    <form:input path="login" class="form-control" placeholder="Nickname"
                                                type="text"/>
                                </div>
                            </div>

                            <!-- Number input-->
                            <div class="form-group">
                                <form:label path="phoneNumber" cssClass="col-md-4 control-label">Phone number</form:label>
                                <div class="col-md-8">
                                    <form:input path="phoneNumber" class="form-control" placeholder="380979890909"
                                                type="number"/>
                                </div>
                            </div>

                            <!-- Number input-->
                            <div class="form-group">
                                <form:label path="secretDigit" cssClass="col-md-4 control-label">Secret digit</form:label>
                                <div class="col-md-8">
                                    <form:input path="secretDigit" class="form-control" placeholder="e.g. 25"
                                                type="number"/>
                                </div>
                            </div>

                            <!-- Role choice -->
                            <div class="form-group">
                                <form:label path="role" cssClass="col-md-4 control-label">Role</form:label>
                                <div class="col-md-8">
                                <form:select path="role" class="form-control">
                                    <form:option value="None" label="-- Select --"/>
                                    <form:options items="${roles}"/>
                                </form:select>
                                </div>
                            </div>

                            <!-- Password input-->
                            <div class="form-group">
                                <form:label path="password" cssClass="col-md-4 control-label">Password</form:label>
                                <div class="col-md-8">
                                    <form:input path="password" class="form-control" placeholder="Your password"
                                                name="password" type="password" value=""/>
                                </div>
                            </div>

                            <!-- Confirm Password input-->
                            <div class="form-group">
                                <form:label path="confirmPassword" cssClass="col-md-4 control-label">Confirm Password
                                </form:label>
                                <div class="col-md-8">
                                    <form:input path="confirmPassword" class="form-control"
                                                placeholder="Confirm password"
                                                name="confirmPassword" type="password" value=""/>
                                </div>
                            </div>

                            <%--Create Account--%>
                            <input type="submit" class="btn btn-lg btn-success btn-block" value="Create Account"/>
                        </fieldset>
                    </form:form>


                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>