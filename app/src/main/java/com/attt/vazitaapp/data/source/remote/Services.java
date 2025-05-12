package com.attt.vazitaapp.data.source.remote;

public class Services {

    public static UserService userService;


    public static UserService getClientService() {
        return userService;
    }

    public static void setClientService(UserService userService) {
        Services.userService = userService;
    }




}