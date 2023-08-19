package com.raiyansoft.mawed.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.chaos.view.PinView;
import com.raiyansoft.mawed.R;
import com.raiyansoft.mawed.adapter.SalonProfileImagesAdapter;
import com.raiyansoft.mawed.model.ImageData;
import com.raiyansoft.mawed.model.auth.User;
import com.raiyansoft.mawed.services.api.IMawedAPI;
import com.raiyansoft.mawed.services.api.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HelperMethods {
    private static final String TAG = HelperMethods.class.getSimpleName();

    public static Double LATITUDE;
    public static Double LONGITUDE;
    public static String ADDRESS;

    private static String imageUrl;

    public static String salonName;
    public static Integer isHome;

    private static Long timeInMillis;
    private static Long dateInMillis;

    public static String services = "services";
    public static int servicesId = 0;
    public static String servicesName = "";

    public static List<ImageData> listImagesUrls = new ArrayList<>();

    public static String getImageUrl() {
        return imageUrl;
    }

    public static void setImageUrl(String imageUrl) {
        HelperMethods.imageUrl = imageUrl;
    }

    public static Long getTimeInMillis() {
        return timeInMillis;
    }

    public static void setTimeInMillis(Long timeInMillis) {
        HelperMethods.timeInMillis = timeInMillis;
    }

    public static Long getDateInMillis() {
        return dateInMillis;
    }

    public static void setDateInMillis(Long dateInMillis) {
        HelperMethods.dateInMillis = dateInMillis;
    }

    public static IMawedAPI getMawedAPI() {
        return RetrofitClient.getClient().create(IMawedAPI.class);
    }



    public static String getUserToken(Activity activity) {
        if (PreferencesManager.loadUserToken(activity, Const.KEY_USER_TOKEN) != null && !PreferencesManager.loadUserToken(activity, Const.KEY_USER_TOKEN).equals("")) {
            return "Bearer " + PreferencesManager.loadUserToken(activity, Const.KEY_USER_TOKEN);
        } else
            return null;
    }

    // TODO 1 USER
    // TODO 2 SALON
    // TODO 3 EMPLOYEE
    public static String getUserType(Activity activity) {
        if (PreferencesManager.loadAppData(activity, Const.KEY_USER_TYPE) != null && !PreferencesManager.loadAppData(activity, Const.KEY_USER_TYPE).equals("")) {
            return PreferencesManager.loadAppData(activity, Const.KEY_USER_TYPE);
        } else {
            return null;
        }
    }

    public static User getCurrentUser(Activity activity) {
        if (PreferencesManager.getUserData(activity, Const.KEY_USER_DATA) != null && !PreferencesManager.getUserData(activity, Const.KEY_USER_DATA).equals("")) {
            return PreferencesManager.getUserData(activity, Const.KEY_USER_DATA);
        } else {
            return null;
        }
    }

    public static void saveAppLanguage(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        activity.getResources().updateConfiguration(configuration, activity.getResources().getDisplayMetrics());
    }

    public static void checkAppLanguage(Activity activity) {
        if (PreferencesManager.loadLanguage(activity, Const.KEY_LANGUAGE) != null && !PreferencesManager.loadLanguage(activity, Const.KEY_LANGUAGE).equals(""))
            HelperMethods.saveAppLanguage(activity, PreferencesManager.loadLanguage(activity, Const.KEY_LANGUAGE));
        else
            HelperMethods.saveAppLanguage(activity, getDeviceLanguage());
    }

    public static String getAppLanguage(Activity activity) {
        if (PreferencesManager.loadLanguage(activity, Const.KEY_LANGUAGE).equals(Const.KEY_LANGUAGE_AR))
            return Const.KEY_LANGUAGE_AR;
        else if (PreferencesManager.loadLanguage(activity, Const.KEY_LANGUAGE).equals(Const.KEY_LANGUAGE_EN))
            return Const.KEY_LANGUAGE_EN;
        else
            return getDeviceLanguage();
    }

    public static Locale getAppLocale(Activity activity) {
        return new Locale(getAppLanguage(activity));
    }

    public static String getDeviceLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String getCurrency(Activity activity) {
        if (getAppLanguage(activity).equals("ar"))
            return "دك";
        else
            return "KD";
    }

    public static void showCustomDialog(AlertDialog dialog) {
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
    }

    public static void loadAnimation(Activity activity, View view) {
        if (HelperMethods.getAppLanguage(activity).equals("ar"))
            view.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.anim_item_ar));
        else
            view.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.anim_item_en));
    }

    public static void showDateDialog(Activity activity, String pattern, TextView textView) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, dayOfMonth);
