<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>List of usrs</h1>
<form action="/" method="post">
<table>
<thead>
<tr>
<th>
Id
</th>
<th>Email</th>
<th><th>
</tr>
</thead>
<c:forEach items="${users}" var="user">
        <tr>
          <td><c:out value="${user.id}" /><td>
          <td><c:out value="${user.email}" /><td>
          <td><button type="submit" formaction="/smartimageanalysis/delete-users?id=${user.id}">Delete</button></td>
        </tr>
</c:forEach>
</table>
</form>