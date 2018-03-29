<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            <title>Login</title>
                <meta charset="utf-8">
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" crossorigin="anonymous">
                <link rel="stylesheet" href="/public/css/app.css" />

                <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
                <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
                <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
                <link rel="stylesheet" href="/public/css/style.css" />
                <script language="javascript">
                $(function() {
                    $('#login-form-link').click(function(e) {
                		$("#login-form").delay(100).fadeIn(100);
                 		$("#register-form").fadeOut(100);
                		$('#register-form-link').removeClass('active');
                		$(this).addClass('active');
                		e.preventDefault();
                	});
                	$('#register-form-link').click(function(e) {
                		$("#register-form").delay(100).fadeIn(100);
                 		$("#login-form").fadeOut(100);
                		$('#login-form-link').removeClass('active');
                		$(this).addClass('active');
                		e.preventDefault();
                	});
                });
                </script>
        </head>
        <body>
            <div class="container">
                	<div class="row">
            			<div class="col-md-6 col-md-offset-3">
            				<div class="panel panel-login">
            					<div class="panel-heading">
            						<div class="row">
            							<div class="col-xs-12">
            								<a href="#" class="active" id="login-form-link">Login</a>
            							</div>
            						</div>
            						<hr>
            					</div>
            					<div class="panel-body">
            						<div class="row">
            							<div class="col-lg-12">
            								<form:form id="login-form" modelAttribute="login" action="loginProcess" method="post" role="form" style="display: block;">
            									<div class="form-group">
            										<form:input type="text" path="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email" value=""/>
            									</div>
            									<div class="form-group">
            										<form:input type="password" path="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password"/>
            									</div>
            									<div class="form-group">
            										<div class="row">
            											<div class="col-sm-6 col-sm-offset-0">
            											    <form:button id="login" name="login" class="btn btn-blue-grey btn-lg">Login</form:button>
            											</div>
            										</div>
            									</div>
                                                <div class="form-group">
            										<div class="row">
            											<div class="col-sm-6 col-sm-offset-0">
            											    <button type="button" class="btn btn-light-green btn-lg" onclick="window.open('/register','_self')">Register</button>
            											</div>
            										</div>
            									</div>
            									<div class="form-group">
                                                	<div class="row">
                                                		<div class="col-lg-12">
                                                			<div class="text-center">
                                                				<button type="button" class="btn btn-outline-danger waves-effect">${message}</button>
                                                			</div>
                                                		</div>
                                                	</div>
                                                </div>
            								</form:form>
            							</div>
            						</div>
            					</div>
            				</div>
            			</div>
            		</div>
            	</div>
     </body>
</html>