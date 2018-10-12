package kz.greetgo.sandbox.controller.model.report_models;

import java.util.Date;

public class ClientReportFootData {
    public String username;
    public Date generateTime;

    public ClientReportFootData(String username, Date generateTime) {
        this.username = username;
        this.generateTime = generateTime;
    }
}
