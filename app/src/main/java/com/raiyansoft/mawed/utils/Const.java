package com.raiyansoft.mawed.utils;

public interface Const {

    String testToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL21hd2VkLndvcmsvYXBpL3VzZXIvYXV0aCIsImlhdCI6MTY4MDcxODI4OSwiZXhwIjoxNjkxMjMwMjg5LCJuYmYiOjE2ODA3MTgyODksImp0aSI6IlNlWmcwZ2p5Vkc3Z2ZjTXgiLCJzdWIiOjEsInBydiI6IjYxMzQwMGVmYjFiZmI1MGY5NmY5MGIzZWZmM2ViMWU0OWRmNGU4MWQifQ.Ir-rCVQcI5pY3X_8OGP1D7vxXFGD0og8zaTWKW4qEnE";

    String testSlider = "https://i.postimg.cc/28rwR7c0/test-slider.png";
    String defaultUserImage = "https://i.postimg.cc/KcNg9Fpm/default-avatar2.png";
    String tmpUserImage = "http://mawed.work/assets/tmp";

    // TODO Boarding
    String KEY_STARTED_SCREENS = "started";

    String KEY_SERVICES = "services";
    String KEY_CATEGORIES = "categories";
    String KEY_SECTION = "section";

    // TODO USER
    String KEY_USER_DATA = "userdata";
    String KEY_USER_TYPE = "userType";
    String FIREBASE_TOKEN = "FireBaseToken";
    String KEY_USER_TOKEN = "userToken";

    // TODO FILTER
    String KEY_SERVICE_ID = "service_id";
    String KEY_GOVERNORATE_ID = "governorate_id";
    String KEY_DATE = "date";
    String KEY_SALON_ID = "salon_id";
    String KEY_SALON_FAV = "salon_fav";
    String KEY_EMPLOYEE_ID = "employee_id";

    // TODO Languages
    String KEY_LANGUAGE = "language";
    String KEY_LANGUAGE_AR = "ar";
    String KEY_LANGUAGE_EN = "en";

    // TODO Location
    String KEY_CURRENT_LATITUDE = "current_latitude";
    String KEY_CURRENT_LONGITUDE = "current_longitude";
    String KEY_SALON_LATITUDE = "salon_latitude";
    String KEY_SALON_LONGITUDE = "salon_longitude";
    String KEY_SALON_NAME = "salon_name";

    // TODO Permissions
    int CODE_REQUEST_ALL_PERMISSOINS = 1;
    int CODE_REQUEST_STOAREG = 2;
    int CODE_REQUEST_LOCATION = 3;
    int REQUEST_CHECK_SETTINGS = 4;

    // TODO SETTINGS
    String KEY_ABOUT = "about";
    String KEY_PRIVACY = "privacy";
    String KEY_CONDITIONS = "conditions";

    //
    String KEY_APPOINTMENT_ID = "appointment_id";
    String KEY_BOOKING_DATA = "bookin_data";

    String KEY_PAYMENT_STATUS = "payment_status";
    String PAYMENT_STATUS_SUCCESS = "payment_success";
    String PAYMENT_STATUS_FAILED = "payment_failed";

    // TODO PAYING
    String KEY_PAYING_URL = "paying_url";
    String KEY_PROFILE = "salon_profile";
    String KEY_SECTIONS = "sections";
}