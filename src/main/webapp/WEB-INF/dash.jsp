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
    <title>Dashboard</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/app.js"></script><!-- change to match your file/naming structure -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"><script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
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

<table class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Image</th>
      <th scope="col">Comment</th>
      <th scope="col">Owner</th>
      
      <th scope="col">Actions</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach var="comment" items="${comments}">
  <tr>
      <th scope="row">${comment.id}</th>
      <td><img class="w-25 rounded shadow" src="${comment.image_url}" alt="" /></td>
      <td>${comment.text}</td>
      <td>${comment.user.userName}</td>
      
      <td>
      	
      	<a href="/comments/${comment.id}">View</a> |
      	
      	<c:choose>
      	<c:when test="${userId == comment.user.id}">
			<a href="/comments/${comment.id}/edit">Edit</a>|
      		<form action="/comments/${comment.id}/delete" method="POST">
	      		<input type="hidden" name="_method" value="DELETE" />
	      		<input class="btn text-decoration-underline text-decoration-none text-primary" type="submit" value="Delete" />
      		</form>
		</c:when>
		<c:otherwise>
			<form action="comments/${comment.id}/likes/new" method="POST">
				<label for="likeButton"><i class="fa fa-heart-o" style="font-size:20px;color:red"></i>${comment.liked_comment.size() }</label>
				<input id="likeButton" class="d-none" type="submit" value="Like" >
			</form>
			<form action="comments/${comment.id}/likes/delete" method="POST">
				<input type="hidden" name="_method" value="DELETE" />
				<input class="" type="submit" value="Unlike" />
			</form>
		</c:otherwise>
      	
      	</c:choose>
      	
  

	</td>
    </tr>
  
  </c:forEach>
    
    
  </tbody>
</table>
   
</body>
</html>