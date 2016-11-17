package com.softserve.osbb.model.report;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nazar.dovhyy on 29.07.2016.
 */
public final class ReportExporterFactory {

    private static Map<String, ReportExporter> exporters = new HashMap<>();
    
    /**
     * Generates an instance of ReportExporter of appropriate type based on input, which represents 
     * file extension (pdf, xls or csv).  
     * @param type type of ReportExported to be generated
     * @return instance of proper ReportExporter (pdf, xls or csv)
     */
    public static ReportExporter generate(String type) {
        ReportExporter exporter = exporters.get(type);
        
        if (exporter == null) {
            switch (type.toUpperCase()) {
            case "PDF":
                exporter = new PdfReportExporter();
                break;
            case "XLS":
                exporter = new XlsReportExporter();
                break;
            case "CSV":
                exporter = new CsvReportExporter();
                break;
            default:
                exporter = new PdfReportExporter();  
            }
            
            exporters.put(type, exporter);
        }
        
        return exporter;
    }
    
}
