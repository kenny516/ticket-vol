<%@ page import="java.util.List" %>
<%@ page import="com.mg.model.Reservation" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <title>Mes Réservations</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
            </head>

            <body>
                <div class="container mt-5">
                    <h2>Mes Réservations</h2>

                    <div class="mt-4">
                        <a href="${pageContext.request.contextPath}/vols/search" class="btn btn-primary mb-3">
                            Rechercher un vol
                        </a>

                        <c:if test="${empty reservations}">
                            <div class="alert alert-info">
                                Vous n'avez aucune réservation en cours.
                            </div>
                        </c:if>

                        <c:if test="${not empty reservations}">
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
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${reservations}" var="reservation">
                                            <tr>
                                                <td>${reservation.vol.villeDepart.nom} →
                                                    ${reservation.vol.villeArrive.nom}</td>
                                                <td>
                                                    <fmt:formatDate value="${reservation.vol.dateDepart}"
                                                        pattern="dd/MM/yyyy HH:mm" />
                                                </td>
                                                <td>${reservation.typeSiege.nom}</td>
                                                <td>${reservation.nombrePlaces}</td>
                                                <td>
                                                    <fmt:formatNumber
                                                        value="${reservation.nombrePlaces * reservation.vol.prix}"
                                                        type="currency" currencySymbol="Ar" />
                                                </td>
                                                <td>
                                                    <span class="badge bg-success">Confirmée</span>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:if>
                    </div>
                </div>
            </body>

            </html>