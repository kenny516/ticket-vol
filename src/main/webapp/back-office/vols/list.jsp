<%@ page import="com.mg.model.Ville" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mg.model.Vol" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    List<Ville> villes = (List<Ville>) request.getAttribute("villes");
    List<Vol> vols = (List<Vol>) request.getAttribute("vols");

    Integer villeDepartId = (request.getAttribute("villeDepartId") != null) ? (Integer) request.getAttribute("villeDepartId") : null;
    Integer villeArriveId = (request.getAttribute("villeArriveId") != null) ? (Integer) request.getAttribute("villeArriveId") : null;
    String dateDebut = (request.getAttribute("dateDebut") != null) ? (String) request.getAttribute("dateDebut") : "";
    String dateFin = (request.getAttribute("dateFin") != null) ? (String) request.getAttribute("dateFin") : "";
    Double prixMin = (request.getAttribute("prixMin") != null) ? (Double) request.getAttribute("prixMin") : null;
    Double prixMax = (request.getAttribute("prixMax") != null) ? (Double) request.getAttribute("prixMax") : null;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    DecimalFormat df = new DecimalFormat("#,##0.00 Ar");
%>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title>Gestion des Vols - Back Office</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container mt-4">
    <h2>Gestion des Vols</h2>

    <!-- Formulaire de recherche -->
    <div class="card mb-4">
        <div class="card-body">
            <h5 class="card-title">Recherche avancée</h5>
            <form action="<%= request.getContextPath() %>/admin/vols" method="get" class="row g-3">
                <div class="col-md-3">
                    <label for="villeDepartId" class="form-label">Ville de départ</label>
                    <select id="villeDepartId" name="villeDepartId" class="form-select">
                        <option value="">Toutes</option>
                        <% for (Ville ville : villes) { %>
                        <option value="<%= ville.getId() %>" <%= (villeDepartId != null && ville.getId().equals(villeDepartId)) ? "selected" : "" %>>
                            <%= ville.getNom() %>
                        </option>
                        <% } %>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="villeArriveId" class="form-label">Ville d'arrivée</label>
                    <select id="villeArriveId" name="villeArriveId" class="form-select">
                        <option value="">Toutes</option>
                        <% for (Ville ville : villes) { %>
                        <option value="<%= ville.getId() %>" <%= (villeArriveId != null && ville.getId().equals(villeArriveId)) ? "selected" : "" %>>
                            <%= ville.getNom() %>
                        </option>
                        <% } %>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="dateDebut" class="form-label">Date début</label>
                    <input type="datetime-local" id="dateDebut" class="form-control" name="dateDebut" value="<%= dateDebut %>">
                </div>
                <div class="col-md-3">
                    <label for="dateFin" class="form-label">Date fin</label>
                    <input type="datetime-local" id="dateFin" class="form-control" name="dateFin" value="<%= dateFin %>">
                </div>
                <div class="col-md-2">
                    <label for="prixMin" class="form-label">Prix min</label>
                    <input type="number" id="prixMin" step="0.01" class="form-control" name="prixMin" value="<%= prixMin != null ? prixMin : "" %>">
                </div>
                <div class="col-md-2">
                    <label for="prixMax" class="form-label">Prix max</label>
                    <input type="number"  id="prixMax" step="0.01" class="form-control" name="prixMax" value="<%= prixMax != null ? prixMax : "" %>">
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Rechercher</button>
                    <a href="<%= request.getContextPath() %>/admin/vols/create" class="btn btn-success">Nouveau Vol</a>
                </div>
            </form>
        </div>
    </div>

    <!-- Liste des vols -->
    <div class="table-responsive">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Départ</th>
                <th>Arrivée</th>
                <th>Date de départ</th>
                <th>Avion</th>
                <th>Prix</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <% for (Vol vol : vols) { %>
            <tr>
                <td><%= vol.getId() %></td>
                <td><%= vol.getVilleDepart().getNom() %></td>
                <td><%= vol.getVilleArrive().getNom() %></td>
                <td><%= sdf.format(vol.getDateDepart()) %></td>
                <td><%= vol.getAvion().getModele() %></td>
                <td><%= df.format(vol.getPrix()) %></td>
                <td>
                    <a href="<%= request.getContextPath() %>/admin/vols/edit?id=<%= vol.getId() %>"
                       class="btn btn-sm btn-warning">Modifier</a>
                    <a href="<%= request.getContextPath() %>/admin/promotions?volId=<%= vol.getId() %>"
                       class="btn btn-sm btn-info">Promotions</a>
                    <form action="<%= request.getContextPath() %>/admin/vols/delete" method="post" style="display: inline;">
                        <input type="hidden" name="id" value="<%= vol.getId() %>">
                        <button type="submit" class="btn btn-sm btn-danger"
                                onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce vol ?')">
                            Supprimer
                        </button>
                    </form>
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
