<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

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
                            <form action="${pageContext.request.contextPath}/admin/vols/search" method="get"
                                class="row g-3">
                                <div class="col-md-3">
                                    <label for="villeDepartId" class="form-label">Ville de départ</label>
                                    <select name="villeDepartId" class="form-select">
                                        <option value="">Toutes</option>
                                        <c:forEach items="${villes}" var="ville">
                                            <option value="${ville.id}" ${ville.id eq villeDepartId ? 'selected' : '' }>
                                                ${ville.nom}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label for="villeArriveId" class="form-label">Ville d'arrivée</label>
                                    <select name="villeArriveId" class="form-select">
                                        <option value="">Toutes</option>
                                        <c:forEach items="${villes}" var="ville">
                                            <option value="${ville.id}" ${ville.id eq villeArriveId ? 'selected' : '' }>
                                                ${ville.nom}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label for="dateDebut" class="form-label">Date début</label>
                                    <input type="datetime-local" class="form-control" name="dateDebut"
                                        value="${dateDebut}">
                                </div>
                                <div class="col-md-3">
                                    <label for="dateFin" class="form-label">Date fin</label>
                                    <input type="datetime-local" class="form-control" name="dateFin" value="${dateFin}">
                                </div>
                                <div class="col-md-2">
                                    <label for="prixMin" class="form-label">Prix min</label>
                                    <input type="number" step="0.01" class="form-control" name="prixMin"
                                        value="${prixMin}">
                                </div>
                                <div class="col-md-2">
                                    <label for="prixMax" class="form-label">Prix max</label>
                                    <input type="number" step="0.01" class="form-control" name="prixMax"
                                        value="${prixMax}">
                                </div>
                                <div class="col-12">
                                    <button type="submit" class="btn btn-primary">Rechercher</button>
                                    <a href="${pageContext.request.contextPath}/admin/vols/create"
                                        class="btn btn-success">Nouveau Vol</a>
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
                                <c:forEach items="${vols}" var="vol">
                                    <tr>
                                        <td>${vol.id}</td>
                                        <td>${vol.villeDepart.nom}</td>
                                        <td>${vol.villeArrive.nom}</td>
                                        <td>
                                            <fmt:formatDate value="${vol.dateDepart}" pattern="dd/MM/yyyy HH:mm" />
                                        </td>
                                        <td>${vol.avion.modele}</td>
                                        <td>
                                            <fmt:formatNumber value="${vol.prix}" type="currency" currencySymbol="Ar" />
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/vols/edit?id=${vol.id}"
                                                class="btn btn-sm btn-warning">Modifier</a>
                                            <a href="${pageContext.request.contextPath}/admin/promotions?volId=${vol.id}"
                                                class="btn btn-sm btn-info">Promotions</a>
                                            <form action="${pageContext.request.contextPath}/admin/vols/delete"
                                                method="post" style="display: inline;">
                                                <input type="hidden" name="id" value="${vol.id}">
                                                <button type="submit" class="btn btn-sm btn-danger"
                                                    onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce vol ?')">
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