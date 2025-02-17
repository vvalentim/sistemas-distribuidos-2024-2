package com.vvalentim.gui.pages;

public enum Page {
    SERVER_CONFIGURATION_PAGE(ServerConfigurationPage.class),
    SERVER_CONNECTION_PAGE(ServerConnectionPage.class),
    LOGIN_PAGE(LoginPage.class),
    SIGNUP_PAGE(SignupPage.class),

    MANAGE_USER_PAGE(ManageUserPage.class),
    LIST_CATEGORIES_PAGE(ListCategoriesPage.class),
    LIST_ONLINE_USERS_PAGE(ListOnlineUsersPage.class);

    public final Class<?> pageClass;

    Page(Class<?> pageClass) {
        this.pageClass = pageClass;
    }
}
