<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mg.model.Vol" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Vol> vols = (List<Vol>) request.getAttribute("vols");
%>

<!DOCTYPE html>
<html>

<head>
    <title>Résultats de la recherche</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container mt-5">
    <h2>Vols disponibles</h2>

    <div class="mt-4">
        <a href="${pageContext.request.contextPath}/vols/search" class="btn btn-secondary mb-3">
            Nouvelle recherche
        </a>

        <c:choose>
            <c:when test="${empty vols}">
                <div class="alert alert-info">
                    Aucun vol ne correspond à vos critères de recherche.
                </div>
            </c:when>

            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Départ</th>
                            <th>Arrivée</th>
                            <th>Date</th>
                            <th>Prix</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${vols}" var="vol">
                            <tr>
                                <td>${vol.villeDepart.nom}</td>
                                <td>${vol.villeArrive.nom}</td>
                                <td>
                                    <fmt:formatDate value="${vol.dateDepart}" pattern="dd/MM/yyyy HH:mm" />
                                </td>
                                <td>
                                    <fmt:formatNumber value="${vol.prix}" type="currency" currencyCode="MGA" />
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/reserver?volId=${vol.id}"
                                       class="btn btn-primary btn-sm">
                                        Réserver
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>

</html>
