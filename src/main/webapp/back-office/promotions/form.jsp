<%@ page import="com.mg.model.Vol" %>
<%@ page import="com.mg.model.TypeSiege" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<% String error = (String) request.getAttribute("error");
    Integer volId = (Integer)request.getAttribute("volId");
    Integer typeSiegeId = (Integer)request.getAttribute("typeSiegeId");
    Integer nbSiege = (Integer) request.getAttribute("nbSiege");
    Double reduction = (Double) request.getAttribute("reduction");
    List<Vol> vols = (List<Vol>)request.getAttribute("vols");
    List<TypeSiege> typeSieges = (List<TypeSiege>) request.getAttribute("typeSieges");
    Vol selectedVol = (Vol) request.getAttribute("selectedVol");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouvelle Promotion - Back Office</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/back-office/css/style.css" rel="stylesheet">
</head>

<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark fixed-top">
        <div class="container">
            <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/admin">
                <i class="bi bi-airplane-engines me-2"></i>Administration
            </a>
        </div>
    </nav>

    <div class="container mt-5 pt-4">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2><i class="bi bi-tag me-2"></i>Nouvelle Promotion</h2>
                </div>

                <% if (error != null) { %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-triangle me-2"></i>
                        <%= error %>
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                <% } %>

                <div class="card">
                    <div class="card-header bg-light">
                        <h5 class="card-title mb-0">Informations de la promotion</h5>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/admin/promotions/create"
                              method="post" class="needs-validation" novalidate>

                            <div class="row g-4">
                                <div class="col-md-12">
                                    <label for="volId" class="form-label">
                                        <i class="bi bi-airplane me-1"></i>Vol
                                        <span class="text-danger">*</span>
                                    </label>
                                    <select name="volId" id="volId" class="form-select"
                                            required <%=volId != null ? "disabled" : "" %>>
                                        <option value="">Sélectionnez un vol</option>
                                        <% for (Vol vol : vols) { %>
                                            <option value="<%= vol.getId() %>"
                                                    <%=(volId!= null && volId.equals(vol.getId()))? "selected" : "" %>>
                                                <%= vol.getVilleDepart().getNom() %> →
                                                <%= vol.getVilleArrive().getNom() %>
                                                (<%= sdf.format(vol.getDateDepart())%>)
                                            </option>
                                        <% } %>
                                    </select>
                                    <div class="invalid-feedback">
                                        Veuillez sélectionner un vol
                                    </div>
                                    <% if (volId != null) { %>
                                        <input type="hidden" name="volId" value="<%= volId %>">
                                    <% } %>
                                </div>

                                <div class="col-md-6">
                                    <label for="typeSiegeId" class="form-label">
                                        <i class="bi bi-chair me-1"></i>Type de Siège
                                        <span class="text-danger">*</span>
                                    </label>
                                    <select name="typeSiegeId" id="typeSiegeId" class="form-select" required>
                                        <option value="">Sélectionnez un type de siège</option>
                                        <% for (TypeSiege typeSiege : typeSieges) { %>
                                            <option value="<%= typeSiege.getId() %>"
                                                    <%=(typeSiegeId != null && typeSiegeId.equals(typeSiege.getId())) ? "selected" : "" %>>
                                                <%= typeSiege.getDesignation() %>
                                            </option>
                                        <% } %>
                                    </select>
                                    <div class="invalid-feedback">
                                        Veuillez sélectionner un type de siège
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <label for="nbSiege" class="form-label">
                                        <i class="bi bi-people me-1"></i>Nombre de sièges en promotion
                                        <span class="text-danger">*</span>
                                    </label>
                                    <input type="number" class="form-control" id="nbSiege"
                                           name="nbSiege" min="1"
                                           value="<%= nbSiege != null ? nbSiege : "" %>" required>
                                    <div class="form-text">
                                        Nombre de sièges disponibles pour cette promotion
                                    </div>
                                    <div class="invalid-feedback">
                                        Veuillez entrer un nombre de sièges valide (minimum 1)
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <label for="reduction" class="form-label">
                                        <i class="bi bi-percent me-1"></i>Pourcentage de réduction
                                        <span class="text-danger">*</span>
                                    </label>
                                    <div class="input-group">
                                        <input type="number" class="form-control percentage-input"
                                               id="reduction" name="reduction" min="0"
                                               max="100" step="0.01"
                                               value="<%= reduction != null ? reduction : "" %>"
                                               required>
                                        <span class="input-group-text">%</span>
                                    </div>
                                    <div class="form-text">
                                        Réduction appliquée sur le prix du billet
                                    </div>
                                    <div class="invalid-feedback">
                                        Veuillez entrer un pourcentage valide entre 0 et 100
                                    </div>
                                </div>
                            </div>

                            <div class="alert alert-info mt-4">
                                <i class="bi bi-info-circle-fill me-2"></i>
                                <strong>Note:</strong> La promotion sera appliquée uniquement aux nouveaux billets
                                réservés après sa création.
                            </div>

                            <div class="d-flex gap-2 mt-4">
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-check-circle me-1"></i>Enregistrer
                                </button>
                                <a href="${pageContext.request.contextPath}/admin/promotions<%= selectedVol != null ? "?volId=" + selectedVol.getId() : "" %>"
                                   class="btn btn-secondary">
                                    <i class="bi bi-x-circle me-1"></i>Annuler
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/back-office/js/script.js"></script>
</body>
</html>