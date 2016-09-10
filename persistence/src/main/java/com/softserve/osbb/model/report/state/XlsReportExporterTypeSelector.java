package com.softserve.osbb.model.report.state;

import com.softserve.osbb.model.report.ReportExporter;
import com.softserve.osbb.model.report.XlsReportExporter;
import com.softserve.osbb.utils.Constants;

/**
 * Created by nazar.dovhyy on 05.09.2016.
 */
public class XlsReportExporterTypeSelector implements ReportExporterTypeSelector {
    private ReportExporterCreator reportExporterCreator;

    public XlsReportExporterTypeSelector(ReportExporterCreator reportExporterCreator) {
        this.reportExporterCreator = reportExporterCreator;
    }

    @Override
    public ReportExporter createByType(String type) {
        if (!Constants.XLS_TYPE.equalsIgnoreCase(type)) {
            reportExporterCreator.setBaseReportExporterTypeSelector(reportExporterCreator.getCsvPdfReportSelector());
            return reportExporterCreator.createByType(type);
        }
        return new XlsReportExporter();
    }
}
