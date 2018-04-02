<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="/public/css/app.css" />
    <link rel="stylesheet" href="/public/css/style.css" />
</head>
<body class="text-center">
    <%@ include file="../_shared/header.jsp" %>
    <div class="view-area">
    <form action="/" method="get" class="form-inline">
      <div class="form-group  mx-sm-3 mb-2">
        <input type="text" class="form-control" id="keywords"  name="keywords" placeholder="Keywords..." value="${keywords}">
      </div>
      <button type="submit" class="btn btn-primary mb-2">Search</button>
    </form>

    <div class="container-fluid">
    <div class="row">
    <c:forEach items="${result}" var="item">
    <div class="col-sm-3">
    <div class="card">
      <img class="card-img-top" src="${item.imageUrl}" alt="image">
      <div class="card-body text-left">
        <h5 class="card-title"><c:forEach items="${item.similarityScores}" var="score"><span><c:out value="${score.category}"/></span> </c:forEach></h5>
        <p class="card-text">Image matches following labels</p>
      </div>
      <ul class="list-group list-group-flush text-left">
      <c:forEach items="${item.annotations}" var="annotation">
        <li class="list-group-item"><c:out value="${annotation.description}" /></li>
      </c:forEach>
      </ul>
      <div class="card-body text-left">
        Image Uploaded By: <strong><c:out value="${item.uploadedBy}"/></strong>
      </div>
    </div>
    </div>
    </c:forEach>
    </div>
    </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"  crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"  crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"  crossorigin="anonymous"></script>
    <script src="/public/js/app.js"></script>
</body>
</html>