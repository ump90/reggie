package com.itheima.reggie_take_out.common;

/**
 * @author UMP90
 * @date 2021/10/14
 */

public class BaseContext {
    private static final ThreadLocal<Long> threadLocal = new InheritableThreadLocal<>();

    public static void setId(Long id) {
        threadLocal.set(id);
    }

    public static Long getId() {
        return threadLocal.get();
    }

    public static void close() {
        threadLocal.remove();
    }
}
