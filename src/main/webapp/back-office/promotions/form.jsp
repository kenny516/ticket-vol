<%@ page import="com.mg.model.Vol" %>
<%@ page import="com.mg.model.TypeSiege" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<% String error = (String) request.getAttribute("error");
    Integer volId = (Integer)request.getAttribute("volId");
    Integer typeSiegeId = (Integer)request.getAttribute("typeSiegeId");
    Integer nbSiege = (Integer) request.getAttribute("nbSiege");
    Double reduction = (Double) request.getAttribute("reduction");
    List<Vol> vols = (List<Vol>)request.getAttribute("vols");
    List<TypeSiege> typeSieges = (List<TypeSiege>) request.getAttribute("typeSieges");
    Vol selectedVol = (Vol) request.getAttribute("selectedVol");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Nouvelle Promotion - Back Office</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
</head>

<body>
<div class="container mt-4">
    <h2>Nouvelle Promotion</h2>

    <% if (error != null) { %>
    <div class="alert alert-danger alert-dismissible fade show"
         role="alert">
        <%= error %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"
                aria-label="Close"></button>
    </div>
    <% } %>
    <form
            action="<%= request.getContextPath() %>/admin/promotions/create"
            method="post" class="needs-validation" novalidate>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="volId" class="form-label">Vol *</label>
                <select name="volId" id="volId" class="form-select"
                        required <%=volId != null ? "disabled" : "" %>>
                    <option value="">Sélectionnez un vol</option>
                    <% for (Vol vol : vols) { %>
                    <option value="<%= vol.getId() %>" <%=(volId!= null && volId.equals(vol.getId()))? "selected" : "" %>>
                        <%= vol.getVilleDepart().getNom() %> → <%=vol.getVilleArrive().getNom() %>(<%= sdf.format(vol.getDateDepart())%>)
                    </option>
                    <% } %>
                </select>
                <div class="invalid-feedback">
                    Veuillez sélectionner un vol
                </div>
                <% if (volId != null) { %>
                <input type="hidden" name="volId"value="<%= volId %>">
                <% } %>
            </div>

            <div class="col-md-6">
                <label for="typeSiegeId" class="form-label">Type de Siège *</label>
                <select name="typeSiegeId" id="typeSiegeId" class="form-select" required>
                    <option value="">
                        Sélectionnez un type de siège
                    </option>
                    <% for (TypeSiege typeSiege : typeSieges) { %>
                    <option value="<%= typeSiege.getId() %>" <%=(typeSiegeId != null && typeSiegeId.equals(typeSiege.getId())) ? "selected" : "" %>>
                            <%= typeSiege.getDesignation() %>
                    </option>
                    <% } %>
                </select>
                <div class="invalid-feedback">
                    Veuillez sélectionner un type de siège
                </div>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="nbSiege" class="form-label">Nombre de sièges
                    en promotion *</label>
                <input type="number" class="form-control" id="nbSiege" name="nbSiege" min="1" value="<%= nbSiege != null ? nbSiege : "" %>" required>
                <div class="invalid-feedback">
                    Veuillez entrer un nombre de sièges valide (minimum
                    1)
                </div>
            </div>

            <div class="col-md-6">
                <label for="reduction" class="form-label">Pourcentage de
                    réduction *</label>
                <div class="input-group has-validation">
                    <input type="number" class="form-control"
                           id="reduction" name="reduction" min="0"
                           max="100" step="0.01"
                           value="<%= reduction != null ? reduction : "" %>"
                           required>
                    <span class="input-group-text">%</span>
                    <div class="invalid-feedback">
                        Veuillez entrer un pourcentage valide entre 0 et
                        100
                    </div>
                </div>
            </div>
        </div>

        <div class="mb-3">
            <button type="submit"
                    class="btn btn-primary">Enregistrer
            </button>
            <a href="<%= request.getContextPath() %>/admin/promotions<%= selectedVol != null ? " ?volId=" + selectedVol.getId() : "" %>"
               class="btn btn-secondary">Annuler</a>
        </div>
    </form>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    (function () {
        'use strict';
        var forms = document.querySelectorAll('.needs-validation');
        Array.prototype.slice.call(forms).forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });

        // Additional validation for reduction
        document.getElementById('reduction').addEventListener('input', function (e) {
            const value = parseFloat(e.target.value);
            if (value < 0 || value > 100) {
                e.target.setCustomValidity('La réduction doit être comprise entre 0 et 100%');
            } else {
                e.target.setCustomValidity('');
            }
        });

        // Additional validation for nbSiege
        document.getElementById('nbSiege').addEventListener('input', function (e) {
            const value = parseInt(e.target.value);
            if (value < 1) {
                e.target.setCustomValidity('Le nombre de sièges doit être supérieur à 0');
            } else {
                e.target.setCustomValidity('');
            }
        });
    })();
</script>
</body>

</html>