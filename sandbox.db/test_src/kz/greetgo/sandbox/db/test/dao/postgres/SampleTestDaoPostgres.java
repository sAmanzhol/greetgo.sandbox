package kz.greetgo.sandbox.db.test.dao.postgres;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.db.register_impl.model.SamplePerson;
import kz.greetgo.sandbox.db.test.dao.SampleTestDao;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Bean
public interface SampleTestDaoPostgres extends SampleTestDao {

	@Override
	@Select("select * from sample_person")
	List<SamplePerson> getAllSamplePerson();
}
