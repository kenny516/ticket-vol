<%@ page import="java.util.List" %>
<%@ page import="com.mg.model.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% List<PlaceAlea> placesAlea = (List<PlaceAlea>) request.getAttribute("placesAlea");
    List<Vol> vols = (List<Vol>) request.getAttribute("vols");
    List<TypeSiege> typeSieges = (List<TypeSiege>) request.getAttribute("typeSieges");

    Integer volId = (request.getAttribute("volId") != null) ? (Integer)
            request.getAttribute("volId") : null;
    Integer typeSiegeId = (request.getAttribute("typeSiegeId") != null) ?
            (Integer) request.getAttribute("typeSiegeId") : null;
    String dateDebut = (request.getAttribute("dateDebut") != null) ? (String)
            request.getAttribute("dateDebut") : "";
    String dateFin = (request.getAttribute("dateFin") != null) ? (String)
            request.getAttribute("dateFin") : "";
    Double prixMin = (request.getAttribute("prixMin") != null) ? (Double)
            request.getAttribute("prixMin") : null;
    Double prixMax = (request.getAttribute("prixMax") != null) ? (Double)
            request.getAttribute("prixMax") : null;

    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
%>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Places Aléatoires</title>
    <jsp:include page="../shared/header.jsp"/>
</head>

