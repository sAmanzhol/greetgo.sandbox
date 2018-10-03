package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.register.CharmRegister;
import kz.greetgo.sandbox.register.beans.all.IdGenerator;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class CharmsImpTest extends ParentTestNg {
    IdGenerator idGenerator = new IdGenerator();
    public BeanGetter<CharmRegister> charmRegister;

    @Test
    public void listCharm(){
        List<Charm> charmList = null;
        //
        //
        charmList = charmRegister.get().list(); //
        //
        //
        assertThat(charmList).isNotNull();
        assertThat(charmList).isNotEmpty();
        assertThat(charmList.get(0).name).isNotNull();
    }


    @Test
    public void insertCharm(){
        String name = RND.str(5);
        String descr = RND.str(20);
        Double energy = 82d;
        Charm charm = new Charm(null,name,descr,energy);
        //
        //
        Long insRetId = charmRegister.get().insert(charm); // Возвращенный айди после insert
        Charm retCharm = charmRegister.get().getById(insRetId); // Достаем, вставленый объект
        //
        //
        assertThat(retCharm).isNotNull();
        assertThat(retCharm.name).isEqualTo(retCharm.name);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void insertCharmNull(){
        Charm charm = null;
        //
        //
        Long insRetId = charmRegister.get().insert(charm); // Возвращенный айди после insert
        Charm retCharm = charmRegister.get().getById(insRetId); // Достаем, вставленый объект
        //
        //
        assertThat(retCharm).isNotNull();
        assertThat(retCharm.name).isEqualTo(retCharm.name);
    }


    @Test(expectedExceptions = NullPointerException.class)
    public void getCharmById_null(){
        Long id = null;
        //
        //
        insertCharm();
        Charm charm = charmRegister.get().getById(id);
        //
        //
        assertThat(charm).isNotNull();
    }

    @Test
    public void getCharmByFakeId(){
        Long id = -5l;
        //
        //
        insertCharm();
        Charm charm = charmRegister.get().getById(id);
        //
        //
        assertThat(charm).isNull();
    }

    @Test
    public void getCharmById(){
        Long id = 1L;
        //
        //
        insertCharm();
        Charm charm = charmRegister.get().getById(id);
        //
        //
        assertThat(charm).isNotNull();
    }

}
