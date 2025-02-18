<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <% String error=(String) request.getAttribute("error"); %>
            <!DOCTYPE html>
            <html lang="fr">

            <head>
                <title>Connexion</title>
                <%@ include file="shared/header.jsp" %>
                    <style>
                        .login-container {
                            min-height: 100vh;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                        }

                        .login-form {
                            max-width: 400px;
                            width: 100%;
                        }

                        .login-header {
                            text-align: center;
                            margin-bottom: 2rem;
                        }
                    </style>
            </head>

            <body>
                <div class="login-container">
                    <div class="card login-form">
                        <div class="card-body">
                            <div class="login-header">
                                <h2>Connexion</h2>
                                <p class="text-muted">Connectez-vous pour réserver vos vols</p>
                            </div>

                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">
                                    ${error}
                                </div>
                            </c:if>

                            <form action="${pageContext.request.contextPath}/login" method="post"
                                class="needs-validation" novalidate>
                                <div class="mb-3">
                                    <label for="username" class="form-label">Nom d'utilisateur</label>
                                    <input type="text" class="form-control" id="username" name="pseudo" required>
                                </div>

                                <div class="mb-3">
                                    <label for="password" class="form-label">Mot de passe</label>
                                    <input type="password" class="form-control" id="password" name="motDePasse"
                                        required>
                                </div>

                                <div class="d-grid">
                                    <button type="submit" class="btn btn-custom-primary">Se connecter</button>
                                </div>
                            </form>

                            <div class="mt-3 text-center">
                                <a href="${pageContext.request.contextPath}/" class="text-decoration-none">
                                    Retour à l'accueil
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </body>

            </html>