package cn.jgayb.dao.utils.mybatis;

import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Classname BaseService
 * Description
 * Date 2019-06-27 16:44
 * Created by Wang jun gang
 */
public interface BaseService<T> {


    /**
     * @param <S> 限定接口
     * @return IMapper 子接口
     */
    <S extends IMapper> S getMapper();

    /**
     * 消费函数中同时存在crud时，子类需要重写并使用事物注解
     * 注解{@link Transactional}在强制cglib动态代理的时候不起作用
     *
     * @param consumer 消费函数
     */
    @Transactional
    default <S extends IMapper<T>> void crudAndConsumer(Consumer<S> consumer) {
        consumer.accept(getMapper());
    }

    /**
     * 消费函数中同时存在crud时，子类需要重写并使用事物注解
     * 注解{@link Transactional}在强制cglib动态代理的时候不起作用
     *
     * @param function 应用函数
     */
    @Transactional
    default <E, S extends IMapper<T>> E crudAndFunction(Function<S, E> function) {
        return function.apply(getMapper());
    }
}