//            Log.d(TAG, "showDateDialog: " + gregorianCalendar.getTimeInMillis());

            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, HelperMethods.getAppLocale(activity));
//            Log.d(TAG, "showDateDialog: " + dateFormat.pattern(calendar.getTime()));
            setDateInMillis(calendar.getTimeInMillis());
            textView.setText(dateFormat.format(calendar.getTime()));

        };

        new DatePickerDialog(activity, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public static String getDateToday(Activity activity) {
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", HelperMethods.getAppLocale(activity));
        String currentDay  = format.format(d.getTime());
        Log.d(TAG, "initUI: " + currentDay);
        return currentDay;
    }

    public static String getDateToday(Activity activity, String formate) {
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat(formate, HelperMethods.getAppLocale(activity));
        String currentDay  = format.format(d.getTime());
        Log.d(TAG, "initUI: " + currentDay);
        return currentDay;
    }

    public static void showTimeDialog(Activity activity, String pattern, TextView textView) {

        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            SimpleDateFormat timeFormat = new SimpleDateFormat(pattern, HelperMethods.getAppLocale(activity));

            Log.d(TAG, "showTimeDialog: " + calendar.getTimeInMillis());
            Log.d(TAG, "showTimeDialog: " + timeFormat.format(new Date(calendar.getTimeInMillis())));
            setTimeInMillis(calendar.getTimeInMillis());
            textView.setText(timeFormat.format(calendar.getTimeInMillis()));
        };

        new TimePickerDialog(activity, timeSetListener,
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                true)
                .show();
    }

    public static String monthYearFromDate(Activity activity, LocalDate date, String formate) {
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern(formate, new Locale("en"));
            return date.format(formatter);
        }
        return "";
    }

    public static String formatDecimalValue(Double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedValue = decimalFormat.format(value);
        return formattedValue;
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @SuppressLint("UnspecifiedImmutableFlag")
    public static void showNotification(Context context, int notificationId, String title, String content, Intent intent) {
        PendingIntent pendingIntent = null;
        if (intent != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                pendingIntent = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_MUTABLE);
            else
                pendingIntent = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        String NOTIFICATION_CHANNEL_ID = "SOOG";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Soog", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Soog");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(context.getColor(R.color.colorPrimary));
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_foreground));
        if (pendingIntent != null)
            builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notificationManager.notify(notificationId, notification);
    }

    public static void openSingleGallery(Activity activity, ImageView imageView) {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Album.initialize(AlbumConfig.newBuilder(activity)
                    .setAlbumLoader(new MediaLoader())
                    .build());

            Album.image(activity)
                    .singleChoice()
                    .camera(true)
                    .columnCount(3)
                    .onResult(result -> {
                        if (imageView != null) {
                            imageView.setVisibility(View.VISIBLE);
                            setImageUrl(result.get(0).getPath());
//                            imageUrl = result.get(0).getPath();
                            Log.d(TAG, "openSingleGallery: " + getImageUrl());
                            Glide.with(activity).load(getImageUrl()).into(imageView);
                        }
                    })
                    .onCancel(result -> Log.d(TAG, "openSingleGallery: " + result))
                    .start();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, Const.CODE_REQUEST_STOAREG);
