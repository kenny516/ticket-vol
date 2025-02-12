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
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");

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

        <% if (success != null && !success.isEmpty()) { %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <%= success %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        <% } %>

        <% if (error != null && !error.isEmpty()) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <%= error %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        <% } %>

        <!-- Formulaire de recherche -->
        <div class="card mb-4">
            <div class="card-body">
                <h5 class="card-title">Recherche avancée</h5>
                <form action="<%= request.getContextPath() %>/admin/vols" method="get" class="row g-3" id="searchForm">
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

                    <div class="col-md-3">
                        <label for="prixMin" class="form-label">Prix min</label>
                        <div class="input-group">
                            <input type="number" id="prixMin" step="0.01" class="form-control" name="prixMin"
                                   value="<%= prixMin != null ? prixMin : "" %>">
                            <span class="input-group-text">Ar</span>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <label for="prixMax" class="form-label">Prix max</label>
                        <div class="input-group">
                            <input type="number" id="prixMax" step="0.01" class="form-control" name="prixMax"
                                   value="<%= prixMax != null ? prixMax : "" %>">
                            <span class="input-group-text">Ar</span>
                        </div>
                    </div>

                    <div class="col-12">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-search"></i> Rechercher
                        </button>
                        <button type="button" class="btn btn-secondary" onclick="resetForm()">
                            <i class="bi bi-x-circle"></i> Réinitialiser
                        </button>
                        <a href="<%= request.getContextPath() %>/admin/vols/create" class="btn btn-success">
                            <i class="bi bi-plus-circle"></i> Nouveau Vol
                        </a>
                    </div>
                </form>
            </div>
        </div>

        <!-- Liste des vols -->
        <div class="card">
            <div class="card-body">
                <% if (vols == null || vols.isEmpty()) { %>
                    <div class="alert alert-info">
                        Aucun vol ne correspond aux critères de recherche.
                    </div>
                <% } else { %>
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
                                            <div class="btn-group">
                                                <a href="<%= request.getContextPath() %>/admin/vols/edit?id=<%= vol.getId() %>"
                                                   class="btn btn-sm btn-warning">
                                                    <i class="bi bi-pencil"></i> Modifier
                                                </a>
                                                <a href="<%= request.getContextPath() %>/admin/promotions?volId=<%= vol.getId() %>"
                                                   class="btn btn-sm btn-info">
                                                    <i class="bi bi-tag"></i> Promotions
                                                </a>
                                                <form action="<%= request.getContextPath() %>/admin/vols/delete"
                                                      method="post" style="display: inline;"
                                                      onsubmit="return confirmDelete()">
                                                    <input type="hidden" name="id" value="<%= vol.getId() %>">
                                                    <button type="submit" class="btn btn-sm btn-danger">
                                                        <i class="bi bi-trash"></i> Supprimer
                                                    </button>
                                                </form>
                                            </div>
                                        </td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                <% } %>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function confirmDelete() {
            return confirm('Êtes-vous sûr de vouloir supprimer ce vol ?');
        }

        function resetForm() {
            document.getElementById('villeDepartId').value = '';
            document.getElementById('villeArriveId').value = '';
            document.getElementById('dateDebut').value = '';
            document.getElementById('dateFin').value = '';
            document.getElementById('prixMin').value = '';
            document.getElementById('prixMax').value = '';
            document.getElementById('searchForm').submit();
        }

        // Validation des dates
        document.getElementById('dateDebut').addEventListener('change', validateDates);
        document.getElementById('dateFin').addEventListener('change', validateDates);

        function validateDates() {
            const dateDebut = document.getElementById('dateDebut').value;
            const dateFin = document.getElementById('dateFin').value;

            if (dateDebut && dateFin && dateDebut > dateFin) {
                alert('La date de début doit être antérieure à la date de fin');
                document.getElementById('dateFin').value = '';
            }
        }

        // Validation des prix
        document.getElementById('prixMin').addEventListener('change', validatePrices);
        document.getElementById('prixMax').addEventListener('change', validatePrices);

        function validatePrices() {
            const prixMin = parseFloat(document.getElementById('prixMin').value);
            const prixMax = parseFloat(document.getElementById('prixMax').value);

            if (prixMin && prixMax && prixMin > prixMax) {
                alert('Le prix minimum doit être inférieur au prix maximum');
                document.getElementById('prixMax').value = '';
            }
        }

        // Auto-hide alerts after 5 seconds
        setTimeout(function() {
            document.querySelectorAll('.alert').forEach(function(alert) {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            });
        }, 5000);
    </script>
</body>
</html>