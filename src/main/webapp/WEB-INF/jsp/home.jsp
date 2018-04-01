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
    <form action="/" method="get">
      <div class="form-group">
        <label for="keywords">Keywords</label>
        <input type="text" class="form-control" id="keywords"  name="keywords" placeholder="Search" value="${keywords}">
      </div>
      <button type="submit" class="btn btn-primary">Search</button>
    </form>
    <ul class="list-unstyled">
        <c:forEach items="${result}" var="item">
             <li class="media">
                <img class="mr-3" src="${item.imageUrl}" alt="image">
                <div class="media-body">
                  <h5 class="mt-0 mb-1">List-based media object</h5>
                  Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
                </div>
              </li>
        </c:forEach>
    </ul>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"  crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"  crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"  crossorigin="anonymous"></script>
    <script src="/public/js/app.js"></script>
</body>
</html>