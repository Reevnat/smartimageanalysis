<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" crossorigin="anonymous">
                    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
                    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" href="/public/css/app.css" />
    <link rel="stylesheet" href="/public/css/style.css" />
</head>
<body class="text-center">
    <%@ include file="../_shared/header.jsp" %>

    <div class="cover-container d-flex h-75 p-3 mx-auto flex-column">
        <div class="view-area">

          <main role="main" class="inner cover">
            <h1 class="cover-heading">Smart Image Analysis</h1>
            <p class="lead">Quickly search the images that matches your own.</p>
            <p class="lead">
              <button type="button" class="btn btn-primary btn-lg" onclick="window.open('https://cloud.google.com/vision/')">Learn More</button>
            </p>
          </main>
      </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"  crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"  crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"  crossorigin="anonymous"></script>
    <script src="/public/js/app.js"></script>
</body>
</html>