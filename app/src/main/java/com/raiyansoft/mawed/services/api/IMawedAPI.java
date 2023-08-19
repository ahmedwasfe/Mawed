package com.raiyansoft.mawed.services.api;

import com.raiyansoft.mawed.model.DeleteSalonService;
import com.raiyansoft.mawed.model.address.Addresses;
import com.raiyansoft.mawed.model.address.add.AddAddress;
import com.raiyansoft.mawed.model.address.delete.DeleteAddress;
import com.raiyansoft.mawed.model.appointments.Appointments;
import com.raiyansoft.mawed.model.auth.Logout;
import com.raiyansoft.mawed.model.auth.changePhone.ChangePhone;
import com.raiyansoft.mawed.model.auth.register.Register;
import com.raiyansoft.mawed.model.auth.verifyOTP.ResendCode;
import com.raiyansoft.mawed.model.auth.verifyOTP.VerifyOTP;
import com.raiyansoft.mawed.model.auth.login.Auth;
import com.raiyansoft.mawed.model.convertSalon.ConvertToSalon;
import com.raiyansoft.mawed.model.employee.Empolyee;
import com.raiyansoft.mawed.model.employee.review.Review;
import com.raiyansoft.mawed.model.favorite.Favorites;
import com.raiyansoft.mawed.model.favorite.addRemove.AddRemoveFavorite;
import com.raiyansoft.mawed.model.filter.Filter;
import com.raiyansoft.mawed.model.governorate.Governorate;
import com.raiyansoft.mawed.model.home.Home;
import com.raiyansoft.mawed.model.notifications.Notifications;
import com.raiyansoft.mawed.model.notifications.ReadNotification;
import com.raiyansoft.mawed.model.payment.paymentMethods.PaymentMethods;
import com.raiyansoft.mawed.model.payment.paymentStatus.PaymentStatus;
import com.raiyansoft.mawed.model.plans.Plans;
import com.raiyansoft.mawed.model.plans.orderPlan.OrderPlan;
import com.raiyansoft.mawed.model.profile.Profile;
import com.raiyansoft.mawed.model.profile.updateProfile.UpdateProfile;
import com.raiyansoft.mawed.model.region.Region;
import com.raiyansoft.mawed.model.salon.Employee;
import com.raiyansoft.mawed.model.salon.Orders;
import com.raiyansoft.mawed.model.salon.management.ManagementSalon;
import com.raiyansoft.mawed.model.salon.sales.Sales;
import com.raiyansoft.mawed.model.salon.services.SalonServices;
import com.raiyansoft.mawed.model.salon.services.save.SaveService;
import com.raiyansoft.mawed.model.salon.workHour.SaveWorkHours;
import com.raiyansoft.mawed.model.salon.workHour.WorkHour;
import com.raiyansoft.mawed.model.settings.ContactUs;
import com.raiyansoft.mawed.model.userOrders.UserOrders;
import com.raiyansoft.mawed.model.userOrders.checkout.CheckOut;
import com.raiyansoft.mawed.model.userOrders.coupon.CheckCoupon;
import com.raiyansoft.mawed.model.salon.updateStatus.UpdateOrderStatus;
import com.raiyansoft.mawed.model.userSalon.SalonDetails;
import com.raiyansoft.mawed.model.sections.Sections;
import com.raiyansoft.mawed.model.settings.Settings;
import com.raiyansoft.mawed.model.settings.about.About;
import com.raiyansoft.mawed.model.settings.questions.Questions;
import com.raiyansoft.mawed.model.userOrders.UserAppointmentDetails;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IMawedAPI {

    @POST("v1/setting")
    Call<Settings> getSettings();

    @POST("user/auth")
    @FormUrlEncoded
    Call<Auth> login(@Field("mobile_number") String phoneNumber);

    @POST("user/register")
    @FormUrlEncoded
    Call<Register> register(
            @Header("lang") String language,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("mobile_number") String phoneNumber,
            @Field("device_type") String deviceType,
            @Field("device_token") String deviceToken,
            @Field("date_of_birth") String dateOfBirth,
            @Field("sex") Integer gender);

    @POST("user/activateAccount")
    @FormUrlEncoded
    Call<VerifyOTP> verifyPhoneNumber(@Header("Authorization") String token,
                                      @Field("activation_code") String otp,
                                      @Field("type") int type);

    @POST("user/resendActivation")
    Call<ResendCode> resendCode(@Header("Authorization") String token);

    @POST("user/update")
    @Multipart
    Call<UpdateProfile> updateUserProfile(@Header("lang") String language,
                                          @Header("Authorization") String token,
                                          @Part MultipartBody.Part image,
                                          @Part("first_name") RequestBody firstName,
                                          @Part("last_name") RequestBody lastName,
                                          @Part("email") RequestBody email,
                                          @Part("mobile_number") RequestBody mobileNumber,
                                          @Part("date_of_birth") RequestBody dateOfBirth,
                                          @Part("gender") RequestBody gender);

    @POST("user/changePhone")
    @FormUrlEncoded
    Call<ChangePhone> changePhone(@Header("lang") String language,
                                  @Header("Authorization") String token,
                                  @Field("mobile_number_old") String oldPhone,
                                  @Field("mobile_number_new") String newPhone);

    @POST("user/logout")
    Call<Logout> logout(@Header("lang") String language,
                        @Header("Authorization") String token);


    // TODO Regions
    @POST("v2/cities")
    Call<Governorate> getGovernorate(@Header("lang") String language);


    // TODO Regions
    @POST("v2/regions/{governorate_id}")
    Call<Region> getRegions(@Header("lang") String language,
                            @Path("governorate_id") Integer id);

    // TODO Address
    // TODO Get Addresses
    @POST("address/get")
    Call<Addresses> getAddresses(@Header("lang") String language,
                                 @Header("Authorization") String token);

    // TODO Add Address
    @POST("address/add")
    @FormUrlEncoded
    Call<AddAddress> addAddress(@Header("lang") String language,
                                @Header("Authorization") String token,
                                @Field("title") String title,
                                @Field("widget") String widget,
                                @Field("street") String street,
                                @Field("house_number") String houseNumber,
                                @Field("city_id") Integer governorateId,
                                @Field("region_id") Integer regionId,
                                @Field("lat") Double latitude,
                                @Field("lng") Double longitude);

    // TODO DELETE ADDRESS
    @POST("address/delete")
    @FormUrlEncoded
    Call<DeleteAddress> deleteAddress(@Header("lang") String language,
                                      @Header("Authorization") String token,
                                      @Field("id") int addressId);

    // TODO HOME
    @POST("v1/home")
    Call<Home> getHomeData(@Header("lang") String language,
                           @Header("Authorization") String token);

    // TODO FILTET
    @POST("v1/homefilter")
    @FormUrlEncoded
    Call<Filter> filter(@Header("lang") String language,
                        @Header("Authorization") String token,
                        @Field("service_id") Integer serviceId,
                        @Field("governorate_id") Integer governorateId,
                        @Field("date") String date,
                        @Field("salon_id") Integer salonId,
                        @Field("lat") double latitudelatitude,
                        @Field("lng") double longitude);

    @POST("v1/salon")
    @FormUrlEncoded
    Call<Filter> getSalons(@Header("lang") String language,
                           @Header("Authorization") String token,
                           @Field("cat_id") Integer sectionId);

    // TODO SECTIONS
    @POST("v1/categories")
    @FormUrlEncoded
    Call<Sections> getSections(
            @Header("Authorization") String token,
            @Header("lang") String language,
            @Field("gender") int gender);

    // TODO SALON
    @POST("v1/salon/details")
    @FormUrlEncoded
    Call<SalonDetails> getSalonDetails(@Header("lang") String language,
                                       @Field("id") Integer salonId,
                                       @Field("cat_id") Integer categoryId);

    // TODO Favorites
    @POST("v1/getFav")
    Call<Favorites> getFavorites(
            @Header("lang") String language,
            @Header("Authorization") String token);

    @POST("products/addFav")
    @FormUrlEncoded
    Call<AddRemoveFavorite> addToFavorite(@Header("lang") String language,
                                          @Header("Authorization") String token,
                                          @Field("id") Integer salonId);

    @POST("v1/deleteFav")
    @FormUrlEncoded
    Call<AddRemoveFavorite> removeFromFavorite(@Header("lang") String language,
                                          @Header("Authorization") String token,
                                          @Field("id") Integer salonId);


    // TODO Appointments
    @POST("orders/getOrders/appointments")
    Call<Appointments> getAppointments(@Header("lang") String language,
                                       @Header("Authorization") String token);

    // TODO USER Appointment Details
    @POST("orders/getOrderDetails")
    @FormUrlEncoded
    Call<UserAppointmentDetails> getUserAppointmentDetails(@Header("lang") String language,
                                                           @Header("Authorization") String token,
                                                           @Field("id") Integer id);


    // TODO dates == Past Appointments
    @POST("orders/getOrders/dates")
    Call<UserOrders> getUserOrdersPastAppointments(@Header("lang") String language,
                                                   @Header("Authorization") String token);

    // TODO appointments == Upcoming Dates
    @POST("orders/getOrders/appointments")
    Call<UserOrders> getUserOrdersUpcomingDates(@Header("lang") String language,
                                                   @Header("Authorization") String token);

    @POST("v1/employee/details")
    @FormUrlEncoded
    Call<Empolyee> getEmpolyeeData(@Header("lang") String language,
                                   @Header("Authorization") String token,
                                   @Field("id") Integer salonId);

    @POST("v1/employee/details")
    @FormUrlEncoded
    Call<Empolyee> getEmpolyeeTimes(@Header("lang") String language,
                                    @Header("Authorization") String token,
                                    @Field("id") Integer salonId,
                                    @Field("services[]") List<Integer> services,
                                    @Field("date") String date);

    @POST("v1/reviews/store")
    @FormUrlEncoded
    Call<Review> review(@Header("lang") String language,
                        @Header("Authorization") String token,
                        @Field("employee_id") Integer employeeId,
                        @Field("rate") double rate);


    // TODO Notifications
    @GET("user/notification")
    Call<Notifications> getNotifications(@Header("lang") String language,
                                         @Header("Authorization") String token);

    @GET("user/read/{id}")
    Call<ReadNotification> readNotification(@Header("lang") String language,
                                            @Header("Authorization") String token,
                                            @Path("id") int notificationId);

    // TODO SETTINGS
    @POST("v1/about")
    Call<About> about(@Header("lang") String language);

    @POST("v1/questions")
    Call<Questions> getQuestions(@Header("lang") String language);

    @POST("v1/contactUs")
    @FormUrlEncoded
    Call<ContactUs> contactUs(@Header("lang") String language,
                              @Header("Authorization") String token,
                              @Field("subject") String subject,
                              @Field("message") String message,
                              @Field("name") String userName,
                              @Field("email") String email);

    @POST("orders/checkPromo")
    @FormUrlEncoded
    Call<CheckCoupon> checkCoupon(@Header("lang") String language,
                                  @Header("Authorization") String token,
                                  @Field("promo_code") String promoCode);

    @Headers({("Accept: application/json")})
    @POST("orders/checkout")
    @FormUrlEncoded
    Call<CheckOut> checkOut(@Header("lang") String language,
                            @Header("Authorization") String token,
                            @FieldMap Map<String, Object> fields,
                            @Field("employee_id") int employeeId,
                            @Field("total") int total,
                            @Field("notes") String notes,
                            @Field("address_id") int addressId,
                            @Field("date") String date,
                            @Field("start_time") String time,
                            @Field("home") int home,
                            @Field("payment_id") int payment_id,
                            @Field("promo_code") String promoCode,
                            @Field("discount") int discount);

    // TODO PLANS
    @POST("v1/packages")
    Call<Plans> getPackages(@Header("lang") String language,
                            @Header("Authorization") String token);

    // TODO ORDER PLAN
    @POST("orders/order_packages")
    @FormUrlEncoded
    Call<OrderPlan> orderPlan(@Header("lang") String language,
                              @Header("Authorization") String token,
                              @Field("id") Integer planId,
                              @Field("start_date") String startDate,
                              @Field("payment_id") Integer payment_id);

    // TODO PAYMENT METHODS
    @POST("v2/payment")
    Call<PaymentMethods> getPaymentMethods(@Header("lang") String language);

    // TODO CHANGE PAYMENT STATUS
    // TODO STATUS 1 => true // 0 => false
    // TODO TYPE 1 => 1 order // 2 paceges
    @POST("orders/payment_status")
    @FormUrlEncoded
    Call<PaymentStatus> changePaymentStatus(@Header("lang") String language,
                                            @Header("Authorization") String token,
                                            @Field("status") int status,
                                            @Field("order_id") int orderId,
                                            @Field("type") String type);



    ///// TODO SALON

    // TODO Convert to Salon
    @POST("user/transferSalon")
    @FormUrlEncoded
    Call<ConvertToSalon> convertToSalon(@Header("lang") String language,
                                        @Header("Authorization") String token,
                                        @Field("full_name") String fullName,
                                        @Field("commercail_name") String commercailName,
                                        @Field("cat_id") Integer categoryid,
                                        @Field("type_business") String typeBusiness,
                                        @Field("mobile_number") String mobileNumber,
                                        @Field("whats_number") String whatsNumber,
                                        @Field("address_id") Integer addressId);

    // TODO SALON
    @POST("user/profile")
    Call<Profile> getProfile(@Header("lang") String language,
                             @Header("Authorization") String token);

    @POST("user/update")
    @Multipart
    Call<UpdateProfile> updateSalonProfile(@Header("lang") String language,
                                           @Header("Authorization") String token,
                                           @Part List<MultipartBody.Part> images,
                                           @Part("first_name") RequestBody firstName,
                                           @Part("last_name") RequestBody lastName,
                                           @Part("email") RequestBody email,
                                           @Part("commercail_name_ar") RequestBody commercailNameAr,
                                           @Part("commercail_name_en") RequestBody commercailNameEn,
                                           @Part("mobile_number") RequestBody mobileNumber,
                                           @Part("date_of_birth") RequestBody dateOfBirth,
                                           @Part("description_ar") RequestBody descriptionAr,
                                           @Part("description_en") RequestBody descriptionEn,
                                           @Part("home") RequestBody home);

    // TODO Empolyee
    @POST("vendor/show/employee")
    Call<Employee> getSalonEmployee(@Header("lang") String language,
                                    @Header("Authorization") String token);

    // TODO
    @POST("vendor/filter")
    @FormUrlEncoded
    Call<ManagementSalon> getManagementSalon(@Header("lang") String language,
                                             @Header("Authorization") String token,
                                             @Field("upcoming") String upcoming,
                                             @Field("show") String show,
                                             @Field("employee_id") Integer employeeId);

    // TODO Salon Service
    @POST("vendor/cat_vendor/index")
    Call<SalonServices> getSalonServices(@Header("lang") String language,
                                         @Header("Authorization") String token);

    // TODO Save Service
    @POST("vendor/cat_vendor")
    @FormUrlEncoded
    Call<SaveService> saveService(@Header("lang") String language,
                                  @Header("Authorization") String token,
                                  @Field("cat_id") Integer catId,
                                  @Field("time") int time,
                                  @Field("price") int price);

    @POST("vendor/cat_vendor/delete")
    @FormUrlEncoded
    Call<DeleteSalonService> deleteSalonService(@Header("lang") String language,
                                                @Header("Authorization") String token,
                                                @Field("cat_id") Integer catId);

    // TODO SALES
    @POST("vendor/sales")
    @FormUrlEncoded
    Call<Sales> getSales(@Header("lang") String language,
                         @Header("Authorization") String token,
                         @Field("id") Integer empId,
                         @Field("date") String date);

    // TODO WORK HOURS
    @POST("vendor/work_hours/index")
    Call<WorkHour> getWorkHour(@Header("lang") String language,
                               @Header("Authorization") String token);

    // TODO SAVE WORK HOURS
    @POST("vendor/work_hours")
    @FormUrlEncoded
    Call<SaveWorkHours> saveWorkHours(@Header("lang") String language,
                                      @Header("Authorization") String token,
                                      @FieldMap Map<String, Object> fields);

    // TODO Type ==> appointments / dates
    @POST("vendor/order/getOrders")
    @FormUrlEncoded
    Call<Orders> getOrders(@Header("lang") String language,
                           @Header("Authorization") String token,
                           @Field("employee_id") int employeeId,
                           @Field("type") String type);


    // TODO  تم الحجز بنجاح 1 / 2 تم الحضور / 3 موعد مضى
    @POST("orders/update_status")
    @FormUrlEncoded
    Call<UpdateOrderStatus> updateOrderStatus(@Header("lang") String language,
                                              @Header("Authorization") String token,
                                              @Field("order_id") int orderId,
                                              @Field("status") int status);
}
