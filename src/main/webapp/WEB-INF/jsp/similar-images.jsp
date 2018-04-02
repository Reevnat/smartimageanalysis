<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="/public/css/app.css" />
    <link rel="stylesheet" href="/public/css/style.css" />
</head>
<body>
    <%@ include file="../_shared/header.jsp" %>
    <div class="small-divider">
    <div class="view-area">
    <h2>Similar Images</h2>

    <div class="container-fluid">
        <div class="row">
        <c:forEach items="${images}" var="item">
        <div class="col-sm-3">
        <div class="card">
          <img class="card-img-top" src="${item.url}" alt="image">
          <div class="card-body text-left">
          <table class="table table-sm">
            <thead class="thead-light">
              <tr>
                <th scope="col"></th>
                <th scope="col">Similarity score</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach items="${item.annotations}" var="annotation">
            <tr>
                <td><c:out value="${annotation.description}"/></td>
                <td><c:out value="${annotation.score}"/></td>
            </tr>
            </c:forEach>
            </tbody>
          </table>
          </div>
          <div class="card-body text-left">
            Image Uploaded By: <strong><c:out value="${item.uploadedBy}"/></strong>
          </div>
        </div>
        </div>
        </c:forEach>
        </div>
    </div>
    </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="/public/js/app.js"></script>
</body>
</html>