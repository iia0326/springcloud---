package com.kangyi.mapper;

import com.kangyi.pojo.GeLi;
import com.kangyi.pojo.GeLiExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GeLiMapper {
    long countByExample(GeLiExample example);

    int deleteByExample(GeLiExample example);

    int deleteByPrimaryKey(Long geliId);

    int insert(GeLi record);

    int insertSelective(GeLi record);

    List<GeLi> selectByExample(GeLiExample example);

    GeLi selectByPrimaryKey(Long geliId);

    int updateByExampleSelective(@Param("record") GeLi record, @Param("example") GeLiExample example);

    int updateByExample(@Param("record") GeLi record, @Param("example") GeLiExample example);

    int updateByPrimaryKeySelective(GeLi record);

    int updateByPrimaryKey(GeLi record);

    int insertAndGetId(GeLi geLi);


    List<GeLi> selectByOrderStatusAndExample(GeLiExample geLiExample);
}