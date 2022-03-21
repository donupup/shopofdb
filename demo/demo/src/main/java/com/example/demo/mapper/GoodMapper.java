package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.dto.EditDTO;
import com.example.demo.model.entity.Good;
import com.example.demo.model.vo.InGood;
import com.example.demo.model.vo.OutGood;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GoodMapper extends BaseMapper<Good> {

    int outGood(@Param("name") String name, @Param("num") int num, @Param("good_id") String good_id, @Param("bio") String bio, @Param("is_deleted") int is_del,@Param("date") Date date);
    int inGood(@Param("name") String name, @Param("num") int num, @Param("good_id") String good_id, @Param("bio") String bio, @Param("date") Date date,@Param("inPrice") int inPrice);
    int sellGoodToTable(@Param("name") String name, @Param("num") int num, @Param("good_id") String good_id, @Param("in") int in,@Param("out") int out, @Param("date") Date date);

    @Select("select * from bms_good_in")
    List<InGood>  getInList();

    @Select("select * from bms_good_out")
    List<OutGood>  getOutList();
}
