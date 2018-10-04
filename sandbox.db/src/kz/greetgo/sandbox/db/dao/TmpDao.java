package kz.greetgo.sandbox.db.dao;

import kz.greetgo.sandbox.db.model.TmpClient;
import org.apache.ibatis.annotations.Select;

public interface TmpDao {

	@Select("select client_id, account_number from  tmp_client_transaction where client_id notnull")
	TmpClient selectTmp();
}
