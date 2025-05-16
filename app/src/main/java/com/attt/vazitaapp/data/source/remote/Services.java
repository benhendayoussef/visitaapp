package com.attt.vazitaapp.data.source.remote;

public class Services {

    public static UserService userService;
    public static DossierService dossierService;


    public static UserService getClientService() {
        return userService;
    }
    public static DossierService getDossierService() {
        return dossierService;
    }

    public static void setClientService(UserService userService) {
        Services.userService = userService;
    }
    public static void setDossierService(DossierService dossierService) {
        Services.dossierService = dossierService;
    }




}