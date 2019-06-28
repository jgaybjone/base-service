package cn.jgayb.dao.utils.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * @param <T> entity class
 * @param <I> key class
 * @since 1.8
 */
public interface BaseService<T, I> {

    /**
     * get jpa repository
     *
     * @return jpaRepository
     */
    JpaRepository<T, I> getRepository();

    /**
     * 消费函数中同时存在crud时，子类需要重写并使用事物注解
     * 注解{@link Transactional}在强制cglib动态代理的时候不起作用
     *
     * @param consumer 消费函数
     */
    @Transactional
    default void crudAndConsumer(Consumer<JpaRepository<T, I>> consumer) {
        consumer.accept(getRepository());
    }

    /**
     * 消费函数中同时存在crud时，子类需要重写并使用事物注解
     * 注解{@link Transactional}在强制cglib动态代理的时候不起作用
     *
     * @param function 应用函数
     */
    @Transactional
    default <E> E crudAndFunction(Function<JpaRepository<T, I>, E> function) {
        return function.apply(getRepository());
    }

    default List<T> findAll() {
        return getRepository().findAll();
    }

    default Optional<T> findById(I id) {
        return getRepository().findById(id);
    }

    default List<T> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    default List<T> findAllById(Iterable<I> ids) {
        return getRepository().findAllById(ids);
    }

    default <S extends T> List<S> saveAll(Iterable<S> entities) {
        return getRepository().saveAll(entities);
    }

    default Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    default <S extends T> S save(S entity) {
        return getRepository().save(entity);
    }
}
