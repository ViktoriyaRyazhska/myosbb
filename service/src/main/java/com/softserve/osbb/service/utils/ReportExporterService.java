package com.softserve.osbb.service.utils;

import com.softserve.osbb.model.report.ReportExporter;
import com.softserve.osbb.model.report.ReportExporterFactory;
import com.softserve.osbb.utils.Constants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by nazar.dovhyy on 29.07.2016.
 */
@Service
class ReportExporterService implements ReportExporterServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(ReportExporterService.class);

    @Autowired
    private FileDirectoryConfig fileDirectoryConfig;

    @Override
    public String export(String type, JasperPrint jp, HttpServletResponse response, ByteArrayOutputStream baos) throws IOException {
        exportToOutputStream(type, jp, response, baos);
        return exportToFileSystem(jp, type, fileDirectoryConfig.getOutputFileDirectory(Constants.REPORTS_DIR_NAME));
    }

    private HttpServletResponse exportToOutputStream(String type, JasperPrint jp, HttpServletResponse response, ByteArrayOutputStream baos) {
        ReportExporter reportExporter = ReportExporterFactory.generate(type);
        try {
            if (reportExporter != null) {
                reportExporter.exportToOutputStream(jp, baos);
                logger.info("exporting report output stream in " + type);
                buildHttpServletResponseMessage(response, baos, reportExporter.getFileName());
            }
        } catch (JRException e) {
            logger.error("failed to save report in " + type);
            throw new RuntimeException(e);
        }

        return response;
    }

    private String exportToFileSystem(JasperPrint jp, String type, String outputDir) {
        String destFileName = null;
        ReportExporter reportExporter = ReportExporterFactory.generate(type);
        if (reportExporter != null) {
            try {
                destFileName = reportExporter.exportToFile(jp, outputDir);
                logger.info("exporting report to file in " + type);
            } catch (JRException e) {
                logger.error("failed to save report to file in " + type);
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("reportExporter wasn't generated due to wrong type: " + type);
        }

        return destFileName;
    }


    private void buildHttpServletResponseMessage(HttpServletResponse response, ByteArrayOutputStream baos, String fileName) {
        response.setHeader("Content-disposition", ", inline; fileName= " + fileName);
        response.setContentType(HttpResponseMessageBuilder.returnContentTypeOf(fileName));
        response.setContentLength(baos.size());
    }


}
