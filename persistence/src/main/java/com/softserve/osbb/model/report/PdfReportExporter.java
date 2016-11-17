package com.softserve.osbb.model.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by nazar.dovhyy on 29.07.2016.
 */
public class PdfReportExporter extends ReportExporter {

    public PdfReportExporter() {
        super("pdf");        
    }

    @Override
    public void exportToOutputStream(JasperPrint jp, ByteArrayOutputStream baos) throws JRException {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        exporter.exportReport();
    }

    @Override
    public String exportToFile(JasperPrint jasperPrint, String outputDir) throws JRException {
        final String fileName = getFileName();
        String outputFileName = outputDir + File.separator + fileName;
        
        if (jasperPrint != null) {
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName);
            exporter.exportReport();
        }
        
        return outputFileName;
    }

}
