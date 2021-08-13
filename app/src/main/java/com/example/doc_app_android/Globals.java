package com.example.doc_app_android;

public class Globals {
    public static String serverURL = "https://maivrikdoc.herokuapp.com/api/";

    public static String loginURL = serverURL + "login";
    public static String patientRegister = serverURL + "register/patient";
    public static String docRegister = serverURL + "register/doctor";
    public static String docFilter = serverURL + "problems";
    public static String profileDoctor = serverURL + "doctor/";
    public static String profilePatient = serverURL + "patient/";
    public static String editGenDetails = serverURL + "user/";
    public static String doctorHomeScreenPatientList = serverURL + "patients?problem=";
    public static String checkUpHistory = serverURL + "reports/";
    public static String xray = serverURL + "xrays/";
    public static String prescription = serverURL + "prescriptions/";
    public static String appointment = serverURL + "getappointment";
    public static String docList = serverURL + "doctors";
    public static String report = serverURL + "report/";
    public static String addNewXray = serverURL + "xray/add";
    public static String addNewReport = serverURL + "report/add";

}
