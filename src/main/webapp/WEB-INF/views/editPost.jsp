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

    <title>Edit post ${post.id}</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/login.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>

<body>
    <h2>${author.username}</h2>
    <c:if test="${editPost != null}">
        <form:form modelAttribute="editPost" method="post">
            <spring:bind path="message">
                <div class="${status.error ? 'has-error' : ''}">
                    <label>
                        <p>${post.message}</p>
                        <form:textarea path="message" type="text"
                            placeholder="Write the message..."></form:textarea>
                    </label>
                    <span>${message}${error}</span>
                </div>
            </spring:bind>
            <button type="submit">Submit</button>
        </form:form>
    </c:if>

    <c:if test="${editPost == null}">
        <label>
            ${post.message}
        </label>
    </c:if>
    <form action="/users/id=${author.id}">
        <button type="submit">Go back</button>
    </form>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>