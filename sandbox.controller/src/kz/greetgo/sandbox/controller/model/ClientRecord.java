package kz.greetgo.sandbox.controller.model;


/**
 * Created by msultanova on 9/4/18.
 */
public class ClientRecord {
    public String fio;
    public CharacterType character;
    public int age;
    public int totalBalance;
    public int minBalance;
    public int maxBalance;
    public long clientId;

    public ClientRecord() {
    }

    public ClientRecord(String fio, CharacterType character, int age, int totalBalance, int minBalance, int maxBalance) {
        this.fio = fio;
        this.character = character;
        this.age = age;
        this.totalBalance = totalBalance;
        this.minBalance = minBalance;
        this.maxBalance = maxBalance;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setCharacter(CharacterType character) {
        this.character = character;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setTotalBalance(int totalBalance) {
        this.totalBalance = totalBalance;
    }

    public void setMinBalance(int minBalance) {
        this.minBalance = minBalance;
    }

    public void setMaxBalance(int maxBalance) {
        this.maxBalance = maxBalance;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }
}
