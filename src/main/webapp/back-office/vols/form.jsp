<%@ page import="com.mg.model.Ville" %>
<%@ page import="com.mg.model.Vol" %>
<%@ page import="com.mg.model.Avion" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    Vol vol = (Vol) request.getAttribute("vol");
    List<Ville> villes = (List<Ville>) request.getAttribute("villes");
    List<Avion> avions = (List<Avion>) request.getAttribute("avions");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    SimpleDateFormat sdfDisplay = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat df = new DecimalFormat("#,##0.00 Ar");
%>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title><%= (vol != null) ? "Modifier" : "Créer" %> un Vol - Back Office</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container mt-4">
    <h2><%= (vol != null) ? "Modifier" : "Créer" %> un Vol</h2>

    <form action="<%= request.getContextPath() %>/admin/vols/<%= (vol != null) ? "edit" : "create" %>"
          method="post" class="needs-validation" novalidate>

        <% if (vol != null) { %>
        <input type="hidden" name="id" value="<%= vol.getId() %>">
        <% } %>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="villeDepartId" class="form-label">Ville de départ</label>
                <select name="villeDepartId" class="form-select" required>
                    <option value="">Sélectionnez une ville</option>
                    <% for (Ville ville : villes) { %>
                    <option value="<%= ville.getId() %>"
                            <%= (vol != null && vol.getVilleDepart().getId().equals(ville.getId())) ? "selected" : "" %>>
                        <%= ville.getNom() %>
                    </option>
                    <% } %>
                </select>
            </div>

            <div class="col-md-6">
                <label for="villeArriveId" class="form-label">Ville d'arrivée</label>
                <select name="villeArriveId" class="form-select" required>
                    <option value="">Sélectionnez une ville</option>
                    <% for (Ville ville : villes) { %>
                    <option value="<%= ville.getId() %>"
                            <%= (vol != null && vol.getVilleArrive().getId().equals(ville.getId())) ? "selected" : "" %>>
                        <%= ville.getNom() %>
                    </option>
                    <% } %>
                </select>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="avionId" class="form-label">Avion</label>
                <select name="avionId" class="form-select" required>
                    <option value="">Sélectionnez un avion</option>
                    <% for (Avion avion : avions) { %>
                    <option value="<%= avion.getId() %>"
                            <%= (vol != null && vol.getAvion().getId().equals(avion.getId())) ? "selected" : "" %>>
                        <%= avion.getModele() %> (Fabriqué le: <%= sdfDisplay.format(avion.getDateFabrication()) %>)
                    </option>
                    <% } %>
                </select>
            </div>

            <div class="col-md-6">
                <label for="prix" class="form-label">Prix</label>
                <div class="input-group">
                    <input type="number" step="0.01" class="form-control" name="prix"
                           value="<%= (vol != null) ? vol.getPrix() : "" %>" required>
                    <span class="input-group-text">Ar</span>
                </div>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="dateDepart" class="form-label">Date et heure de départ</label>
                <input type="datetime-local" class="form-control" name="dateDepart"
                       value="<%= (vol != null) ? sdf.format(vol.getDateDepart()) : "" %>" required>
            </div>
        </div>

        <div class="mb-3">
            <button type="submit" class="btn btn-primary">Enregistrer</button>
            <a href="<%= request.getContextPath() %>/admin/vols" class="btn btn-secondary">Annuler</a>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Validation des formulaires Bootstrap
    (function () {
        'use strict';
        var forms = document.querySelectorAll('.needs-validation');
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
    })();
</script>
</body>

</html>
