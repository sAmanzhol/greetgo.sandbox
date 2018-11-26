package kz.greetgo.sandbox.backend.test.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StarTestDao {
  @Select("select using_name from star")
  List<String> allStarNames();
}
