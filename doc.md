kenny516: @workspace je vais t expliquer mon framework java web tous d abord pour declarer un controller il faut mettre l annotation @Controller
public class AuthController { puis pour declarer la method http d une methode dans le controller on met @Get ou @Post (pour l instant ce sont les seul qu il gere ) puis pour mapper une url sur une methode il faut   @Url(road_url = "/loginForm") puis pour obtenir la session on met juste CustomSession customSession comme argument de la requette sachant que customsession contientpackage Model;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;

public class CustomSession {
    private HttpSession httpSession;

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public CustomSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }
    public CustomSession() {
    }


    public void addSession(String key, Object value) {
        httpSession.setAttribute(key, value);
    }

    public Object getAttribute(String key) {
        return httpSession.getAttribute(key);
    }

    public void removeAttribute(String key) {
        httpSession.removeAttribute(key);
    }
    public void destroySession(){
        httpSession.invalidate();;
    }
}
 puis pour faire une redirection vers une page il faut utiliser la class ModelAndView et faire retourner ModelAndView la mehtod et dans model and view on peut set l url set si isredirect et ajouter des donner a envoyer vers la view qui est recuperable avec getattribute  et aussi pour recuperer les parametre de la requette il faut utiliser @Param() example @Param(name = "pseudo") String pseudo  on ajoutant les donner dans le dossier controller donne moi un doc detailler pour qu on comprenne comment l utiliser  

Workspace: Collecte des informations sur l’espace de travailVoici une documentation détaillée sur l'utilisation du framework web Java :

# Documentation du Framework Web

## Configuration des Controllers

### Déclaration d'un Controller
Pour créer un controller, utilisez l'annotation `@Controller` :

```java
@Controller 
public class MonController {
    // Méthodes du controller
}
```

### Mapping des URLs 

Pour mapper une URL sur une méthode, utilisez l'annotation `@Url` combinée avec `@Get` ou `@Post` :

```java
@Get
@Url(road_url = "/ma-route")  
public ModelAndView maMethode() {
    // Code
}

@Post 
@Url(road_url = "/traitement-form")
public ModelAndView traitementFormulaire() {
    // Code
}
```

### Récupération des paramètres

Pour récupérer les paramètres de la requête, utilisez l'annotation `@Param` :

```java
@Post
@Url(road_url = "/login") 
public ModelAndView login(
    @Param(name = "username") String username,
    @Param(name = "password") String password
) {
    // Traitement des paramètres
}
```

### Gestion de la Session 

Pour accéder à la session, ajoutez un paramètre `CustomSession` dans la méthode :

```java
@Get
@Url(road_url = "/profil")
public ModelAndView profil(CustomSession session) {
    // Lecture depuis la session
    Utilisateur user = (Utilisateur) session.getAttribute("user");
    
    // Écriture dans la session  
    session.addSession("dernierAcces", new Date());
    
    // Destruction de la session
    session.destroySession();
}
```

### Retour vers les vues

Utilisez la classe `ModelAndView` pour retourner des vues et transmettre des données :

```java
@Get
@Url(road_url = "/produits")
public ModelAndView listeProduits() {
    ModelAndView mv = new ModelAndView("/views/produits.jsp");
    
    // Ajout de données pour la vue
    mv.add_data("produits", listeProduits);
    mv.add_data("categories", categories);
    
    // Configuration de la redirection
    mv.setIsRedirect(true); // Pour une redirection 302
    mv.setUrl("/nouvelle-url"); // Change l'URL cible
    
    return mv;
}
```

## Exemple complet

Voici un exemple complet de controller :

```java
@Controller
public class AuthController {

    private final UtilisateurService userService;
    
    public AuthController() {
        this.userService = new UtilisateurService();
    }

    @Get
    @Url(road_url = "/login")
    public ModelAndView loginForm() {
        return new ModelAndView("/views/login.jsp");
    }

    @Post 
    @Url(road_url = "/login")
    public ModelAndView processLogin(
        @Param(name = "username") String username,
        @Param(name = "password") String password,
        CustomSession session
    ) {
        ModelAndView mv = new ModelAndView();
        
        Utilisateur user = userService.authenticate(username, password);
        
        if (user != null) {
            // Succès : stockage en session et redirection
            session.addSession("user", user);
            mv.setUrl("/dashboard");
            mv.setIsRedirect(true);
        } else {
            // Échec : retour au formulaire avec erreur
            mv.setUrl("/views/login.jsp");
            mv.add_data("error", "Identifiants invalides");
        }
        
        return mv;
    }
}
```

Les données ajoutées via `add_data()` sont accessibles dans les JSP via `request.getAttribute()`.

et pour les page jsp je prefere utiliser le syntax jsp normal et non jstl et faire des getparameter ou getattribut