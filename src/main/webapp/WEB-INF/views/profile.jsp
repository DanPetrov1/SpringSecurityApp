<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="IE=edge">
        <meta name="viewpoint" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>My profile</title>

        <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/css/registration.css" rel="stylesheet">


        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    </head>
    <body>
<div class="container">
    <form:form method="POST" modelAttribute="newPassword" class="form-signin">
    <h1>${pageContext.request.userPrincipal.name}'s profile</h1>

        <h4 class="form-heading">Write new password to update and press the button</h4>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <spring:bind path="password">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="password" path="password" class="form-control" placeholder="New password"></form:input>
                    <form:errors path="password"></form:errors>
                </div>
            </spring:bind>
            
            <spring:bind path="confirmPassword">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="password" path="confirmPassword" class="form-control"
                                placeholder="Confirm your password"></form:input>
                    <form:errors path="confirmPassword"></form:errors>
                </div>
            </spring:bind>
            <button class="btn btn-warning btn-primary btn-block" type="submit">Update</button>
            <a href="/welcome" class="btn" class="btn-default">Back</a>
        </div>

    </form:form>
</div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    </body>
</html>