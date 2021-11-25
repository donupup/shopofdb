package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.dto.EditDTO;
import com.example.demo.model.entity.Good;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface GoodMapper extends BaseMapper<Good> {

    int outGood(@Param("name") String name, @Param("num") int num, @Param("good_id") String good_id, @Param("bio") String bio, @Param("is_deleted") int is_del,@Param("date") Date date);
    int inGood(@Param("name") String name, @Param("num") int num, @Param("good_id") String good_id, @Param("bio") String bio, @Param("date") Date date);

}
