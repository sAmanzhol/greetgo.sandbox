package kz.greetgo.sandbox.controller.register.model;


import java.util.Comparator;
import java.util.Date;

public class Client  {
    public int id ;
    private static int counter =0;
    public String firstname;
    public String lastname;
    public String patronymic;
    public String gender;
    public String dateOfBirth;
    public String character;

    public static int getCounter() {
        return counter;
    }

    public static void setCounter() {
        Client.counter--;
    }

    public int totalAccountBalance;
    public int maximumBalance;
    public int minimumBalance;
    public Address addressOfRegistration;
    public Address addressOfResidence;
    public Phone phone;

    public Client() {
        this.id = counter++;
    }

    // теперь собственно реализуем интерфейс Comparator, для сортировки по названию
    public static class SortedByFirstname implements Comparator<Client> {

        public int compare(Client obj1, Client obj2) {

            String str1 = obj1.getFirstname();
            String str2 = obj2.getFirstname();

            return str1.compareTo(str2);
        }
    }
    // теперь собственно реализуем интерфейс Comparator, для сортировки по характеру
    public static class SortedByCharacter implements Comparator<Client> {

        public int compare(Client obj1, Client obj2) {

            String str1 = obj1.character;
            String str2 = obj2.getCharacter();

            return str1.compareTo(str2);
        }
    }


    // теперь собственно реализуем интерфейс Comparator, для сортировки по характеру
    public static class SortedBydateOfBirth implements Comparator<Client> {

        public int compare(Client obj1, Client obj2) {

            String str1 = obj1.dateOfBirth;
            String str2 = obj2.dateOfBirth;

            return str1.compareTo(str2);
        }
    }


    // а так же реализуем интерфейс Comparator, для сортировки по TotalAccountBalance
    public static class SortedByTotalAccountBalance implements Comparator<Client> {

        public int compare(Client obj1, Client obj2) {

            double price1 = obj1.getTotalAccountBalance();
            double price2 = obj2.getTotalAccountBalance();

            if(price1 > price2) {
                return 1;
            }
            else if(price1 < price2) {
                return -1;
            }
            else {
                return 0;
            }
        }
    }

    // а так же реализуем интерфейс Comparator, для сортировки по MaximumBalance
    public static class SortedByMaximumBalance implements Comparator<Client> {

        public int compare(Client obj1, Client obj2) {

            double price1 = obj1.getMaximumBalance();
            double price2 = obj2.getMaximumBalance();

            if(price1 > price2) {
                return 1;
            }
            else if(price1 < price2) {
                return -1;
            }
            else {
                return 0;
            }
        }
    }

    // а так же реализуем интерфейс Comparator, для сортировки по MinimumBalance
    public static class SortedByMinimumBalance implements Comparator<Client> {

        public int compare(Client obj1, Client obj2) {

            double price1 = obj1.getMinimumBalance();
            double price2 = obj2.getMinimumBalance();

            if(price1 > price2) {
                return 1;
            }
            else if(price1 < price2) {
                return -1;
            }
            else {
                return 0;
            }
        }
    }

    public int getTotalAccountBalance() {
        return totalAccountBalance;
    }

    public void setTotalAccountBalance(int totalAccountBalance) {
        this.totalAccountBalance = totalAccountBalance;
    }

    public int getMaximumBalance() {
        return maximumBalance;
    }

    public void setMaximumBalance(int maximumBalance) {
        this.maximumBalance = maximumBalance;
    }

    public int getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(int minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Address getAddressOfResidence() {
        return addressOfResidence;
    }

    public void setAddressOfResidence() {
        this.addressOfResidence = addressOfResidence;
    }

    public Address getAddressOfRegistration() {
        return addressOfRegistration;
    }

    public void setAddressOfRegistration(Address addressOfRegistration) {
        this.addressOfRegistration = addressOfRegistration;
    }

    @Override
    public String toString() {
        return "Client{" +
          "id=" + id +
          ", firstname='" + firstname + '\'' +
          ", lastname='" + lastname + '\'' +
          ", patronymic='" + patronymic + '\'' +
          ", gender='" + gender + '\'' +
          ", dateOfBirth='" + dateOfBirth + '\'' +
          ", character='" + character + '\'' +
          ", totalAccountBalance=" + totalAccountBalance +
          ", maximumBalance=" + maximumBalance +
          ", minimumBalance=" + minimumBalance +
          ", addressOfRegistration=" + addressOfRegistration +
          ", addressOfResidence=" + addressOfResidence +
          ", phone=" + phone +
          '}';
    }
}




