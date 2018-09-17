package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.GenderType;
import kz.greetgo.sandbox.db.register_impl.model.SamplePerson;
import kz.greetgo.sandbox.db.test.dao.SampleTestDao;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;
import java.util.List;
import static org.fest.assertions.api.Assertions.assertThat;


public class SampleRegisterImplTest extends ParentTestNg {

	//daoTest
	//regster test
	public BeanGetter<SampleRegisterImpl> register;

	public BeanGetter<SampleTestDao> sampleTestDao;

	@Test
	public void testSavePerson_add() {
		sampleTestDao.get().deleteAll();

		String name = RND.str(10);
		SamplePerson person  = new SamplePerson();
		person.name = name;
		person.surname = RND.intStr(10);
		person.gender = GenderType.MALE;

		//
		//
		register.get().savePerson(person);
		//
		//


		List<SamplePerson> ret = sampleTestDao.get().getAllSamplePerson();

		assertThat(ret).hasSize(1);

		assertThat(ret.get(0).id).isNotNull();
		assertThat(ret.get(0).name).isEqualTo(person.name);
		assertThat(ret.get(0).surname).isEqualTo(person.surname);
		assertThat(ret.get(0).gender).isEqualTo(person.gender);
	}

	@Test
	public void testSavePerson_edit() {
		sampleTestDao.get().deleteAll();

		String id = RND.str(10);

		sampleTestDao.get().createSamplePerson(id, RND.str(10), GenderType.MALE);

		SamplePerson person  = new SamplePerson();
		person.id = id;
		person.name = RND.str(10);
		person.surname = RND.intStr(10);
		person.gender = GenderType.FEMALE;

		//
		//
		register.get().savePerson(person);
		//
		//

		SamplePerson ret = sampleTestDao.get().getSamplePersonById(id);

		assertThat(ret).isNotNull();
		assertThat(ret.id).isEqualTo(person.id);
		assertThat(ret.name).isEqualTo(person.name);
		assertThat(ret.surname).isEqualTo(person.surname);
		assertThat(ret.gender).isEqualTo(person.gender);
	}
}