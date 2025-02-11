<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Recherche de Vols</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>

        <body>
            <div class="container mt-5">
                <h2>Rechercher un Vol</h2>
                <form action="${pageContext.request.contextPath}/vols/search" method="post" class="mt-4">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="villeDepart" class="form-label">Ville de départ</label>
                            <select name="villeDepart" id="villeDepart" class="form-select">
                                <option value="">Sélectionnez une ville</option>
                                <c:forEach items="${villes}" var="ville">
                                    <option value="${ville.id}" ${ville.id==selectedVilleDepartId ? 'selected' : '' }>
                                        ${ville.nom}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="villeArrive" class="form-label">Ville d'arrivée</label>
                            <select name="villeArrive" id="villeArrive" class="form-select">
                                <option value="">Sélectionnez une ville</option>
                                <c:forEach items="${villes}" var="ville">
                                    <option value="${ville.id}" ${ville.id==selectedVilleArriveId ? 'selected' : '' }>
                                        ${ville.nom}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="dateDepart" class="form-label">Date de départ</label>
                            <input type="date" name="dateDepart" id="dateDepart" class="form-control"
                                value="${dateDepart}">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="maxPrice" class="form-label">Prix maximum</label>
                            <input type="number" name="maxPrice" id="maxPrice" class="form-control" value="${maxPrice}">
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Rechercher</button>
                </form>
            </div>
        </body>

        </html>