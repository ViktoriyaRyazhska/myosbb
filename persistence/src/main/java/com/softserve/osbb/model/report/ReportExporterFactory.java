package com.softserve.osbb.model.report;

import com.softserve.osbb.model.report.state.ReportExporterCreator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nazar.dovhyy on 29.07.2016.
 */
public final class ReportExporterFactory {

    private static Map<String, ReportExporter> reports = new HashMap<>();
    private static ReportExporterCreator reportExporterCreator = new ReportExporterCreator();

    public static ReportExporter generate(String type) {
        ReportExporter reportExporter = reports.get(type);

        if (reportExporter == null) {
            ReportExporter reportExporterByType = reportExporterCreator.createByType(type);
            reports.put(type, reportExporterByType);
            return reports.get(type);
        }

        return reportExporter;

    }
}
