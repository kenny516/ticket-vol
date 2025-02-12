<%@ page import="com.mg.model.Vol" %>
<%@ page import="com.mg.model.Promotion" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% List<Vol> vols = (List<Vol>) request.getAttribute("vols");
    List<Promotion> promotions = (List<Promotion>) request.getAttribute("promotions");
    Vol selectedVol = (Vol) request.getAttribute("selectedVol");
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Gestion des Promotions - Back Office</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
</head>

<body>
<div class="container mt-4">
    <h2>Gestion des Promotions</h2>

    <% if (success != null && !success.isEmpty()) { %>
    <div class="alert alert-success alert-dismissible fade show"
         role="alert">
        <%= success %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"
                aria-label="Close"></button>
    </div>
    <% } %>

    <% if (error != null && !error.isEmpty()) { %>
    <div class="alert alert-danger alert-dismissible fade show"
         role="alert">
        <%= error %>
        <button type="button" class="btn-close"
                data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <% } %>

    <!-- Filtre par vol -->
    <div class="card mb-4">
        <div class="card-body">
            <form
                    action="<%= request.getContextPath() %>/admin/promotions"
                    method="get" class="row g-3">
                <div class="col-md-6">
                    <label for="volId"
                           class="form-label">Filtrer par
                        vol</label>
                    <select name="volId" id="volId"
                            class="form-select"
                            onchange="this.form.submit()">
                        <option value="">Tous les vols</option>
                        <% for (Vol vol : vols) { %>
                        <option value="<%= vol.getId() %>"
                                <%=(selectedVol != null &&
                                        vol.getId().equals(selectedVol.getId()))
                                        ? "selected" : "" %>>
                            <%= vol.getVilleDepart().getNom()
                            %> → <%=
                        vol.getVilleArrive().getNom()
                        %>
                            (<%= sdf.format(vol.getDateDepart())
                        %>)
                        </option>
                        <% } %>
                    </select>
                </div>
                <div class="col-12">
                    <a href="<%= request.getContextPath() %>/admin/promotions/create<%= selectedVol != null ? " ?volId=" + selectedVol.getId() : "" %>"
                       class="btn btn-success">
                        <i class="bi bi-plus-circle"></i>
                        Nouvelle Promotion
                    </a>
                </div>
            </form>
        </div>
    </div>

    <!-- Liste des promotions -->
    <div class="card">
        <div class="card-body">
            <% if (promotions == null || promotions.isEmpty()) {
            %>
            <div class="alert alert-info">
                Aucune promotion n'a été trouvée.
                <% if (selectedVol != null) { %>
                <br>
                <small>Filtré pour le vol: <%=
                selectedVol.getVilleDepart().getNom()
                %> → <%=
                selectedVol.getVilleArrive().getNom()
                %>
                </small>
                <% } %>
            </div>
            <% } else { %>
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
                    <% for (Promotion promotion :
                            promotions) { %>
                    <tr>
                        <td>
                            <%= promotion.getId()
                            %>
                        </td>
                        <td>
                            <%= promotion.getVol().getVilleDepart().getNom()
                            %> → <%=
                        promotion.getVol().getVilleArrive().getNom()
                        %>
                            <br>
                            <small
                                    class="text-muted">
                                <%= sdf.format(promotion.getVol().getDateDepart())
                                %>
                            </small>
                        </td>
                        <td>
                            <%= promotion.getTypeSiege().getDesignation()
                            %>
                        </td>
                        <td>
                            <%= promotion.getNbSiege()
                            %>
                        </td>
                        <td>
                            <%= promotion.getPourcentageReduction()
                            %>%
                        </td>
                        <td>
                            <form
                                    action="<%= request.getContextPath() %>/admin/promotions/delete"
                                    method="post"
                                    style="display: inline;"
                                    onsubmit="return confirmDelete('<%= promotion.getVol().getVilleDepart().getNom() %> → <%= promotion.getVol().getVilleArrive().getNom() %>')">
                                <input
                                        type="hidden"
                                        name="id"
                                        value="<%= promotion.getId() %>">
                                <input
                                        type="hidden"
                                        name="volId"
                                        value="<%= promotion.getVol().getId() %>">
                                <button
                                        type="submit"
                                        class="btn btn-sm btn-danger">
                                    <i
                                            class="bi bi-trash"></i>
                                    Supprimer
                                </button>
                            </form>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
            <% } %>
        </div>
    </div>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function confirmDelete(volInfo) {
        return confirm('Êtes-vous sûr de vouloir supprimer cette promotion pour le vol ' + volInfo + ' ?');
    }

    // Auto-hide alerts after 5 seconds
    setTimeout(function () {
        document.querySelectorAll('.alert').forEach(function (alert) {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        });
    }, 5000);
</script>
</body>

</html>