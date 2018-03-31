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
    <div class="cover-container d-flex h-75 p-3 mx-auto flex-column">
            <div class="view-area">
    <h2>Categories</h2>
    <a href="/add-category" class="btn btn-primary">Add New Category</a>
    <form method="post" action="">
    <table class="table table-bordered">
        <thead>
        <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Labels</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${result}" var="item">
                <tr>
                  <td><c:out value="${item.id}" /></td>
                  <td><c:out value="${item.name}" /></td>
                  <td>
                  <a href="/add-label?categoryId=${item.id}" class="btn btn-info btn-sm">Add New Label</a><br>
                  <table class="table table-sm">
                    <thead>
                      <tr>
                        <th scope="col">Label</th>
                        <th scope="col">Threshold</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${item.labels}" var="label">
                    <tr>
                        <td><c:out value="${label.description}" /></td>
                        <td><c:out value="${label.threshold}" /></td>
                    </tr>
                    </c:forEach>
                    </tbody>
                  </table>
                  </td>
                  <td><button type="submit" class="btn btn-danger" formaction="/delete-category?id=${item.id}" onclick="if(!confirm('Are you sure you want to delete this item?')) return false;">Delete</button>
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