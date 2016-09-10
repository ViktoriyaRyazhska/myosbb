package com.softserve.osbb.model.report.state;

import com.softserve.osbb.model.report.PdfReportExporter;
import com.softserve.osbb.model.report.ReportExporter;
import com.softserve.osbb.utils.Constants;

/**
 * Created by nazar.dovhyy on 05.09.2016.
 */
public class PdfReportExporterTypeSelector implements ReportExporterTypeSelector {

    private ReportExporterCreator reportExporterCreator;

    public PdfReportExporterTypeSelector(ReportExporterCreator reportExporterCreator) {
        this.reportExporterCreator = reportExporterCreator;
    }

    @Override
    public ReportExporter createByType(String type) {
        if (!Constants.PDF_TYPE.equalsIgnoreCase(type)) {
            reportExporterCreator.setBaseReportExporterTypeSelector(reportExporterCreator.getXlsPdfReportExporterTypeSelector());
            return reportExporterCreator.createByType(type);
        }
        return new PdfReportExporter();
    }
}
