package com.softserve.osbb.model.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * Created by nazar.dovhyy on 29.07.2016.
 */
public abstract class ReportExporter {

    private String fileExtension;    
    private String fileName;

    protected ReportExporter(String fileExtension) {
        this.fileExtension = fileExtension;
    }
    
    protected void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFileName() {
        if (fileName == null) {
            setFileName(buildDestinationFileName(getFileExtension()));
        }
        
        return fileName;
    }

    protected String getFileExtension() {
        return this.fileExtension;
    }

    private String buildDestinationFileName(String fileExtension) {
        StringBuilder stringBuilder = new StringBuilder();
        String randomFileName = UUID.randomUUID().toString();
        stringBuilder.append("report" + randomFileName).append(".").append(fileExtension);
        return stringBuilder.toString();
    }

    public abstract void exportToOutputStream(JasperPrint jp, ByteArrayOutputStream baos) throws JRException;

    public abstract String exportToFile(JasperPrint jasperPrint, String outputDir) throws JRException;

}
