package com.softserve.osbb.model.report.state;

import com.softserve.osbb.model.report.ReportExporter;

/**
 * Created by nazar.dovhyy on 05.09.2016.
 */
public class ReportExporterCreator implements Creator<ReportExporter> {

    private ReportExporterTypeSelector baseReportExporterTypeSelector;
    private ReportExporterTypeSelector pdfPdfReportExporterTypeSelector;
    private ReportExporterTypeSelector xlsPdfReportExporterTypeSelector;
    private ReportExporterTypeSelector csvPdfReportExporterTypeSelector;

    public ReportExporterCreator(){
        pdfPdfReportExporterTypeSelector = new PdfReportExporterTypeSelector(this);
        xlsPdfReportExporterTypeSelector = new XlsReportExporterTypeSelector(this);
        csvPdfReportExporterTypeSelector = new CsvReportExporterTypeSelector(this);
        baseReportExporterTypeSelector = pdfPdfReportExporterTypeSelector;

    }

    @Override
    public ReportExporter createByType(String type) {
        return baseReportExporterTypeSelector.createByType(type);
    }


    public ReportExporterTypeSelector getBaseReportExporterTypeSelector() {
        return baseReportExporterTypeSelector;
    }

    public void setBaseReportExporterTypeSelector(ReportExporterTypeSelector baseReportExporterTypeSelector) {
        this.baseReportExporterTypeSelector = baseReportExporterTypeSelector;
    }

    public ReportExporterTypeSelector getPdfPdfReportExporterTypeSelector() {
        return pdfPdfReportExporterTypeSelector;
    }

    public void setPdfPdfReportExporterTypeSelector(ReportExporterTypeSelector pdfPdfReportExporterTypeSelector) {
        this.pdfPdfReportExporterTypeSelector = pdfPdfReportExporterTypeSelector;
    }

    public ReportExporterTypeSelector getXlsPdfReportExporterTypeSelector() {
        return xlsPdfReportExporterTypeSelector;
    }

    public void setXlsPdfReportExporterTypeSelector(ReportExporterTypeSelector xlsPdfReportExporterTypeSelector) {
        this.xlsPdfReportExporterTypeSelector = xlsPdfReportExporterTypeSelector;
    }

    public ReportExporterTypeSelector getCsvPdfReportSelector() {
        return csvPdfReportExporterTypeSelector;
    }

    public void setCsvPdfReportSelector(ReportExporterTypeSelector csvPdfReportExporterTypeSelector) {
        this.csvPdfReportExporterTypeSelector = csvPdfReportExporterTypeSelector;
    }
}
