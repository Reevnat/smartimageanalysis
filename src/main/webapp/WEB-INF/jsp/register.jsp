<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
        <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            <title>Registration</title>
                <meta charset="utf-8">
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" crossorigin="anonymous">
                <link rel="stylesheet" href="/public/css/app.css" />
        </head>
<body>
                <form:form id="regForm" modelAttribute="user" action="registerProcess" method="post">
                    <table align="center">
                        <tr>
                            <td>
                                <form:label path="email">Email:</form:label>
                            </td>
                            <td>
                                <form:input path="email" name="email" id="email" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <form:label path="password">Password:</form:label>
                            </td>
                            <td>
                                <form:password path="password" name="password" id="password" />
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:button id="register" name="register">Register</form:button>
                            </td>
                        </tr>
                        <tr></tr>
                        <tr>
                            <td></td>
                            <td><a href="login">Login</a>
                            </td>
                        </tr>
                    </table>
                </form:form>
</body>
</html>