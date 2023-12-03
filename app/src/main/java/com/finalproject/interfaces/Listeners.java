package com.finalproject.interfaces;


public interface Listeners {



    interface SettingAction{
        void onLogIn();
        void onFavorite();
        void onEditProfile();
        void onLanguageSetting();
        void onAbout();
        void onTerms();
        void onContactUs();
        void onLogout();
    }
    interface BackListener
    {
        void back();
    }
    interface ProfileActions
    {

        void onContactUs();
        void onLogout();
        void onHelp();
        void onUpdateProfile();
        void onMyWork();
        void onMyAuction();
        void onLang();
        void onOrders();
    }

}
