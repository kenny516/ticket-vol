<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String error = (String) request.getAttribute("error");
%>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Connexion</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
            <style>
                .login-container {
                    min-height: 100vh;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    background: linear-gradient(135deg, #6c757d, #495057);
                }

                .login-form {
                    background: white;
                    padding: 2rem;
                    border-radius: 10px;
                    box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
                    width: 100%;
                    max-width: 400px;
                }

                .login-header {
                    text-align: center;
                    margin-bottom: 2rem;
                }

                .error-message {
                    color: #dc3545;
                    margin-bottom: 1rem;
                    text-align: center;
                }
            </style>
        </head>

        <body>
            <div class="login-container">
                <div class="login-form">
                    <div class="login-header">
                        <h2>Connexion</h2>
                        <p class="text-muted">Connectez-vous pour réserver vos vols</p>
                    </div>

                    <c:if test="${not empty error}">
                        <div class="error-message">
                            ${error}
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/login" method="post" class="needs-validation"
                        novalidate>
                        <div class="mb-3">
                            <label for="username" class="form-label">Nom d'utilisateur</label>
                            <input type="text" class="form-control" id="username" name="pseudo" required>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Mot de passe</label>
                            <input type="password" class="form-control" id="password" name="motDePasse" required>
                        </div>

                        <button type="submit" class="btn btn-primary w-100">Se connecter</button>
                    </form>

                    <div class="mt-3 text-center">
                        <a href="${pageContext.request.contextPath}/" class="text-decoration-none">
                            Retour à l'accueil
                        </a>
                    </div>
                </div>
            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>