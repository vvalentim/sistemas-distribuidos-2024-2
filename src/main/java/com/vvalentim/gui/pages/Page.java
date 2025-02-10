package com.vvalentim.gui.pages;

public enum Page {
    SERVER_CONFIG(ClientConnectionPage.class),
    LOGIN_PAGE(LoginPage.class),
    SIGNUP_PAGE(SignupPage.class),
    HOME_PAGE(HomePage.class);

    public final Class<?> pageClass;

    Page(Class<?> pageClass) {
        this.pageClass = pageClass;
    }
}
