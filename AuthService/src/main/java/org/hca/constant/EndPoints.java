package org.hca.constant;

public class EndPoints {
    public static final String VERSION = "/v1";
    //profiles
    public static final String API = "/api";
    public static final String DEV = "/dev";
    public static final String TEST = "/test";
    public static final String ROOT = API + VERSION;
    public static final String  REDIRECT= "redirect:";

    public static final String AUTH = "/auth";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String CONFIRM = "/confirm";
    public static final String REGISTRATION = "/registration";
    public static final String CONFIRMATION = "/confirmation";
    public static final String AUTHENTICATION = "/authentication";
    public static final String CHANGE_PASSWORD = "/password/change";
    public static final String RESET_PASSWORD = "/password/reset";
    public static final String CREATE_PASSWORD = "/password/create";
    public static final String DELETE="/delete"+"/{id}";
}
