package cn.jgayb.dao.utils.tkmybatis;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Classname BaseService
 * Description
 * Date 2019-06-27 17:00
 * Created by Wang jun gang
 */
public interface BaseService<T> {

    <S extends BaseMapper<T>> S getMapper();

    /**
     * xml的命名空间
     */
    String getNamespace();

    /**
     * @return session工厂
     */
    SqlSessionFactory sessionFactory();

    /**
     * 消费函数中同时存在crud时，子类需要重写并使用事物注解
     *
     * @param consumer 消费函数
     */
    default <S extends BaseMapper<T>> void crueAndConsumer(Consumer<S> consumer) {
        consumer.accept(getMapper());
    }

    /**
     * 消费函数中同时存在crud时，子类需要重写并使用事物注解
     *
     * @param function 应用函数
     */
    default <E, S extends BaseMapper<T>> E crueAndFunction(Function<S, E> function) {
        return function.apply(getMapper());
    }

    /**
     * 批量插入
     *
     * @param entities 插入实体集合
     */
    default void batchInsert(List<T> entities) {
        try (final SqlSession session = sessionFactory().openSession(ExecutorType.BATCH)) {
            entities.forEach(e -> session.insert(getNamespace() + ".insertSelective", e));
            session.commit();
        }
    }

    /**
     * 批量更新
     *
     * @param entities 实体集合
     */
    default void batchUpdate(List<T> entities) {

        try (final SqlSession session = sessionFactory().openSession(ExecutorType.BATCH)) {
            entities.forEach(e -> session.update(getNamespace() + ".updateByPrimaryKeySelective", e));
            session.commit();
        }
    }

    /**
     * @param key 主键
     * @return entity
     */
    default T selectByKey(Object key) {
        return getMapper().selectByPrimaryKey(key);
    }

    default int insert(T entity) {
        return getMapper().insertSelective(entity);
    }

    default int insertFull(T entity) {
        return getMapper().insert(entity);
    }

    default int insertList(List<T> collection) {
        return getMapper().insertList(collection);
    }

    default int updateByPrimaryKeySelective(T entity) {
        return getMapper().updateByPrimaryKeySelective(entity);
    }

    default int updateAll(T entity) {
        return getMapper().updateByPrimaryKey(entity);
    }

    default T selectOne(T entity) {
        return getMapper().selectOne(entity);
    }

    default List<T> select(T entity) {
        return getMapper().select(entity);
    }

    default List<T> selectAll() {
        return getMapper().selectAll();
    }

    default List<T> selectByExample(Example example) {
        return getMapper().selectByExample(example);
    }

    default int selectCountByExample(Example example) {
        return getMapper().selectCountByExample(example);
    }

    default boolean existsWithPrimaryKey(Object k) {
        return getMapper().existsWithPrimaryKey(k);
    }

    default int updateByExampleSelective(T record, Object example) {
        return getMapper().updateByExampleSelective(record, example);
    }

}