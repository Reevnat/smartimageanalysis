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
    <h2>Edit User</h2>
    <form method="post" action="/edit-user">
        <div class="form-group">
            <label for="name">Change Password</label>
            <input type="text"  class="form-control" id="password" name="password" value="${password}">
        </div>
        <div class="form-check">
            <input type="checkbox" class="form-check-input" id="isAdmin" name="isAdmin">
            <label class="form-check-label" for="exampleCheck1">Make Admin</label>
        </div>
        <input type=hidden name="id" value="${id}"/>
        <a href="/users" class="btn btn-secondary">Back</a>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="/public/js/app.js"></script>
</body>
</html>