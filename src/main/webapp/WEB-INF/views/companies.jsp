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
    <title>Companies</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/main.css" rel="stylesheet">


    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</head>
<body>
<div class="container">
    <span>${error}${message}</span>
    <form:form modelAttribute="newCompany" method="post" class="decor">
        <div class="form-left-decoration"></div>
        <div class="form-right-decoration"></div>
        <div class="circle"></div>
        <div class="form-inner">
            <h3>New company</h3>
            <spring:bind path="name">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:input type="text" placeholder="Company name" path="name"></form:input>
                    <form:errors path="name"></form:errors>
                </div>
            </spring:bind>
            <spring:bind path="description">
                <div class="${status.error ? 'has-error' : ''}">
                    <form:textarea path="description" type="text"
                                   placeholder="Write the descrition..."></form:textarea>
                    <form:errors path="description"></form:errors>
                </div>
            </spring:bind>
            <button class="button30" type="submit">+</button>
        </div>
    </form:form>

    <div class="form-group">
        <ol class="rounded">
            <c:forEach items="${companies}" var="company">
                <li>
                    <div class="form-group-lg">
                        <a href="${contextPath}/company=${company.id}">
                            <p>${company.name} - ${company.dateFoundation}</p>
                        </a>
                        <label>
                            ${company.description}
                        </label>
                        <p>${company.cash}</p>
                    </div>
                </li>
            </c:forEach>
        </ol>
    </div>
    <form action="${contextPath}/feed">
        <button type="submit">Go back</button>
    </form>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>