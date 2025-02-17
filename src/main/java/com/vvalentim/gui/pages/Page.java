package com.vvalentim.gui.pages;

public enum Page {
    SERVER_CONFIGURATION_PAGE(ServerConfigurationPage.class),
    LIST_ONLINE_USERS_PAGE(ListOnlineUsersPage.class),

    SERVER_CONNECTION_PAGE(ServerConnectionPage.class),
    LOGIN_PAGE(LoginPage.class),
    SIGNUP_PAGE(SignupPage.class),

    MANAGE_USER_PAGE(ManageUserPage.class),
    LIST_CATEGORIES_PAGE(ListCategoriesPage.class),
    ADD_CATEGORY_PAGE(AddCategoryPage.class),
    LIST_NOTIFICATIONS_PAGE(ListNotificationsPage.class),
    LIST_MY_NOTIFICATIONS_PAGE(ListMyNotificationsPage.class),
    ADD_NOTIFICATION_PAGE(AddNotificationPage.class),
    EDIT_NOTIFICATION_PAGE(EditNotificationPage.class),
    VIEW_NOTIFICATION_PAGE(ViewNotificationPage.class);

    public final Class<?> pageClass;

    Page(Class<?> pageClass) {
        this.pageClass = pageClass;
    }
}
