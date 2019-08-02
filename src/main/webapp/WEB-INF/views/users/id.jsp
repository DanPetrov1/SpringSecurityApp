<%@ page import="com.crowdfunding.model.Role" %>
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
    <title>${user.username}'s profile</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/main.css" rel="stylesheet">


    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</head>
<body>

<div class="row">
    <div class="col-md-offset-2 col-md-8 col-lg-offset-3 col-lg-6">
        <div class="well profile">
            <c:if test="${noRole == null}">
            <div class="col-sm-12">
                <div class="col-xs-12 col-sm-8">
                    <h2>${user.username}</h2>
                    <p><strong>Email: </strong> ${user.email}. </p>
                </div>
                <div class="col-xs-12 col-sm-4 text-center">
                    <figure>
                        <img src="${contextPath}/resources/img/user_profile.png" alt="user" class="img-circle img-responsive">
                    </figure>
                </div>
            </div>
            <div class="col-xs-12 divider text-center">
                <c:if  test="${adminRole != null}">
                    <span>${warning}</span>
                    <form:form method="post" modelAttribute="editPassword" class="form-signin">
                        <h2 class="form-signin-heading">Edit user</h2>
                        <span>${message}</span>
                        <spring:bind path="password">
                            <form:input type="password" path="password" placeholder="New password"></form:input>
                        </spring:bind>

                        <spring:bind path="confirmPassword">
                            <form:input type="password" path="confirmPassword"
                                            placeholder="Confirm your password"></form:input>
                        </spring:bind>
                        <div class="${error != null ? 'has-error' : ''}">
                            <span>${error}</span>
                        </div>
                    <div class="col-xs-12 col-sm-4 emphasis">
                        <input class="btn btn-success btn-block" type="submit" value="Submit">
                    </div>
                    </form:form>
                    <form:form method="post" class="form-signin">
                    <div class="col-xs-12 col-sm-4 emphasis">
                        <button class="btn btn-success btn-block" type="submit"><span class="fa fa-plus-circle">
                            <c:if test="${adminRole.id == 2 || adminRole.id == 1}">Block</c:if><c:if test="${adminRole.id == 4}"> Unblock</c:if>
                        </span></button>
                    </div>
                    </form:form>
                </c:if>
            </div>
            </c:if>
            <c:if test="${noRole != null}">
                <p><strong>Warning!</strong> Access closed!</p>
            </c:if>
            <button><a href="${contextPath}/feed">Go back</a></button>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>