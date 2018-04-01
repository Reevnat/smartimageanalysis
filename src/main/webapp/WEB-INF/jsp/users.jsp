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
<h1>List of users</h1>
<form action="/" method="post">
<table class="table table-striped table-bordered">
  <thead class="thead-light">
    <tr>
      <th scope="col">Id</th>
      <th scope="col">Email</th>
      <th scope="col">Option</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${users}" var="user">
          <tr>
            <th scope+"row"><c:out value="${user.id}" /></th>
            <td><c:out value="${user.email}" /></td>
            <td align="right">
            <button type="submit" class="btn btn-danger" formaction="/smartimageanalysis/delete-users?id=${user.id}">Delete</button>
            </td>
          </tr>
  </c:forEach>
  </tbody>
</table>
</form>
</div>
</div>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="/public/js/app.js"></script>
</body>
</html>