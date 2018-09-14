package kz.greetgo.sandbox.db.test.dao;

import kz.greetgo.sandbox.controller.model.model.GenderType;
import kz.greetgo.sandbox.db.register_impl.model.SamplePerson;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SampleTestDao {

	List<SamplePerson> getAllSamplePerson();

	@Delete("delete from sample_person")
	void deleteAll();


	@Select("select * from sample_person where id = #{id}")
	SamplePerson getSamplePersonById(@Param("id") String id);

	@Insert("insert into sample_person(id, name, gender) values(#{id}, #{name}, #{gender})")
	void createSamplePerson(@Param("id") String id, @Param("name") String name, @Param("gender") GenderType gender);
}