//            openSingleGallery(activity, listImages, adapter, imageView);
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    public static void selectMultipleImage(Activity activity, boolean isCamera, List<ImageData> listImages, SalonProfileImagesAdapter adapter) {

        Album.initialize(AlbumConfig.newBuilder(activity)
                .setAlbumLoader(new MediaLoader()).build());

        Album.image(activity) // Image selection.
                .multipleChoice()
                .camera(isCamera)
                .columnCount(3)
                .selectCount(30)
                .onResult(result -> {
                    for (int i = 0; i < result.size(); i++) {
                        listImagesUrls.add(new ImageData(result.get(i).getPath()));
                    }
                    listImages.addAll(listImagesUrls);
                    adapter.notifyDataSetChanged();

                })
                .onCancel(result -> {
                    Log.e(TAG, "selectMultipleImage: " + result);
                }).start();
    }

    public static MultipartBody.Part convertFileToMultiPart(String filePath, String key) {
        if (filePath != null) {
            File file = new File(filePath);
            RequestBody requestBody = RequestBody.create(file, MediaType.parse("image/jpeg"));
            MultipartBody.Part fileBody = MultipartBody.Part.createFormData(key, file.getName(), requestBody);
            return fileBody;
        } else
            return null;
    }

    public static MultipartBody.Part convertFileToMultiPart(String filePath, String key, String type) {
        if (filePath != null) {
            File file = new File(filePath);
            RequestBody requestBody = RequestBody.create(file, MediaType.parse(type));
            MultipartBody.Part fileBody = MultipartBody.Part.createFormData(key, file.getName(), requestBody);
            return fileBody;
        } else
            return null;
    }

    public static MultipartBody.Part convertToMultiPart(String value, String key) {
        if (value != null) {
            MultipartBody.Part fileBody = MultipartBody.Part.createFormData(key, value);
            return fileBody;
        } else
            return null;
    }

    public static RequestBody convertToRequestBody(String body) {
        if (!body.isEmpty()) {
            RequestBody requestBody = RequestBody.create(body, MediaType.parse("multipart/form-data"));
            return requestBody;
        } else
            return null;
    }

    public static boolean validatePhoneNumber(Activity context, String phoneNumber, EditText editText) {
        if (TextUtils.isEmpty(phoneNumber)) {
            editText.setError(context.getString(R.string.please_enter_phone));
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public static boolean validateFirstName(Context context, String firstName, EditText editText) {
        if (TextUtils.isEmpty(firstName)) {
            editText.setError(context.getString(R.string.enter_your_first_name));
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public static boolean validateLastName(Context context, String lastName, EditText editText) {
        if (TextUtils.isEmpty(lastName)) {
            editText.setError(context.getString(R.string.enter_your_last_name));
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public static boolean validateFullName(Context context, String fullName, EditText editText) {
        if (TextUtils.isEmpty(fullName)) {
            editText.setError(context.getString(R.string.enter_your_full_name));
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public static boolean validateCommercialName(Context context, String commercialName, EditText editText) {
        if (TextUtils.isEmpty(commercialName)) {
            editText.setError(context.getString(R.string.enter_your_commercial_name));
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public static boolean validateTypeBusiness(Context context, String typeBusiness, EditText editText) {
        if (TextUtils.isEmpty(typeBusiness)) {
            editText.setError(context.getString(R.string.enter_type_business));
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public static boolean validateWhatsappNumber(Context context, String whatsappNumber, EditText editText) {
        if (TextUtils.isEmpty(whatsappNumber)) {
            editText.setError(context.getString(R.string.enter_whatsapp_number));
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public static boolean validateOldPhoneNumber(Activity context, String phoneNumber, EditText editText) {
        if (TextUtils.isEmpty(phoneNumber)) {
            editText.setError(context.getString(R.string.please_enter_old_phone));
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public static boolean validateNewPhoneNumber(Activity context, String phoneNumber, EditText editText) {
        if (TextUtils.isEmpty(phoneNumber)) {
            editText.setError(context.getString(R.string.please_enter_new_phone));
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public static boolean validateEmail(Activity context, String email, EditText editEmail) {

        if (TextUtils.isEmpty(email)) {
            editEmail.setError(context.getString(R.string.please_enter_email));
            return false;
        } else if (!validateEmail(email)) {
            editEmail.setError(context.getString(R.string.invalid_email));
            return false;
        } else {
            editEmail.setError(null);
            return true;
        }
    }

    private static boolean validateEmail(String emailAddress) {
        String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }


    @SuppressLint("MissingInflatedId")
    public static void showCustomSnackBar(Context context, View view,
                                          @NonNull String message, @NonNull boolean isSuccessful,
                                          int duration,
                                          int gravity,
                                          View.OnClickListener listener) {

        Snackbar mSnackBar = Snackbar.make(view, "", duration);

        int layout;
        if (isSuccessful)
            layout = R.layout.snack_success_layout;
        else
            layout = R.layout.snack_error_layout;

        View snackView = LayoutInflater.from(context)
                .inflate(layout, null);

        mSnackBar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) mSnackBar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);

        FrameLayout.LayoutParams mFrameParams = (FrameLayout.LayoutParams) mSnackBar.getView().getLayoutParams();
        mFrameParams.gravity = gravity;
        mSnackBar.getView().setLayoutParams(mFrameParams);


        CardView cardSuccess = snackView.findViewById(R.id.card_snackbar);
        TextView tvMessage = snackView.findViewById(R.id.tv_message);
        TextView retry = snackView.findViewById(R.id.tv_click_wifi);
        tvMessage.setText(message);
        if (listener != null) {
            retry.setVisibility(View.VISIBLE);
            retry.setOnClickListener(listener);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            cardSuccess.setElevation(8);
        else
            cardSuccess.setElevation(0);

        snackbarLayout.addView(snackView, 0);
        mSnackBar.show();

    }

    public static void showSnackBar(Context context, View view,
                                          @NonNull String message, @NonNull boolean isSuccessful,
                                          int duration,
                                          int gravity) {

        Snackbar mSnackBar = Snackbar.make(view, "", duration);

        int layout;
        if (isSuccessful)
            layout = R.layout.snack_payment_success_layout;
        else
            layout = R.layout.snack_payment_success_layout;

        View snackView = LayoutInflater.from(context)
                .inflate(layout, null);

        mSnackBar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) mSnackBar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);

        FrameLayout.LayoutParams mFrameParams = (FrameLayout.LayoutParams) mSnackBar.getView().getLayoutParams();
        mFrameParams.gravity = gravity;
        mSnackBar.getView().setLayoutParams(mFrameParams);


        CardView cardSuccess = snackView.findViewById(R.id.card_snackbar);
        TextView tvMessage = snackView.findViewById(R.id.tv_message);
        tvMessage.setText(message);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            cardSuccess.setElevation(8);
        else
            cardSuccess.setElevation(0);

        snackbarLayout.addView(snackView, 0);
        mSnackBar.show();

    }

    public static void showCustomToast(Activity activity, String ToastTitle, boolean isSuccessful) {

        LayoutInflater inflater = activity.getLayoutInflater();

        int layoutId;

        if (isSuccessful) {
            layoutId = R.layout.toast_success_layout;
        } else {
            layoutId = R.layout.toast_error_layout;
        }

        View layout = inflater.inflate(layoutId, null);

        TextView text = layout.findViewById(R.id.tv_message);
        text.setText(ToastTitle);

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static boolean validateOTP(Activity context, String otp, PinView pinView) {
        if (TextUtils.isEmpty(otp)) {
            pinView.setError(context.getString(R.string.enter_verify_code));
            return false;
        } else {
            pinView.setError(null);
            return true;
        }
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected()))
            return true;
        else
            return false;
    }

    public static void wifiSettings(Activity context) {
        context.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
    }

}
