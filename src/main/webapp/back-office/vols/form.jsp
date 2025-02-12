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
    String error = (String) request.getAttribute("error");

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

        <% if (error != null && !error.isEmpty()) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <%= error %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        <% } %>

        <form action="<%= request.getContextPath() %>/admin/vols/<%= (vol != null) ? "edit" : "create" %>"
              method="post" class="needs-validation" novalidate>

            <% if (vol != null) { %>
                <input type="hidden" name="id" value="<%= vol.getId() %>">
            <% } %>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="villeDepartId" class="form-label">Ville de départ *</label>
                    <select name="villeDepartId" id="villeDepartId" class="form-select" required>
                        <option value="">Sélectionnez une ville</option>
                        <% for (Ville ville : villes) { %>
                            <option value="<%= ville.getId() %>"
                                    <%= (vol != null && vol.getVilleDepart().getId().equals(ville.getId())) ? "selected" : "" %>>
                                <%= ville.getNom() %>
                            </option>
                        <% } %>
                    </select>
                    <div class="invalid-feedback">
                        Veuillez sélectionner la ville de départ
                    </div>
                </div>

                <div class="col-md-6">
                    <label for="villeArriveId" class="form-label">Ville d'arrivée *</label>
                    <select name="villeArriveId" id="villeArriveId" class="form-select" required>
                        <option value="">Sélectionnez une ville</option>
                        <% for (Ville ville : villes) { %>
                            <option value="<%= ville.getId() %>"
                                    <%= (vol != null && vol.getVilleArrive().getId().equals(ville.getId())) ? "selected" : "" %>>
                                <%= ville.getNom() %>
                            </option>
                        <% } %>
                    </select>
                    <div class="invalid-feedback">
                        Veuillez sélectionner la ville d'arrivée
                    </div>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="avionId" class="form-label">Avion *</label>
                    <select name="avionId" id="avionId" class="form-select" required>
                        <option value="">Sélectionnez un avion</option>
                        <% for (Avion avion : avions) { %>
                            <option value="<%= avion.getId() %>"
                                    <%= (vol != null && vol.getAvion().getId().equals(avion.getId())) ? "selected" : "" %>>
                                <%= avion.getModele() %> (Fabriqué le: <%= sdfDisplay.format(avion.getDateFabrication()) %>)
                            </option>
                        <% } %>
                    </select>
                    <div class="invalid-feedback">
                        Veuillez sélectionner un avion
                    </div>
                </div>

                <div class="col-md-6">
                    <label for="prix" class="form-label">Prix *</label>
                    <div class="input-group has-validation">
                        <input type="number" step="0.01" class="form-control" id="prix" name="prix"
                               value="<%= (vol != null) ? vol.getPrix() : "" %>" required min="0">
                        <span class="input-group-text">Ar</span>
                        <div class="invalid-feedback">
                            Veuillez entrer un prix valide (supérieur à 0)
                        </div>
                    </div>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="dateDepart" class="form-label">Date et heure de départ *</label>
                    <input type="datetime-local" class="form-control" id="dateDepart" name="dateDepart"
                           value="<%= (vol != null) ? sdf.format(vol.getDateDepart()) : "" %>" required>
                    <div class="invalid-feedback">
                        Veuillez sélectionner la date et l'heure de départ
                    </div>
                </div>
            </div>

            <div class="mb-3">
                <button type="submit" class="btn btn-primary">
                    <%= (vol != null) ? "Modifier" : "Créer" %> le vol
                </button>
                <a href="<%= request.getContextPath() %>/admin/vols" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        (function() {
            'use strict';

            // Form validation
            var forms = document.querySelectorAll('.needs-validation');
            Array.prototype.slice.call(forms).forEach(function(form) {
                form.addEventListener('submit', function(event) {
                    if (!form.checkValidity()) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });

            // Custom validation for departure and arrival cities
            document.getElementById('villeDepartId').addEventListener('change', validateCities);
            document.getElementById('villeArriveId').addEventListener('change', validateCities);

            function validateCities() {
                const villeDepartId = document.getElementById('villeDepartId').value;
                const villeArriveId = document.getElementById('villeArriveId').value;

                if (villeDepartId && villeArriveId && villeDepartId === villeArriveId) {
                    alert('La ville de départ et d\'arrivée ne peuvent pas être identiques');
                    document.getElementById('villeArriveId').value = '';
                }
            }

            // Price validation
            document.getElementById('prix').addEventListener('input', function(e) {
                const prix = parseFloat(e.target.value);
                if (prix <= 0) {
                    e.target.setCustomValidity('Le prix doit être supérieur à 0');
                } else {
                    e.target.setCustomValidity('');
                }
            });

            // Date validation
            document.getElementById('dateDepart').addEventListener('change', function(e) {
                const dateDepart = new Date(e.target.value);
                const now = new Date();

                if (dateDepart < now) {
                    e.target.setCustomValidity('La date de départ ne peut pas être dans le passé');
                } else {
                    e.target.setCustomValidity('');
                }
            });
        })();
    </script>
</body>
</html>