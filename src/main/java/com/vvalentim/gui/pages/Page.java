package com.vvalentim.gui.pages;

public enum Page {
    SERVER_CONNECTION_PAGE(ServerConnectionPage.class),
    LOGIN_PAGE(LoginPage.class),
    SIGNUP_PAGE(SignupPage.class),

    MANAGE_USER_PAGE(ManageUser.class),
    LIST_CATEGORIES_PAGE(ListCategories.class);

    public final Class<?> pageClass;

    Page(Class<?> pageClass) {
        this.pageClass = pageClass;
    }
}
