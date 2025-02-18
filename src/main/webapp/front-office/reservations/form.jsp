<%@ page import="com.mg.model.Vol" %>
<%@ page import="com.mg.model.TypeSiege" %>
<%@ page import="com.mg.model.PlaceVol" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="fr">

<head>
    <title>Réserver un vol</title>
    <%@ include file="../shared/header.jsp" %>
</head>

<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card mb-4">
                <div class="card-body">
                    <h2 class="card-title">Réservation de vol</h2>

                    <% String error = (String) request.getAttribute("error");
                        if (error != null &&
                                !error.isEmpty()) { %>
                    <div class="alert alert-danger">
                        <%= error %>
                    </div>
                    <% } %>

                    <% Vol vol = (Vol) request.getAttribute("vol");
                        if (vol != null) { %>
                    <div class="card mb-4 bg-light">
                        <div class="card-body">
                            <h5 class="card-title">Détails du vol</h5>
                            <div class="row">
                                <div class="col-md-6">
                                    <p><strong>De:</strong>
                                        <%= vol.getVilleDepart().getNom() %>
                                    </p>
                                    <p><strong>À:</strong>
                                        <%= vol.getVilleArrive().getNom() %>
                                    </p>
                                </div>
                                <div class="col-md-6">
                                    <p><strong>Date:</strong>
                                        <%= new java.text.SimpleDateFormat("dd/MM/yyyyHH:mm").format(vol.getDateDepart()) %>
                                    </p>
                                    <% for (PlaceVol placeVol : vol.getPlaceVols()) { %>
                                    <p>
                                        <strong>
                                            <%= placeVol.getTypeSiege().getDesignation()
                                            %>:
                                        </strong>
                                        <%= placeVol.getPrix() %> AR
                                    </p>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                    </div>

                    <form action="<%= request.getContextPath() %>/vols/reserver"
                          method="post" class="mt-4">
                        <input type="hidden" name="volId" value="<%= vol.getId() %>">

                        <div class="mb-3">
                            <label for="typeSiegeId" class="form-label">Type de
                                siège</label>
                            <select name="typeSiegeId" id="typeSiegeId"
                                    class="form-select" required>
                                <option value="">Sélectionnez un type de siège</option>
                                <% for (PlaceVol placeVol : vol.getPlaceVols()) { %>
                                <option
                                        value="<%= placeVol.getTypeSiege().getId() %>">
                                    <%= placeVol.getTypeSiege().getDesignation() %>
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="nombrePlaces" class="form-label">Nombre de
                                places</label>
                            <input type="number" name="nombrePlaces" id="nombrePlaces"
                                   class="form-control" min="1" value="1" required>
                        </div>

                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                            <button type="submit"
                                    class="btn btn-custom-primary">Confirmer la
                                réservation
                            </button>
                            <a href="<%= request.getContextPath() %>/vols/search"
                               class="btn btn-secondary">Retour</a>
                        </div>
                    </form>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>