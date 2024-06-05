<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
<meta charset="ISO-8859-1">
<title>${team.name}</title>
<link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
</head>
<body>
	<div class="navbar bg-body-primary">
		<div class="container-fluid">
			<h1 class="text-center">Welcome, ${user.firstName}</h1>
			<div class="d-flex">
				<a href="/dashboard" class="btn btn-primary text-center mx-3">Dashboard</a>
				<a href="/logout" class="btn btn-danger text-center mx-3">Log Out</a>
			</div>
		</div>
	</div>
    <div class="main mx-auto w-75 my-3">
        <h2 class="text-center my-3">Team Information</h2>
        <h4 class="text-center my-3">Team Name: ${team.name}</h4>
        <h4 class="text-center my-3">Skill Level: ${team.skillLevel}</h4>
        <h4 class="text-center my-3">Game Day: ${team.gameDay}</h4>
        <h4 class="text-center my-3">Added By: ${team.user.firstName}</h4>
        <br>
    </div>
        <div class="d-flex text-center w-25 mx-auto justify-content-center">
            <c:if test="${user.id == team.user.id}">
                <a href="/teams/${team.id}/edit" class="btn btn-warning mx-3">Edit</a>
                <form action="/teams/${team.id}/delete" method="post">
                    <input type="hidden" name="_method" value="delete">
                    <input type="submit" class="btn btn-danger" value="Delete">
                </form>
            </c:if>
        </div>
            <div class="aside text-center w-25 mx-auto">
                <hr>
                <ul class="list-group">
                    <c:forEach var="player" items="${team.players}">
                        <li class="list-group-item">${player.playerName}</li>
                    </c:forEach>
                </ul>
                <p>${team.players.size()} / 9 players</p>
                <form:form action="/teams/${team.id}/addPlayer" method="post" modelAttribute="player">
                    <form:label path="playerName">Player Name:</form:label>
                    <form:errors path="playerName"></form:errors>
                    <form:input type="text" path="playerName" placeholder="Player Name" class="form-control" />
                    <button type="submit" class="btn btn-primary mt-2">Add</button>
                </form:form>
                <c:if test="${not empty error}">
                    <p style="color: red;">${error}</p>
                </c:if>
            </div>

</body>
</html>