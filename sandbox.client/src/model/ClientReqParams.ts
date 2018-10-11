

export class ClientReqParams {
  nameFilterVal;
  surnameFilterVal;
  patronymicFilterVal;
  offset;
  limit;
  colName;
  order;

  constructor(nameFilterVal, surnameFilterVal, patronymicFilterVal, offset, limit, colName, order) {
    this.nameFilterVal = nameFilterVal;
    this.surnameFilterVal = surnameFilterVal;
    this.patronymicFilterVal = patronymicFilterVal;
    this.offset = offset;
    this.limit = limit;
    this.colName = colName;
    this.order = order;
  }
}


