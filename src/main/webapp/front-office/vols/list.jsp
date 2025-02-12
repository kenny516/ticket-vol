<%@ page import="java.util.List" %>
<%@ page import="com.mg.model.Vol" %>
<%@ page import="com.mg.DTO.VolDTO" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    List<Vol> vols = (List<Vol>) request.getAttribute("vols");
%>
<!DOCTYPE html>
<html>

<head>
    <title>Liste des Vols</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container mt-4">
    <div class="row mb-4">
        <div class="col">
            <h2>Recherche de Vols</h2>
        </div>
        <div class="col text-end">
            <c:if test="${not empty sessionScope.user}">
                <span class="me-3">Bienvenue, ${sessionScope.user.nom}</span>
                <a href="${pageContext.request.contextPath}/logout"
                   class="btn btn-outline-secondary">Déconnexion</a>
            </c:if>
        </div>
    </div>

    <!-- Search Form -->
    <div class="card mb-4">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/vols/list" method="get" class="row g-3">
                <div class="col-md-3">
                    <label for="villeDepart" class="form-label">Ville de départ</label>
                    <select name="villeDepart" id="villeDepart" class="form-select">
                        <option value="">Tous</option>
                        <c:forEach items="${villes}" var="ville">
                            <option value="${ville.id}" ${param.villeDepart==ville.id ? 'selected' : '' }>
                                    ${ville.nom}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="villeArrive" class="form-label">Ville d'arrivée</label>
                    <select name="villeArrive" id="villeArrive" class="form-select">
                        <option value="">Tous</option>
                        <c:forEach items="${villes}" var="ville">
                            <option value="${ville.id}" ${param.villeArrive==ville.id ? 'selected' : '' }>
                                    ${ville.nom}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="dateDepart" class="form-label">Date de départ</label>
                    <input type="date" name="dateDepart" id="dateDepart" class="form-control"
                           value="${param.dateDepart}">
                </div>
                <div class="col-md-2">
                    <label for="maxPrice" class="form-label">Prix maximum</label>
                    <input type="number" name="maxPrice" id="maxPrice" class="form-control"
                           value="${param.maxPrice}">
                </div>
                <div class="col-md-1">
                    <label class="form-label">&nbsp;</label>
                    <button type="submit" class="btn btn-primary w-100">Filtrer</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Results Table -->
    <div class="table-responsive">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Vol N°</th>
                <th>Départ</th>
                <th>Arrivée</th>
                <th>Date</th>
                <th>Prix</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <% for (Vol vol : (List<Vol>) request.getAttribute("vols")) { %>
            <tr>
                <td><%= vol.getId() %></td>
                <td><%= vol.getVilleDepart().getNom() %></td>
                <td><%= vol.getVilleArrive().getNom() %></td>
                <td><%= vol.getDateDepart() %></td>
                <td>
                    <% for (int i = 0; i < vol.getAvion().getPlaces().size(); i++) { %>
                    <p>
                        <%= vol.getAvion().getPlaces().get(i).getTypeSiege().getDesignation() %> -
                        <%= vol.getAvion().getPlaces().get(i).getPrix() %>
                    </p>
                    <% } %>
                </td>
                <td>
                    <a href="<%= request.getContextPath() %>/reserver?volId=<%= vol.getId() %>"
                       class="btn btn-sm btn-primary">Réserver</a>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>