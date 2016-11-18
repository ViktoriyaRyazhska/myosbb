package com.softserve.osbb.model.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by nazar.dovhyy on 29.07.2016.
 */
public class CsvReportExporter extends ReportExporter {

    public CsvReportExporter() {
        super("csv");
    }

    @Override
    public void exportToOutputStream(JasperPrint jp, ByteArrayOutputStream baos) throws JRException {
        JRCsvExporter exporter = new JRCsvExporter();
        exporter.setParameter(JRCsvExporterParameter.JASPER_PRINT, jp);
        exporter.setParameter(JRCsvExporterParameter.OUTPUT_STREAM, baos);
        exporter.exportReport();
    }

    @Override
    public String exportToFile(JasperPrint jasperPrint, String outputDir) throws JRException {
        final String fileName = getFileName();
        JRCsvExporter exporter = new JRCsvExporter();
        String destFileName = outputDir + File.separator + fileName;
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFileName);
        exporter.exportReport();

        return fileName;
    }
    
}
