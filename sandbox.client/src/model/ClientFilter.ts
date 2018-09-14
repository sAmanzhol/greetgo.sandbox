export class ClientFilter {
  public name: string;
  public surname: string;
  public patronymic: string;


  constructor(name: string, surname: string, patronymic: string) {
    this.name = name;
    this.surname = surname;
    this.patronymic = patronymic;
  }
}
