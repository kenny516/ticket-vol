<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Back Office - Gestion des Vols</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css" rel="stylesheet">
            <style>
                .feature-card {
                    transition: transform 0.2s;
                }

                .feature-card:hover {
                    transform: translateY(-5px);
                }

                .icon-large {
                    font-size: 2rem;
                }
            </style>
        </head>

        <body>
            <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
                <div class="container">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/admin">Administration</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/admin/vols">Vols</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link"
                                    href="${pageContext.request.contextPath}/admin/promotions">Promotions</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link"
                                    href="${pageContext.request.contextPath}/admin/parametres">Paramètres</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>

            <div class="container mt-4">
                <h1 class="text-center mb-4">Tableau de Bord Administration</h1>

                <div class="row g-4">
                    <!-- Gestion des Vols -->
                    <div class="col-md-4">
                        <div class="card h-100 feature-card">
                            <div class="card-body text-center">
                                <i class="bi bi-airplane icon-large text-primary mb-3"></i>
                                <h5 class="card-title">Gestion des Vols</h5>
                                <p class="card-text">Gérez les vols, leurs horaires, prix et destinations.</p>
                                <a href="${pageContext.request.contextPath}/admin/vols" class="btn btn-primary">
                                    Accéder
                                </a>
                            </div>
                        </div>
                    </div>

                    <!-- Gestion des Promotions -->
                    <div class="col-md-4">
                        <div class="card h-100 feature-card">
                            <div class="card-body text-center">
                                <i class="bi bi-tag icon-large text-success mb-3"></i>
                                <h5 class="card-title">Gestion des Promotions</h5>
                                <p class="card-text">Créez et gérez les promotions sur les vols.</p>
                                <a href="${pageContext.request.contextPath}/admin/promotions" class="btn btn-success">
                                    Accéder
                                </a>
                            </div>
                        </div>
                    </div>

                    <!-- Paramètres Système -->
                    <div class="col-md-4">
                        <div class="card h-100 feature-card">
                            <div class="card-body text-center">
                                <i class="bi bi-gear icon-large text-warning mb-3"></i>
                                <h5 class="card-title">Paramètres Système</h5>
                                <p class="card-text">Configurez les délais de réservation et d'annulation.</p>
                                <a href="${pageContext.request.contextPath}/admin/parametres" class="btn btn-warning">
                                    Accéder
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>