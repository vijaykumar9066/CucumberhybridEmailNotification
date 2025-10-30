package utiles;

import java.text.SimpleDateFormat;
import java.util.Date;

public class commonutils {

    // ✅ Wait Constants
    public static final int IMPLICIT_WAIT_TIME = 10;
    public static final int PAGE_LOAD_TIME = 15;
    public static final int EXPLICIT_WAIT_BASIC_TIME = 20;

    // ✅ Generate email with timestamp
    public static String getEmailwithTimeStamp() {
        return "vijaykumar_" + new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date()) + "@gmail.com";
    }

    // ✅ Current Timestamp
    public static String getCurrentTimestamp() {
        return new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date());
    }
}

