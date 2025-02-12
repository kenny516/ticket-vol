<%@ page import="com.mg.model.Vol" %>
<%@ page import="com.mg.model.TypeSiege" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Réserver un vol</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>Réservation de vol</h2>

    <%-- Affichage des erreurs --%>
    <%
        String error = (String) request.getAttribute("error");
        if (error != null && !error.isEmpty()) {
    %>
    <div class="alert alert-danger">
        <%= error %>
    </div>
    <% } %>

    <%-- Si le vol est disponible, afficher ses détails --%>
    <%
        Vol vol = (Vol) request.getAttribute("vol");
        if (vol != null) {
    %>
    <div class="card mb-4">
        <div class="card-body">
            <h5 class="card-title">Détails du vol</h5>
            <p class="card-text">
                <strong>De:</strong> <%= vol.getVilleDepart().getNom() %><br>
                <strong>À:</strong> <%= vol.getVilleArrive().getNom() %><br>
                <strong>Date:</strong> <%= new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(vol.getDateDepart()) %>
                <br>
                <% for (int i = 0; i < vol.getAvion().getPlaces().size(); i++) { %>
                <strong>
                    <%= vol.getAvion().getPlaces().get(i).getTypeSiege().getDesignation() %>:
                </strong>
                <%= vol.getAvion().getPlaces().get(i).getPrix() %><br>
                <% } %>
            </p>
        </div>
    </div>

    <%-- Formulaire de réservation --%>
    <form action="<%= request.getContextPath() %>/vols/reserver" method="post" class="mt-4">
        <input type="hidden" name="volId" value="<%= vol.getId() %>">

        <div class="mb-3">
            <label for="typeSiegeId" class="form-label">Type de siège</label>
            <select name="typeSiegeId" id="typeSiegeId" class="form-select" required>
                <option value="">Sélectionnez un type de siège</option>
                <%
                    java.util.List<TypeSiege> typeSieges = (java.util.List<TypeSiege>) request.getAttribute("typeSieges");
                    if (typeSieges != null) {
                        for (TypeSiege typeSiege : typeSieges) {
                %>
                <option value="<%= typeSiege.getId() %>">
                    <%= typeSiege.getDesignation() %>
                </option>
                <% }
                }
                %>
            </select>
        </div>

        <div class="mb-3">
            <label for="nombrePlaces" class="form-label">Nombre de places</label>
            <input type="number" name="nombrePlaces" id="nombrePlaces" class="form-control" min="1" max="5" value="1"
                   required>
        </div>

        <button type="submit" class="btn btn-primary">Confirmer la réservation</button>
        <a href="<%= request.getContextPath() %>/vols/search" class="btn btn-secondary">Retour</a>
    </form>
    <% } %>
</div>
</body>
</html>
