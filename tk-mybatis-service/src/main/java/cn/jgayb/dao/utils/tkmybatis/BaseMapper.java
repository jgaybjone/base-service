package cn.jgayb.dao.utils.tkmybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Classname BaseMapper
 * Description 所有mapper的父接口
 * Date 2019-06-27 16:58
 * Created by Wang jun gang
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
