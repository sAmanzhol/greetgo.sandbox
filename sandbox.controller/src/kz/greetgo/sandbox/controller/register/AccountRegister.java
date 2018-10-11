package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.Account;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AccountRegister {

    Long insert(Account address);

    Account getById(Long id);

    List<Account> getByClientId(Long client_id);

    Account update(Account address);

    void delete(Long id);

    void deleteByClientId(Long client_id);
}
