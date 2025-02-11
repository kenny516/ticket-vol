<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Nouvelle Promotion - Back Office</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
            </head>

            <body>
                <div class="container mt-4">
                    <h2>Nouvelle Promotion</h2>

                    <form action="${pageContext.request.contextPath}/admin/promotions/create" method="post"
                        class="needs-validation" novalidate>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="volId" class="form-label">Vol</label>
                                <select name="volId" class="form-select" required ${selectedVol !=null ? 'disabled' : ''
                                    }>
                                    <option value="">Sélectionnez un vol</option>
                                    <c:forEach items="${vols}" var="vol">
                                        <option value="${vol.id}" ${selectedVol !=null && selectedVol.id eq vol.id
                                            ? 'selected' : '' }>
                                            ${vol.villeDepart.nom} → ${vol.villeArrive.nom}
                                            (
                                            <fmt:formatDate value="${vol.dateDepart}" pattern="dd/MM/yyyy HH:mm" />)
                                        </option>
                                    </c:forEach>
                                </select>
                                <c:if test="${selectedVol != null}">
                                    <input type="hidden" name="volId" value="${selectedVol.id}">
                                </c:if>
                            </div>

                            <div class="col-md-6">
                                <label for="typeSiegeId" class="form-label">Type de Siège</label>
                                <select name="typeSiegeId" class="form-select" required>
                                    <option value="">Sélectionnez un type de siège</option>
                                    <c:forEach items="${typeSieges}" var="typeSiege">
                                        <option value="${typeSiege.id}">${typeSiege.designation}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="nbSiege" class="form-label">Nombre de sièges en promotion</label>
                                <input type="number" class="form-control" name="nbSiege" min="1" required>
                            </div>

                            <div class="col-md-6">
                                <label for="reduction" class="form-label">Pourcentage de réduction</label>
                                <div class="input-group">
                                    <input type="number" class="form-control" name="reduction" min="0" max="100"
                                        step="0.01" required>
                                    <span class="input-group-text">%</span>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <button type="submit" class="btn btn-primary">Enregistrer</button>
                            <a href="${pageContext.request.contextPath}/admin/promotions${selectedVol != null ? '?volId='.concat(selectedVol.id) : ''}"
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