package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Account;
import kz.greetgo.sandbox.controller.register.AccountRegister;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

import static org.fest.assertions.api.Assertions.assertThat;

public class AccountImplTest extends ParentTestNg {
    public BeanGetter<AccountRegister> accountRegister;



    @Test
    public void getClientAccount(){
        List<Account> accountList = null;
        Long client_id = -1L;

        //
        //
        Long retAccountId =  accountRegister.get().insert(new Account(null,client_id,Double.valueOf(RND.intStr(4)),
                "KZ"+RND.str(16),new Timestamp(System.currentTimeMillis()),true));

        accountList = accountRegister.get().getByClientId(client_id);
        //
        //
        assertThat(accountList).isNotNull();
        assertThat(accountList).isNotEmpty();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getClientaccountWithNull(){
        List<Account> accountList = null;
        //
        //
        Long client_id = null;
        accountList = accountRegister.get().getByClientId(client_id);
        //
        //
    }

    @Test
    public void getClientaccountWithFakeId(){
        List<Account> accountList = null;
        //
        //
        Long client_id = -111l;
        accountList = accountRegister.get().getByClientId(client_id);
        //
        //
        assertThat(accountList).isEmpty();
    }


    @Test
    public void updateaccount(){

    }

    @Test
    public void getAccountById(){
        Long client_id = -1L;
        Account account = new Account(null,client_id,Double.valueOf(RND.intStr(4)),
                "KZ"+RND.str(16),new Timestamp(System.currentTimeMillis()),true);

        //
        //
        Long retAccountId =  accountRegister.get().insert(account);

        Account reAcc = accountRegister.get().getById(retAccountId);
        //
        //
        assertThat(retAccountId).isNotNull();
        assertThat(reAcc).isNotNull();
        assertThat(reAcc.registered_at).isEqualTo(account.registered_at);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getaccountId_null(){
        Long id = null;
        //
        //
        Account retaccount = accountRegister.get().getById(id);
        //
        //
        assertThat(retaccount).isNull();
    }

    @Test
    public void getaccountByFakeId(){
        Long id = -1L;
        //
        //
        Account retaccount = accountRegister.get().getById(id);
        //
        //
        assertThat(retaccount).isNull();
    }

    @Test
    public Long insertaccount(){
        Random rnd = new Random();

        Long client_id = rnd.nextLong();
        Account account = new Account(null,client_id,Double.valueOf(RND.intStr(4)),
                "KZ"+RND.str(16),new Timestamp(System.currentTimeMillis()),true);


        //
        //
        Long insRetId = accountRegister.get().insert(account);
        Account retaccount = accountRegister.get().getById(insRetId);
        //
        //
        assertThat(insRetId).isNotNull();
        assertThat(retaccount).isNotNull();
        assertThat(retaccount.number).isEqualTo(account.number);
        assertThat(retaccount.money).isEqualTo(account.money);
        assertThat(retaccount.registered_at).isEqualTo(account.registered_at);

        return client_id;
    }


    @Test(expectedExceptions = NullPointerException.class)
    public void insertaccountNull(){

        Account account = new Account(null,null,null,null,null,false);
        //
        //
        Long insRetId = accountRegister.get().insert(account);
        //
        //
        assertThat(insRetId).isNull();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void insertNullaccount(){
        Account account = null;
        //
        //
        Long insRetId = accountRegister.get().insert(account);
        //
        //
        assertThat(insRetId).isNull();
    }

    @Test
    public void insertAccountWithInjection(){

        Account account = new Account(null,-1L,Double.valueOf(RND.intStr(4)),
                "and (SELECT * from client_account)",new Timestamp(System.currentTimeMillis()),true);
        //
        //
        Long insRetId = accountRegister.get().insert(account);
        //
        //
        assertThat(insRetId).isNotNull();
    }

    @Test
    public void deleteaccount(){
        Random rnd = new Random();
        Long client_id = rnd.nextLong();
        Account account = new Account(null,client_id,Double.valueOf(RND.intStr(4)),
                "KZ"+RND.str(16),new Timestamp(System.currentTimeMillis()),true);
        //
        //
        Long insRetId = accountRegister.get().insert(account);
        Account retAccount = accountRegister.get().getById(insRetId);
        System.out.println(insRetId);
        //
        //
        assertThat(insRetId).isNotNull();
        assertThat(retAccount).isNotNull();

        accountRegister.get().delete(insRetId);
    }

}
