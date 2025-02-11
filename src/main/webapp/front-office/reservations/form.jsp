<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <title>Réserver un vol</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
            </head>

            <body>
                <div class="container mt-5">
                    <h2>Réservation de vol</h2>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">
                            ${error}
                        </div>
                    </c:if>

                    <c:if test="${not empty vol}">
                        <div class="card mb-4">
                            <div class="card-body">
                                <h5 class="card-title">Détails du vol</h5>
                                <p class="card-text">
                                    <strong>De:</strong> ${vol.villeDepart.nom}<br>
                                    <strong>À:</strong> ${vol.villeArrive.nom}<br>
                                    <strong>Date:</strong>
                                    <fmt:formatDate value="${vol.dateDepart}" pattern="dd/MM/yyyy HH:mm" /><br>
                                    <strong>Prix:</strong>
                                    <fmt:formatNumber value="${vol.prix}" type="currency" currencySymbol="Ar" />
                                </p>
                            </div>
                        </div>

                        <form action="${pageContext.request.contextPath}/vols/${vol.id}/reserver" method="post"
                            class="mt-4">
                            <div class="mb-3">
                                <label for="typeSiegeId" class="form-label">Type de siège</label>
                                <select name="typeSiegeId" id="typeSiegeId" class="form-select" required>
                                    <option value="">Sélectionnez un type de siège</option>
                                    <c:forEach items="${typeSieges}" var="typeSiege">
                                        <option value="${typeSiege.id}">
                                            ${typeSiege.nom} -
                                            <fmt:formatNumber value="${typeSiege.prix}" type="currency"
                                                currencySymbol="Ar" />
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="nombrePlaces" class="form-label">Nombre de places</label>
                                <input type="number" name="nombrePlaces" id="nombrePlaces" class="form-control" min="1"
                                    max="5" value="1" required>
                            </div>

                            <button type="submit" class="btn btn-primary">Confirmer la réservation</button>
                            <a href="${pageContext.request.contextPath}/vols/search"
                                class="btn btn-secondary">Retour</a>
                        </form>
                    </c:if>
                </div>
            </body>

            </html>