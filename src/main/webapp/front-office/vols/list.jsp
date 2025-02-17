<%@ page import="java.util.List" %>
<%@ page import="com.mg.model.Vol" %>
<%@ page import="com.mg.DTO.VolDTO" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.mg.model.Ville" %>
<%@ page import="com.mg.model.Utilisateur" %>
<%@ page import="com.mg.model.PlaceVol" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    List<Vol> vols = (List<Vol>) request.getAttribute("vols");
    List<Ville> villes = (List<Ville>) request.getAttribute("villes");
    Integer villeDepartId = (request.getAttribute("villeDepartId") != null) ?
            (Integer) request.getAttribute("villeDepartId") : null;
    Integer villeArriveId = (request.getAttribute("villeArriveId") != null) ?
            (Integer) request.getAttribute("villeArriveId") : null;
    String dateDepart = (request.getAttribute("dateDepart") != null) ? (String)
            request.getAttribute("dateDepart") : "";

    Double maxPrice = (request.getAttribute("maxPrice") != null) ? (Double)
            request.getAttribute("maxPrice") : null;
%>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
            <%
                Utilisateur user = (Utilisateur) session.getAttribute("user");
                if (user != null) {
            %>
            <span class="me-3">Bienvenue, <%= user.getNom() %></span>
            <a href="<%= request.getContextPath() %>/logout" class="btn btn-outline-secondary">Déconnexion</a>
            <%
                }
            %>
        </div>
    </div>

    <!-- Formulaire de recherche -->
    <div class="card mb-4">
        <div class="card-body">
            <form action="<%= request.getContextPath() %>/vols/search" method="post" class="row g-3">
                <div class="col-md-3">
                    <label for="villeDepart" class="form-label">Ville de départ</label>
                    <select name="villeDepart" id="villeDepart" class="form-select">
                        <option value="">Tous</option>
                        <% for (Ville ville : villes) { %>
                        <option
                                value="<%= ville.getId() %>"
                                <%=(villeDepartId != null &&
                                        ville.getId().equals(villeDepartId))
                                        ? "selected" : "" %>>
                            <%= ville.getNom() %>
                        </option>
                        <% } %>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="villeArrive" class="form-label">Ville d'arrivée</label>
                    <select name="villeArrive" id="villeArrive" class="form-select">
                        <option value="">Tous</option>
                        <% for (Ville ville : villes) { %>
                        <option
                                value="<%= ville.getId() %>"
                                <%=(villeArriveId != null &&
                                        ville.getId().equals(villeArriveId))
                                        ? "selected" : "" %>>
                            <%= ville.getNom() %>
                        </option>
                        <% } %>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="dateDepart" class="form-label">Date de départ</label>
                    <input type="date" name="dateDepart" id="dateDepart" class="form-control"
                           value="<%=dateDepart %>">
                </div>

                <div class="col-md-2">
                    <label for="maxPrice" class="form-label">Prix maximum</label>
                    <input type="number" name="maxPrice" id="maxPrice" class="form-control"
                           value="<%=maxPrice%>">
                </div>
                <div class="col-md-1">
                    <label class="form-label">&nbsp;</label>
                    <button type="submit" class="btn btn-primary w-100">Filtrer</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Tableau des résultats -->
    <div class="table-responsive">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Vol N°</th>
                <th>Avion</th>
                <th>Départ</th>
                <th>Arrivée</th>
                <th>Date</th>
                <th>Prix</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                for (Vol vol : vols) {
            %>
            <tr>
                <td><%= vol.getId() %>
                </td>
                <td><%= vol.getAvion().getModele() %>
                <td><%= vol.getVilleDepart().getNom() %>
                </td>
                <td><%= vol.getVilleArrive().getNom() %>
                </td>
                <td><%= vol.getDateDepart() %>
                </td>
                <td>
                    <%
                        for (PlaceVol placeVol :vol.getPlaceVols()) {
                            String designation = placeVol.getTypeSiege().getDesignation();
                            double prix = placeVol.getPrix();
                    %>
                    <p><%= designation %> - <%= prix %>AR</p>
                    <%
                        }
                    %>
                </td>
                <td>
                    <a href="<%= request.getContextPath() %>/reserver?volId=<%= vol.getId() %>"
                       class="btn btn-sm btn-primary">Réserver</a>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
