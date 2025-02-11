<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Gestion des Avions - Back Office</title>
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
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h2>Gestion des Avions</h2>
                        <a href="${pageContext.request.contextPath}/admin/avions/create" class="btn btn-success">
                            Nouvel Avion
                        </a>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Modèle</th>
                                    <th>Date de fabrication</th>
                                    <th>Configuration des sièges</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${avions}" var="avion">
                                    <tr>
                                        <td>${avion.id}</td>
                                        <td>${avion.modele}</td>
                                        <td>
                                            <fmt:formatDate value="${avion.dateFabrication}" pattern="dd/MM/yyyy" />
                                        </td>
                                        <td>
                                            <c:forEach items="${avion.places}" var="place">
                                                ${place.typeSiege.designation}: ${place.nombre}<br>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/avions/edit?id=${avion.id}"
                                                class="btn btn-sm btn-warning">Modifier</a>
                                            <form action="${pageContext.request.contextPath}/admin/avions/delete"
                                                method="post" style="display: inline;">
                                                <input type="hidden" name="id" value="${avion.id}">
                                                <button type="submit" class="btn btn-sm btn-danger"
                                                    onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet avion ?')">
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