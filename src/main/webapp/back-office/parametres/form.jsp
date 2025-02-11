<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Paramètres Système - Back Office</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>

        <body>
            <div class="container mt-4">
                <h2>Paramètres Système</h2>

                <div class="card">
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/admin/parametres/update" method="post"
                            class="needs-validation" novalidate>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="heuresMinimumReservation" class="form-label">
                                        Heures minimum avant vol pour réservation
                                    </label>
                                    <div class="input-group">
                                        <input type="number" class="form-control" name="heuresMinimumReservation"
                                            value="${parametre.heuresMinimumReservation}" min="0" required>
                                        <span class="input-group-text">heures</span>
                                    </div>
                                    <div class="form-text">
                                        Délai minimum requis avant le départ du vol pour effectuer une réservation
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <label for="heuresMinimumAnnulation" class="form-label">
                                        Heures minimum avant vol pour annulation
                                    </label>
                                    <div class="input-group">
                                        <input type="number" class="form-control" name="heuresMinimumAnnulation"
                                            value="${parametre.heuresMinimumAnnulation}" min="0" required>
                                        <span class="input-group-text">heures</span>
                                    </div>
                                    <div class="form-text">
                                        Délai minimum requis avant le départ du vol pour annuler une réservation
                                    </div>
                                </div>
                            </div>

                            <div class="alert alert-info">
                                <strong>Note:</strong> Ces paramètres affectent les conditions de réservation et
                                d'annulation
                                pour tous les vols.
                            </div>

                            <div class="mb-3">
                                <button type="submit" class="btn btn-primary">Enregistrer les modifications</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
            <script>
                // Validation des formulaires Bootstrap
                (function () {
                    'use strict'
                    var forms = document.querySelectorAll('.needs-validation')
                    Array.prototype.slice.call(forms)
                        .forEach(function (form) {
                            form.addEventListener('submit', function (event) {
                                if (!form.checkValidity()) {
                                    event.preventDefault()
                                    event.stopPropagation()
                                }
                                form.classList.add('was-validated')
                            }, false)
                        })
                })()
            </script>
        </body>

        </html>