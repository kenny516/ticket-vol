<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>${avion != null ? 'Modifier' : 'Créer'} un Avion - Back Office</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
            </head>

            <body>
                <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
                    <div class="container">
                        <a class="navbar-brand" href="${pageContext.request.contextPath}/admin">Administration</a>
                        <div class="navbar-nav">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/vols">Vols</a>
                            <a class="nav-link active" href="${pageContext.request.contextPath}/admin/avions">Avions</a>
                            <a class="nav-link"
                                href="${pageContext.request.contextPath}/admin/promotions">Promotions</a>
                            <a class="nav-link"
                                href="${pageContext.request.contextPath}/admin/parametres">Paramètres</a>
                        </div>
                    </div>
                </nav>

                <div class="container mt-4">
                    <h2>${avion != null ? 'Modifier' : 'Créer'} un Avion</h2>

                    <form action="${pageContext.request.contextPath}/admin/avions/${avion != null ? 'edit' : 'create'}"
                        method="post" class="needs-validation" novalidate>

                        <c:if test="${avion != null}">
                            <input type="hidden" name="id" value="${avion.id}">
                        </c:if>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="modele" class="form-label">Modèle</label>
                                <input type="text" class="form-control" name="modele"
                                    value="${avion != null ? avion.modele : ''}" required>
                            </div>

                            <div class="col-md-6">
                                <label for="dateFabrication" class="form-label">Date de fabrication</label>
                                <input type="date" class="form-control" name="dateFabrication"
                                    value="<fmt:formatDate value=" ${avion.dateFabrication}" pattern="yyyy-MM-dd" />"
                                required>
                            </div>
                        </div>

                        <div class="card mb-3">
                            <div class="card-header">
                                Configuration des sièges
                            </div>
                            <div class="card-body">
                                <div id="siegesContainer">
                                    <c:forEach items="${typeSieges}" var="typeSiege" varStatus="status">
                                        <div class="row mb-3 siege-row">
                                            <div class="col-md-6">
                                                <label class="form-label">Type de siège</label>
                                                <select name="typeSieges" class="form-select">
                                                    <option value="">Sélectionnez un type</option>
                                                    <c:forEach items="${typeSieges}" var="ts">
                                                        <option value="${ts.id}" ${avion !=null &&
                                                            places[status.index].typeSiege.id eq ts.id ? 'selected' : ''
                                                            }>
                                                            ${ts.designation}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-md-4">
                                                <label class="form-label">Nombre de places</label>
                                                <input type="number" class="form-control" name="nombrePlaces" min="0"
                                                    value="${avion != null ? places[status.index].nombre : '0'}">
                                            </div>
                                            <div class="col-md-2 d-flex align-items-end">
                                                <button type="button" class="btn btn-danger btn-sm remove-siege"
                                                    onclick="removeSiegeRow(this)">
                                                    Supprimer
                                                </button>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                                <button type="button" class="btn btn-secondary" onclick="addSiegeRow()">
                                    Ajouter un type de siège
                                </button>
                            </div>
                        </div>

                        <div class="mb-3">
                            <button type="submit" class="btn btn-primary">Enregistrer</button>
                            <a href="${pageContext.request.contextPath}/admin/avions"
                                class="btn btn-secondary">Annuler</a>
                        </div>
                    </form>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
                <script>
                    function addSiegeRow() {
                        const container = document.getElementById('siegesContainer');
                        const template = document.querySelector('.siege-row');
                        const newRow = template.cloneNode(true);

                        // Réinitialiser les valeurs
                        newRow.querySelector('select').value = '';
                        newRow.querySelector('input[type="number"]').value = '0';

                        container.appendChild(newRow);
                    }

                    function removeSiegeRow(button) {
                        const row = button.closest('.siege-row');
                        if (document.querySelectorAll('.siege-row').length > 1) {
                            row.remove();
                        } else {
                            alert('Vous devez conserver au moins un type de siège');
                        }
                    }

                    // Validation des formulaires Bootstrap
                    (function () {
                        'use strict'
                        var forms = document.querySelectorAll('.needs-validation')
                        Array.prototype.slice.call(forms)
                            .forEach(function (form) {
                                form.addEventListener('submit', function (event) {
                                    if (!form.checkValidity()) {
                                        event.preventDefault()
                                        event.stopPropagation()
                                    }
                                    form.classList.add('was-validated')
                                }, false)
                            })
                    })()
                </script>
            </body>

            </html>