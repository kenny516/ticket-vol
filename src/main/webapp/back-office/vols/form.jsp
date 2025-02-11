<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>${vol != null ? 'Modifier' : 'Créer'} un Vol - Back Office</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
            </head>

            <body>
                <div class="container mt-4">
                    <h2>${vol != null ? 'Modifier' : 'Créer'} un Vol</h2>

                    <form action="${pageContext.request.contextPath}/admin/vols/${vol != null ? 'edit' : 'create'}"
                        method="post" class="needs-validation" novalidate>

                        <c:if test="${vol != null}">
                            <input type="hidden" name="id" value="${vol.id}">
                        </c:if>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="villeDepartId" class="form-label">Ville de départ</label>
                                <select name="villeDepartId" class="form-select" required>
                                    <option value="">Sélectionnez une ville</option>
                                    <c:forEach items="${villes}" var="ville">
                                        <option value="${ville.id}" ${vol !=null && vol.villeDepart.id eq ville.id
                                            ? 'selected' : '' }>
                                            ${ville.nom}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="col-md-6">
                                <label for="villeArriveId" class="form-label">Ville d'arrivée</label>
                                <select name="villeArriveId" class="form-select" required>
                                    <option value="">Sélectionnez une ville</option>
                                    <c:forEach items="${villes}" var="ville">
                                        <option value="${ville.id}" ${vol !=null && vol.villeArrive.id eq ville.id
                                            ? 'selected' : '' }>
                                            ${ville.nom}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="avionId" class="form-label">Avion</label>
                                <select name="avionId" class="form-select" required>
                                    <option value="">Sélectionnez un avion</option>
                                    <c:forEach items="${avions}" var="avion">
                                        <option value="${avion.id}" ${vol !=null && vol.avion.id eq avion.id
                                            ? 'selected' : '' }>
                                            ${avion.modele} (Fabriqué le:
                                            <fmt:formatDate value="${avion.dateFabrication}" pattern="dd/MM/yyyy" />)
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="col-md-6">
                                <label for="prix" class="form-label">Prix</label>
                                <div class="input-group">
                                    <input type="number" step="0.01" class="form-control" name="prix"
                                        value="${vol != null ? vol.prix : ''}" required>
                                    <span class="input-group-text">Ar</span>
                                </div>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="dateDepart" class="form-label">Date et heure de départ</label>
                                <input type="datetime-local" class="form-control" name="dateDepart"
                                    value="<fmt:formatDate value=" ${vol.dateDepart}" pattern="yyyy-MM-dd'T'HH:mm" />"
                                required>
                            </div>
                        </div>

                        <div class="mb-3">
                            <button type="submit" class="btn btn-primary">Enregistrer</button>
                            <a href="${pageContext.request.contextPath}/admin/vols"
                                class="btn btn-secondary">Annuler</a>
                        </div>
                    </form>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
                <script>
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