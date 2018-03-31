<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar-toggle" aria-controls="navbar-toggle" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <a class="navbar-brand" href="/">Smart Image Analyis (SMIA)</a>

  <div class="collapse navbar-collapse" id="navbar-toggle">
    <ul class="navbar-nav mr-auto mt-2 mt-lg-0 ">
      <li class="nav-item active">
        <a class="nav-link" href="/">Home</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/my-images">My Images</a>
      </li>
       <li class="nav-item">
              <a class="nav-link" href="/users">Users</a>
       </li>
       <li class="nav-item">
             <a class="nav-link" href="/categories">Categories</a>
        </li>
    </ul>
    <span class="navbar-text">
          Hello <sec:authentication property="principal" />,

    </span>
    <ul class="navbar-nav">
        <li class="nav-item">
        <a class="nav-link" href="/logout">Logout</a>
        </li>
    </ul>
  </div>

</nav>