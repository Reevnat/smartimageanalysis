<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title></title>
</head>
<body>
    <%@ include file="../_shared/header.jsp" %>
    <h1>Welcome SpingMVC4 Home Page! <br> Tanveer Rahman ${Test}</h1>

    <form method="post" enctype="multipart/form-data" action="/upload-file">
    <input name="file" type="file" />
    <button type="submit">Submit</button>
    <%@ include file="../_shared/footer.jsp" %>
    </form>
</body>
</html>