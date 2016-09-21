package com.softserve.osbb.utils;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by nazar.dovhyy on 20.08.2016.
 */

public class Constants {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(YYYY_MM_DD);
    public static final String REPORTS_DIR_NAME = "reports";
    public static final int TOTAL_APARTMENT_NUMBER = 100;
    public static final String PDF_TYPE = "pdf";
    public static final String XLS_TYPE = "xls";
    public static final String CSV_TYPE = "csv";
    public static final String FILE_UPLOAD_PATH = File.separator + "var" + File.separator + "www" +
            File.separator + "html" + File.separator + "static" + File.separator;
    public static final String FILE_DOWNLOAD_PATH = "http://192.168.195.250:80/" + "static" + File.separator ;
//    public static final String FILE_DOWNLOAD_PATH = "http://localhost:80/" + "static" + File.separator ;
}
