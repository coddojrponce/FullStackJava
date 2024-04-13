<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!-- Formatting (dates) --> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Page</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/app.js"></script><!-- change to match your file/naming structure -->
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="/dashboard">Welcome <c:out value="${user.userName}"></c:out></a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/dashboard">Dashboard</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/comments/new">Create a Comment</a>
        </li>         
        <li class="nav-item">
          <a class="nav-link text-danger" href="/logout">Logout</a>
        </li> 
        
      </ul>
      
    </div>
  </div>
</nav>
<form:form class="p-4" action="/comments/${comment.id}" method="POST" modelAttribute="comment">
	<h2>Edit Comment</h2>
	<input type="hidden" name="_method" value="PUT" />
  <div class="mb-3">
    <form:label path="text" for="" class="form-label">Comment</form:label>
    <form:input path="text" type="text" class="form-control" id="" aria-describedby=""/>
    <form:errors path="text"/>
    <form:errors path="user"/>
    
  </div>
  
  <div class="mb-3">
    <form:label path="image_url" for="" class="form-label">Image Url</form:label>
    <form:input path="image_url" type="text" class="form-control" id="" aria-describedby=""/>
    <form:errors path="image_url"/>
  </div>
   
  
  <button type="submit" class="btn btn-primary">Create Comment</button>
</form:form>


</body>
</html>