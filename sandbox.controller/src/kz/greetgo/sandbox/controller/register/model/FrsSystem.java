package kz.greetgo.sandbox.controller.register.model;

public class FrsSystem {
	public String client_id;
	public String finished_at;
	public String registered_at;
	public String account_number;
	public String money;
	public String type;
	public String transaction_type;

	@Override
	public String toString() {

		return "FrsSystem{" +
			"client_id='" + client_id + '\'' +
			", finished_at='" + finished_at + '\'' +
			", registered_at='" + registered_at + '\'' +
			", account_number='" + account_number + '\'' +
			", money='" + money + '\'' +
			", type='" + type + '\'' +
			", transaction_type='" + transaction_type + '\'' +
			'}';
	}
	/*money
account_number
finished_at
type
transaction_type
registered_at
client_id
*/

}
