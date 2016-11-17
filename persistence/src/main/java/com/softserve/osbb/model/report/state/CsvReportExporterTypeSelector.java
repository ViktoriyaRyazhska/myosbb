package com.softserve.osbb.model.report.state;

import com.softserve.osbb.model.report.CsvReportExporter;
import com.softserve.osbb.model.report.ReportExporter;
import com.softserve.osbb.utils.Constants;

/**
 * Created by nazar.dovhyy on 05.09.2016.
 */
public class CsvReportExporterTypeSelector implements ReportExporterTypeSelector {

    @Override
    public ReportExporter createByType(String type) {
        if (!Constants.CSV_TYPE.equalsIgnoreCase(type)) {
            throw new IllegalArgumentException("no ReportExporter object by return type was found");
        }
        return new CsvReportExporter();
    }
}
