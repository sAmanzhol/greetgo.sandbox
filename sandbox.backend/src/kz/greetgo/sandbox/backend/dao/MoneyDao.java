package kz.greetgo.sandbox.backend.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MoneyDao {

  @Insert("insert into money (id, room, amount) values (#{id}, #{room}, #{amount})")
  void insert(@Param("id") String id,
              @Param("room") String room,
              @Param("amount") long amount);

  @Select("select sum(amount) from money where room = #{room}")
  long amountSum(@Param("room") String room);

  @Select("select amount from money where id = #{id} for update")
  long getAmount(@Param("id") String id);

  @Select("update money set amount = #{amount} where id = #{id}")
  void setAmount(@Param("id") String id,
                 @Param("amount") long amount);
}
