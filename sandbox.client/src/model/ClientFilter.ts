export class ClientFilter {
  public name: string;
  public surname: string;
  public patronymic: string;
  public offset: number;
  public limit: number;


  constructor(name: string, surname: string, patronymic: string, offset: number, limit: number) {
    this.name = name;
    this.surname = surname;
    this.patronymic = patronymic;
    this.offset = offset;
    this.limit = limit;
  }
}
