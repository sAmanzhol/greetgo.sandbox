import {formatDate} from "@angular/common";
import {Address} from "./Address";
import {ClientRecord} from "./ClientRecord";

export class ClientDisplay {
  id;
  fio;
  charm_name;
  age;
  sumbal;
  maxbal;
  minbal;


  public static createFromClientRec(rec : ClientRecord) : ClientDisplay{
    const ret = new ClientDisplay();
      ret.id =rec.id;
      ret.fio =rec.surname + " " + rec.name + " " + rec.patronymic;
      ret.age =rec.getAge();
      ret.charm_name =rec.charm.name;
      ret.sumbal = rec.getTotalAccBal();
      ret.maxbal = rec.getMaxAccBal();
      ret.minbal = rec.getMinAccBal();
    return ret;
  }
}
