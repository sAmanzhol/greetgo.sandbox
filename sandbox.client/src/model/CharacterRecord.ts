export class CharacterRecord {
  public id: string;
  public value: string;

  public static create(a: any): CharacterRecord {
    const ret = new CharacterRecord();
    ret.assign(a);
    return ret;
  }

  assign(a: any) {
    this.id = a.id;
    this.value = a.value;
  }
}
