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
    <table class="table">
        <thead class="thead-light">
        <tr>
        <th></th>
        <th></th>
        <th>Images like me</th>
        <th></th>
        <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${images}" var="item">
                <tr>
                  <td><img src="${item.url}" alt="image" class="my-image img-fluid" /></td>
                  <td>
                  <table class="table table-sm">
                      <thead class="thead-light">
                        <tr>
                          <th scope="col">Labels</th>
                          <th scope="col">Score</th>
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
                  </td>
                  <td>
                  <table class="table table-sm">
                    <thead class="thead-light">
                      <tr>
                        <th scope="col">Category</th>
                        <th scope="col">Score</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${item.similarityScores}" var="score">
                    <tr>
                        <td><a href="/similar-images?categoryId=${score.categoryId}"><c:out value="${score.category}"/></a></td>
                        <td><c:out value="${score.score}"/></td>
                    </tr>
                    </c:forEach>
                    </tbody>
                  </table></td>
                </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="/public/js/app.js"></script>
</body>
</html>