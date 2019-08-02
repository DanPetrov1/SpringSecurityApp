<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>News</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/newPost.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/main.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <a href="${contextPath}/profile" class="button5">Profile</a>
    <a href="${contextPath}/users" class="button5">Users</a>
    <a onclick="document.forms['logoutForm'].submit()" class="button5">Logout</a>

        <form:form modelAttribute="newPost" method="post" id="newPost" class="decor">

            <h2>Welcome ${pageContext.request.userPrincipal.name}</h2>
            <span>${message}</span>

            <div class="form-left-decoration"></div>
                <div class="form-right-decoration"></div>
                <div class="circle"></div>
                <div class="form-inner">
                    <h3>New post</h3>
                    <form:form modelAttribute="topicName">
                        <spring:bind path="topicName">
                            <form:input type="text" placeholder="Topic name" path="topicName"></form:input>
                        </spring:bind>
                    </form:form>
                    <div class="${error != null ? 'has-error' : ''}">
                        <span>${error}</span>
                    </div>
                    <spring:bind path="message">
                        <div class="${status.error ? 'has-error' : ''}">
                            <form:textarea path="message" type="text"
                                           placeholder="Write the message..."></form:textarea>
                            <form:errors path="message"></form:errors>
                        </div>
                    </spring:bind>
                    <button class="button30" type="submit">+</button>
                </div>
        </form:form>

        <div class="form-group">
            <ol class="rounded">
                <c:forEach items="${posts}" var="post">
                    <li>
                        <div class="form-group-lg">
                            <a href="#">
                                <p>Date of creation - ${post.creationDate}</p>
                                <label>
                                    ${post.message}
                                </label>
                            </a>
                        </div>
                    </li>
                </c:forEach>
            </ol>
        </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>