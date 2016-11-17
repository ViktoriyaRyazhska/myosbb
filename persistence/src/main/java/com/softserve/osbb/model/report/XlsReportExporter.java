package com.softserve.osbb.model.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by nazar.dovhyy on 29.07.2016.
 */
public class XlsReportExporter extends ReportExporter {

    public XlsReportExporter() {
        super("xls");
    }

    @Override
    public void exportToOutputStream(JasperPrint jp, ByteArrayOutputStream baos) throws JRException {
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        exporter.exportReport();
    }

    //WTF?!? returning file name with huge side effect?
    @Override
    public String exportToFile(JasperPrint jasperPrint, String outputDir) throws JRException {
        final String fileName = getFileName();
        String destFileName = outputDir + File.separator + fileName;
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFileName);
        exporter.exportReport();
        
        return fileName;
    }
    
}
