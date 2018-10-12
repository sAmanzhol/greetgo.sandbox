package kz.greetgo.sandbox.controller.model.report_models;

import java.sql.Timestamp;


public class ClientReportRow {


    public Long id;
    public String name;
    public String surname;
    public String patronymic;
    public String charmName;
    public Long age;
    public double maxbal;
    public double minbal;
    public double sumbal;

    public ClientReportRow(Long id, String name, String surname, String patronymic,
                           String charmName, Long age,
                           double maxbal, double minbal, double sumbal)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.charmName = charmName;
        this.age = age;
        this.maxbal = maxbal;
        this.minbal = minbal;
        this.sumbal = sumbal;
    }

    @Override
    public String toString() {
        return "ClientReportRow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", charmName='" + charmName + '\'' +
                ", age=" + age +
                ", maxbal=" + maxbal +
                ", minbal=" + minbal +
                ", sumbal=" + sumbal +
                '}';
    }
}
