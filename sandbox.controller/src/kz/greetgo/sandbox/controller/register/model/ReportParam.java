package kz.greetgo.sandbox.controller.register.model;

import kz.greetgo.sandbox.controller.register.report.ReportType;

import java.io.OutputStream;
import java.util.Date;

public class ReportParam {
    public String username;
    public Date date;
    public ReportType type;
    public OutputStream out;

    public ReportParam(String username, Date date, ReportType reportType, OutputStream out) {
        this.username = username;
        this.date = date;
        this.type = reportType;
        this.out = out;
    }
}
