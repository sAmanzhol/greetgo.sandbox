package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Phone;
import kz.greetgo.sandbox.controller.model.enums.PhoneType;
import kz.greetgo.sandbox.controller.register.PhoneRegister;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.apache.ibatis.exceptions.PersistenceException;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class PhoneImplTest extends ParentTestNg {
    public BeanGetter<PhoneRegister> phoneRegister;

    @Test
    public void listPhone(){
        List<Phone> phoneList = null;

        //
        //
        phoneList = phoneRegister.get().list();
        //
        //

        assertThat(phoneList).isNotNull();
        assertThat(phoneList).isNotEmpty();
    }

    @Test
    public void insertPhone(){
        String number = RND.intStr(8);
        PhoneType type = RND.someEnum(PhoneType.HOME,PhoneType.MOBILE,PhoneType.WORK);

        Phone newPhone = new Phone(null,1l,number,type,true);
        //
        //
        Long retId  = phoneRegister.get().insert(newPhone);
        Phone retPhone = phoneRegister.get().getById(retId);
        //
        //
        assertThat(retId).isNotNull();
        assertThat(retPhone).isNotNull();
        assertThat(retPhone.number).isEqualTo(newPhone.number);
        assertThat(retPhone.type).isEqualTo(newPhone.type);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void insertPhoneNull(){
        Phone newPhone = new Phone(null,null,null,null,false);
        //
        //
        Long retId  =  phoneRegister.get().insert(newPhone);
        Phone retPhone = phoneRegister.get().getById(retId);
        //
        //
        assertThat(retId).isNull();
        assertThat(retPhone).isNull();
    }

    @Test
    public void updatePhone(){
        Phone newPhone = new Phone(null,-1l,RND.intStr(8),PhoneType.HOME,true);
        //
        //
        Long retId  =  phoneRegister.get().insert(newPhone);
        Phone retPhone = phoneRegister.get().getById(retId);
        //
        //
        assertThat(retId).isNotNull();
        assertThat(retPhone).isNotNull();

        newPhone.id = retPhone.id;
        newPhone.number = RND.intStr(8);
        newPhone.type = RND.someEnum(PhoneType.HOME,PhoneType.MOBILE,PhoneType.WORK);
        //
        //
        newPhone = phoneRegister.get().update(newPhone);
        //
        //
        assertThat(newPhone).isNotNull();
        assertThat(newPhone.number).isNotEqualTo(retPhone.number);
        assertThat(newPhone.id).isEqualTo(retPhone.id);
    }



    @Test
    public void updatePhoneWithFakeID(){
        Phone newPhone = new Phone(1000l,-1l,RND.intStr(8),PhoneType.HOME,true);

        //
        //
        newPhone = phoneRegister.get().update(newPhone);
        //
        //
        assertThat(newPhone).isNull();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updatePhoneWithNull(){
        Phone newPhone = null;
        //
        //
        newPhone = phoneRegister.get().update(newPhone);
        //
        //
    }

    @Test
    public void updatePhoneWithNullId(){
        Phone newPhone = new Phone(null,-1l,RND.intStr(8),PhoneType.HOME,true);
        //
        //
        newPhone = phoneRegister.get().update(newPhone);
        //
        //
        assertThat(newPhone).isNull();
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void updatePhoneWithNullData(){
        Phone newPhone = new Phone(null,-1l,RND.intStr(8),PhoneType.HOME,true);
        //
        //
        Long retId  =  phoneRegister.get().insert(newPhone);
        Phone retPhone = phoneRegister.get().getById(retId);
        //
        //
        assertThat(retId).isNotNull();
        assertThat(retPhone).isNotNull();

        newPhone.id = retPhone.id;
        newPhone.number = null;
        newPhone.type = null;
        //
        //
        newPhone = phoneRegister.get().update(newPhone);
        //
        //
    }

    @Test
    public void getPhoneById(){
        String number = RND.intStr(8);
        PhoneType type = RND.someEnum(PhoneType.HOME,PhoneType.MOBILE,PhoneType.WORK);

        Phone newPhone = new Phone(null,-1l,number,type,true);
        //
        //
        Long retId  = phoneRegister.get().insert(newPhone);
        Phone retPhone = phoneRegister.get().getById(retId);
        //
        //
        assertThat(retPhone).isNotNull();
        assertThat(retPhone.number).isEqualTo(newPhone.number);
        assertThat(retPhone.type).isEqualTo(newPhone.type);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getPhoneId_null(){
        Long id = null;
        //
        //
        Phone retPhone = phoneRegister.get().getById(id);
        //
        //
        assertThat(retPhone).isNull();
    }

    @Test
    public void getPhoneByFakeId(){
        Long id = -1l;
        //
        //
        Phone retPhone = phoneRegister.get().getById(id);
        //
        //
        assertThat(retPhone).isNull();
    }
}
