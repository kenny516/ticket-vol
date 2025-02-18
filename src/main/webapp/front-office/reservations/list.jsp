<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mg.model.Reservation" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<!DOCTYPE html>
<html lang="fr">

<head>
    <title>Mes Réservations</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container mt-5">
    <h2>Mes Réservations</h2>

    <div class="mt-4">
        <a href="<%= request.getContextPath() %>/vols/search" class="btn btn-primary mb-3">
            Rechercher un vol
        </a>
        <%
            String error =  request.getParameter("error");
            if (error != null && !error.isEmpty()) {
        %>
        <div class="alert alert-danger">
            <%= error %>
        </div>
        <% } %>

        <%
            List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");

            if (reservations == null || reservations.isEmpty()) {
        %>
        <div class="alert alert-info">
            Vous n'avez aucune réservation en cours.
        </div>
        <% } else { %>
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Vol</th>
                    <th>Date</th>
                    <th>Type de siège</th>
                    <th>Nombre de places</th>
                    <th>Prix total</th>
                    <th>Statut</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <%
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    for (Reservation reservation : reservations) {
                        String villeDepart = (reservation.getPlaceVol().getVol() != null && reservation.getPlaceVol().getVol().getVilleDepart() != null)
                                ? reservation.getPlaceVol().getVol().getVilleDepart().getNom()
                                : "N/A";

                        String villeArrive = (reservation.getPlaceVol().getVol() != null && reservation.getPlaceVol().getVol().getVilleArrive() != null)
                                ? reservation.getPlaceVol().getVol().getVilleArrive().getNom()
                                : "N/A";

                        String dateDepart = (reservation.getPlaceVol().getVol() != null && reservation.getPlaceVol().getVol().getDateDepart() != null)
                                ? dateFormat.format(reservation.getPlaceVol().getVol().getDateDepart())
                                : "N/A";

                        String typeSiege = (reservation.getPlaceVol().getTypeSiege() != null)
                                ? reservation.getPlaceVol().getTypeSiege().getDesignation()
                                : "Non spécifié";

                        int nombrePlaces = (reservation.getNombrePlaces() != null)
                                ? reservation.getNombrePlaces()
                                : 0;

                        double prixTotal = (reservation.getPrix() != null)
                                ? reservation.getPrix()
                                : 0.0;

                        boolean estValide = (reservation.getValider() != null) && reservation.getValider();
                %>
                <tr>
                    <td><%= villeDepart %> → <%= villeArrive %></td>
                    <td><%= dateDepart %></td>
                    <td><%= typeSiege %></td>
                    <td><%= nombrePlaces %></td>
                    <td><%= prixTotal %> Ar</td>
                    <td>
                        <% if (estValide) { %>
                        <span class="badge bg-success">Confirmée</span>
                        <% } else { %>
                        <span class="badge bg-warning">Annule</span>
                        <% } %>
                    </td>
                    <td>
                        <a href="<%= request.getContextPath() %>/annuler-reservation?idReservation=<%=reservation.getId()%>" class="btn btn-danger mb-3">
                            Annuler reservation
                        </a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
        <% } %>
    </div>
</div>
</body>

</html>
