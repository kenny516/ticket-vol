package com.mg.controller.front_office;

import Annotation.*;
import Model.CustomSession;
import Model.ModelAndView;
import Utils.UploadFile.UploadFile;
import com.mg.model.Utilisateur;
import com.mg.service.UtilisateurService;

@Controller
public class UtilisateurController {
    private final UtilisateurService utilisateurService = new UtilisateurService();

    @Get
    @Url(road_url = "/profil")
    public ModelAndView profilDetail(CustomSession customSession) throws Exception {
        Utilisateur utilisateur = customSession.getAttribute("user") != null ? (Utilisateur) customSession.getAttribute("user") : null;
        if (utilisateur == null) {
            return new ModelAndView("/front-office/login.jsp");
        }
        ModelAndView modelAndView = new ModelAndView("/front-office/profil.jsp");
        modelAndView.add_data("utilisateur", utilisateur);
        return modelAndView;
    }


    @Post
    @Url(road_url = "/uploadProfilePicture")
    public ModelAndView updatePdp(CustomSession session,@Param(name = "profilePicture")UploadFile uploadFile) throws Exception {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        String fileName = utilisateur.getPseudo()+utilisateur.getPrenom()+"."+UploadFile.extractFileName(uploadFile.getPart()).split("\\.")[1];
        fileName = uploadFile.saveFile("C:/Program Files/Apache Software Foundation/Tomcat 10.1/webapps/ticket-vol-assets",fileName);
        fileName = fileName.replace("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps","");

        utilisateur.setPdp(fileName);

        utilisateurService.update(utilisateur);
        session.addSession("user", utilisateur);
        ModelAndView modelAndView = new ModelAndView("/ticket-vol/profil");
        modelAndView.setIsRedirect(true);
        return modelAndView;
    }

}
