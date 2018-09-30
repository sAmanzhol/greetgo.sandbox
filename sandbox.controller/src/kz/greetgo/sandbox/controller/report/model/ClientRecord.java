package kz.greetgo.sandbox.controller.report.model;


import kz.greetgo.sandbox.controller.model.model.Charm;

import java.util.Date;

public class ClientRecord {
    public int id;
    public String firstname;
    public String lastname;
    public String patronymic;
    public String characterName;
    public Date dateOfBirth;
    public Integer totalAccountBalance;
    public Integer maximumBalance;
    public Integer minimumBalance;

    public ClientRecord(){
        this.firstname="";
        this.lastname="";
        this.patronymic="";
        this.characterName=new Charm().name;
        this.dateOfBirth=new Date();
        this.totalAccountBalance=0;
        this.maximumBalance=0;
        this.minimumBalance=0;
    }

    @Override
    public String toString() {

        return "ClientRecord{" +
          "id=" + id +
          ", firstname='" + firstname + '\'' +
          ", lastname='" + lastname + '\'' +
          ", patronymic='" + patronymic + '\'' +
          ", characterName='" + characterName + '\'' +
          ", dateOfBirth=" + dateOfBirth +
          ", totalAccountBalance=" + totalAccountBalance +
          ", maximumBalance=" + maximumBalance +
          ", minimumBalance=" + minimumBalance +
          '}';    }
}
