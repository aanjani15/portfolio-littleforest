package com.fintecho.littleforest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fintecho.littleforest.vo.RandomTreeVO;

@Mapper
public interface RandomTreeMapper {
RandomTreeVO randomCount(@Param("growing_Tree_Id") int growing_Tree_Id);
}
