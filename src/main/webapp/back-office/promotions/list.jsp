<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Gestion des Promotions - Back Office</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
            </head>

            <body>
                <div class="container mt-4">
                    <h2>Gestion des Promotions</h2>

                    <!-- Filtre par vol -->
                    <div class="card mb-4">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/admin/promotions" method="get"
                                class="row g-3">
                                <div class="col-md-6">
                                    <label for="volId" class="form-label">Filtrer par vol</label>
                                    <select name="volId" class="form-select" onchange="this.form.submit()">
                                        <option value="">Tous les vols</option>
                                        <c:forEach items="${vols}" var="vol">
                                            <option value="${vol.id}" ${vol.id eq selectedVol.id ? 'selected' : '' }>
                                                ${vol.villeDepart.nom} → ${vol.villeArrive.nom}
                                                (
                                                <fmt:formatDate value="${vol.dateDepart}" pattern="dd/MM/yyyy HH:mm" />)
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-12">
                                    <a href="${pageContext.request.contextPath}/admin/promotions/create${selectedVol != null ? '?volId='.concat(selectedVol.id) : ''}"
                                        class="btn btn-success">
                                        Nouvelle Promotion
                                    </a>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- Liste des promotions -->
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Vol</th>
                                    <th>Type de Siège</th>
                                    <th>Nombre de sièges</th>
                                    <th>Réduction (%)</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${promotions}" var="promotion">
                                    <tr>
                                        <td>${promotion.vol.id}</td>
                                        <td>
                                            ${promotion.vol.villeDepart.nom} → ${promotion.vol.villeArrive.nom}
                                            <br>
                                            <small class="text-muted">
                                                <fmt:formatDate value="${promotion.vol.dateDepart}"
                                                    pattern="dd/MM/yyyy HH:mm" />
                                            </small>
                                        </td>
                                        <td>${promotion.typeSiege.designation}</td>
                                        <td>${promotion.nbSiege}</td>
                                        <td>${promotion.pourcentageReduction}%</td>
                                        <td>
                                            <form action="${pageContext.request.contextPath}/admin/promotions/delete"
                                                method="post" style="display: inline;">
                                                <input type="hidden" name="id" value="${promotion.id}">
                                                <input type="hidden" name="volId" value="${promotion.vol.id}">
                                                <button type="submit" class="btn btn-sm btn-danger"
                                                    onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette promotion ?')">
                                                    Supprimer
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>