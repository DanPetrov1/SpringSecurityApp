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

    <title>Company ${company.name}</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>

<body>
<h2>${user.username}</h2>
<c:if test="${editCompany != null}">
    <form:form modelAttribute="editCompany" method="post">
        <spring:bind path="name">
            <div class="${status.error ? 'has-error' : ''}">
                <label>
                    <p>${company.name}</p>
                    <form:input path="name" type="text"
                                   placeholder="Correct the name"></form:input>
                </label>
            </div>
        </spring:bind>
        <spring:bind path="description">
            <div class="${status.error ? 'has-error' : ''}">
                <label>
                    <p>${company.description}</p>
                    <form:input path="description" type="text"
                                placeholder="Description"></form:input>
                </label>
            </div>
        </spring:bind>
        <span>${message}${error}</span>
        <button type="submit">Submit</button>
        <label>Cash: ${company.cash}</label>
    </form:form>
</c:if>

<c:if test="${editCompany == null}">
    <h3>${company.name}</h3>
    <label>
            ${company.description}
    </label><br>
    <label>Cash: ${company.cash}</label>
</c:if>
<form action="/users/id=${user.id}">
    <button type="submit">Go back</button>
</form>
<form action="/company=${company.id}/share">
    <label>
        <input name="cash" value="0"/>
    </label>
    <button type="submit">Share</button>
</form>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>