<body class="bg-gray-50">
<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- En-tête de la page -->
    <div class="mb-8">
        <div class="flex justify-between items-center">
            <div>
                <h1 class="text-2xl font-bold text-gray-900">Places
                    Aléatoires</h1>
                <p class="mt-2 text-sm text-gray-600">Découvrez les
                    offres spéciales de places aléatoires</p>
            </div>
            <% Utilisateur user = (Utilisateur)
                    session.getAttribute("user");
                if (user != null) { %>
            <div class="flex items-center space-x-4">
                                                                    <span class="text-gray-600">Bienvenue, <%=
                                                                    user.getNom() %></span>
            </div>
            <% } %>
        </div>
    </div>

    <!-- Formulaire de recherche -->
    <div class="bg-white rounded-xl shadow-sm mb-6 p-6">
        <form
                action="<%= request.getContextPath() %>/places-alea/search"
                method="post">
            <div class="grid grid-cols-1 md:grid-cols-6 gap-4">
                <!-- Vol -->
                <div class="space-y-2">
                    <label for="volId"
                           class="block text-sm font-medium text-gray-700">
                        Vol
                    </label>
                    <select name="volId" id="volId"
                            class="text-black px-3 py-2 block w-full rounded-lg border border-gray-300 bg-gray-50 focus:border-blue-500 focus:ring-blue-500 text-sm">
                        <option value="">Tous les vols</option>
                        <% if (vols != null) {
                            for (Vol vol : vols) { %>
                        <option value="<%= vol.getId() %>" <%=volId
                                != null && volId.equals(vol.getId())
                                ? "selected" : "" %>>
                            <%= vol.getVilleDepart().getNom() %> →
                            <%= vol.getVilleArrive().getNom() %>
                        </option>
                        <% }
                        } %>
                    </select>
                </div>

                <!-- Type de Siège -->
                <div class="space-y-2">
                    <label for="typeSiegeId"
                           class="block text-sm font-medium text-gray-700">
                        Type de Siège
                    </label>
                    <select name="typeSiegeId" id="typeSiegeId"
                            class="text-black px-3 py-2 block w-full rounded-lg border border-gray-300 bg-gray-50 focus:border-blue-500 focus:ring-blue-500 text-sm">
                        <option value="">Tous les types</option>
                        <% if (typeSieges != null) {
                            for (TypeSiege
                                    typeSiege : typeSieges) { %>
                        <option value="<%= typeSiege.getId() %>"
                                <%=typeSiegeId != null &&
                                        typeSiegeId.equals(typeSiege.getId())
                                        ? "selected" : "" %>>
                            <%= typeSiege.getDesignation() %>
                        </option>
                        <% }
                        } %>
                    </select>
                </div>

                <!-- Date Début -->
                <div class="space-y-2">
                    <label for="dateDebut"
                           class="block text-sm font-medium text-gray-700">
                        Date début
                    </label>
                    <input type="date" name="dateDebut" id="dateDebut"
                           value="<%= dateDebut %>"
                           class="text-black px-3 py-2 block w-full rounded-lg border border-gray-300 bg-gray-50 focus:border-blue-500 focus:ring-blue-500 text-sm">
                </div>

                <!-- Date Fin -->
                <div class="space-y-2">
                    <label for="dateFin"
                           class="block text-sm font-medium text-gray-700">
                        Date fin
                    </label>
                    <input type="date" name="dateFin" id="dateFin"
                           value="<%= dateFin %>"
                           class="text-black px-3 py-2 block w-full rounded-lg border border-gray-300 bg-gray-50 focus:border-blue-500 focus:ring-blue-500 text-sm">
                </div>

                <!-- Prix Min -->
                <div class="space-y-2">
                    <label for="prixMin"
                           class="block text-sm font-medium text-gray-700">
                        Prix min (Ar)
                    </label>
                    <input type="number" name="prixMin" id="prixMin"
                           value="<%= prixMin != null ? prixMin : "" %>"
                           min="0" step="1000"
                           class="text-black px-3 py-2 block w-full rounded-lg border border-gray-300 bg-gray-50 focus:border-blue-500 focus:ring-blue-500 text-sm">
                </div>

                <!-- Prix Max -->
                <div class="space-y-2">
                    <label for="prixMax"
                           class="block text-sm font-medium text-gray-700">
                        Prix max (Ar)
                    </label>
                    <input type="number" name="prixMax" id="prixMax"
                           value="<%= prixMax != null ? prixMax : "" %>"
                           min="0" step="1000"
                           class="text-black px-3 py-2 block w-full rounded-lg border border-gray-300 bg-gray-50 focus:border-blue-500 focus:ring-blue-500 text-sm">
                </div>
            </div>

            <div class="mt-4 flex gap-3">
                <button type="submit"
                        class="inline-flex items-center px-4 py-2 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-colors duration-200">
                    <svg class="w-4 h-4 mr-2" fill="none"
                         stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round"
                              stroke-linejoin="round" stroke-width="2"
                              d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z">
                        </path>
                    </svg>
                    Rechercher
                </button>
                <a href="<%= request.getContextPath() %>/places-alea"
                   class="inline-flex items-center px-4 py-2 border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-colors duration-200">
                    Réinitialiser
                </a>
            </div>
        </form>
    </div>

    <!-- Résultats -->
    <div class="bg-white rounded-xl shadow-sm overflow-hidden">
        <div class="px-6 py-4 border-b border-gray-200">
            <h3 class="text-lg font-medium text-gray-900">
                Résultats de la recherche
                <% if (placesAlea != null) { %>
                <span class="text-sm text-gray-500">(<%=
                placesAlea.size() %> résultat<%=
                placesAlea.size() > 1 ? "s" : ""
                %>)</span>
                <% } %>
            </h3>
        </div>

        <% if (placesAlea != null && !placesAlea.isEmpty()) { %>
        <div class="overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                <tr>
                    <th
                            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Vol
                    </th>
                    <th
                            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Type de Siège
                    </th>
                    <th
                            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Places Total
                    </th>
                    <th
                            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Places Restantes
                    </th>
                    <th
                            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Places Réservées
                    </th>
                    <th
                            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Prix
                    </th>
                    <th
                            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Date Promotion
                    </th>
                    <th
                            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Statut
                    </th>
                </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                <% for (PlaceAlea placeAlea : placesAlea) { %>
                <tr
                        class="hover:bg-gray-50 transition-colors duration-200">
                    <td class="px-6 py-4 whitespace-nowrap">
                        <div class="flex items-center">
                            <div
                                    class="flex-shrink-0 h-8 w-8">
                                <div
                                        class="h-8 w-8 rounded-full bg-blue-100 flex items-center justify-center">
                                    <svg class="h-4 w-4 text-blue-600"
                                         fill="none"
                                         stroke="currentColor"
                                         viewBox="0 0 24 24">
                                        <path
                                                stroke-linecap="round"
                                                stroke-linejoin="round"
                                                stroke-width="2"
                                                d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                            <div class="ml-4">
                                <div
                                        class="text-sm font-medium text-gray-900">
                                    <%= placeAlea.getVol().getVilleDepart().getNom()
                                    %> → <%=
                                placeAlea.getVol().getVilleArrive().getNom()
                                %>
                                </div>
                                <div
                                        class="text-sm text-gray-500">
                                    <%= placeAlea.getVol().getAvion().getModele()
                                    %>
                                </div>
                            </div>
                        </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                                                                                    <span
                                                                                            class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800">
                                                                                        <%= placeAlea.getTypeSiege().getDesignation()
                                                                                        %>
                                                                                    </span>
                    </td>
                    <td
                            class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        <%= placeAlea.getNombre() %>
                    </td>
                    <td
                            class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                                                                                    <span
                                                                                            class="<%= placeAlea.getRestant() > 0 ? "text-green-600" : "text-red-600"
                                                                                        %> font-medium">
                                                                                        <%= placeAlea.getRestant() %>
                                                                                    </span>
                    </td>
                    <td
                            class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                                                                                    <span
                                                                                            class="<%= placeAlea.getReserver() > 0 ? "text-green-600" : "text-red-600"
                                                                                        %> font-medium">
                                                                                        <%= placeAlea.getReserver() %>
                                                                                    </span>
                    </td>
                    <td
                            class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                                                                                    <span
                                                                                            class="font-medium text-green-600">
                                                                                        <%= String.format("%,.0f",
                                                                                                placeAlea.getPrix()) %> Ar
                                                                                    </span>
                    </td>
                    <td
                            class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        <%= placeAlea.getDateprom() != null ?
                                dateFormatter.format(placeAlea.getDateprom())
                                : "-" %>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                        <% if (placeAlea.getRestant() > 0) {
                        %>
                        <span
                                class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">
                                                                                            Disponible
                                                                                        </span>
                        <% } else { %>
                        <span
                                class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-red-100 text-red-800">
                                                                                                Épuisé
                                                                                            </span>
                        <% } %>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
        <% } else { %>
        <div class="text-center py-12">
            <svg class="mx-auto h-12 w-12 text-gray-400"
                 fill="none" stroke="currentColor"
                 viewBox="0 0 24 24">
                <path stroke-linecap="round"
                      stroke-linejoin="round" stroke-width="2"
                      d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-6m-4 0H4">
                </path>
            </svg>
            <h3 class="mt-2 text-sm font-medium text-gray-900">
                Aucune place aléatoire trouvée</h3>
            <p class="mt-1 text-sm text-gray-500">Essayez de
                modifier vos critères de recherche.</p>
        </div>
        <% } %>
    </div>
</div>
</body>

</html>