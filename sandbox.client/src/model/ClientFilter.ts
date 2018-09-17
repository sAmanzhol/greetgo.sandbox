export class ClientFilter {
  public name: string;
  public surname: string;
  public patronymic: string;
  public offset: number;
  public limit: number;
  public columnName: string;
  public isAsc: boolean;


  constructor(name: string, surname: string, patronymic: string, offset: number, limit: number, columnName: string, isAsc: boolean) {
    this.name = name;
    this.surname = surname;
    this.patronymic = patronymic;
    this.offset = offset;
    this.limit = limit;
    this.columnName = columnName;
    this.isAsc = isAsc;
  }
}
