package cn.boyce.util;


import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Yuan Baiyu
 * @Date: 2020/5/3 17:56
 **/
public class ThreadContext {

    private ThreadContext() {

    }

    /**
     * 线程容器
     */
    private static final ThreadLocal<Map<Object, Object>> local = new ThreadLocal$Map<Map<Object, Object>>();

    public static void put(Object key, Object value) {
        Map<Object, Object> m = getThreadMap();
        m.put(key, value);
    }

    public static Object get(Object key) {
        Map<Object, Object> m = getThreadMap();
        return m.get(key);
    }

    public static void remove(Object key) {
        Map<Object, Object> m = getThreadMap();
        m.remove(key);
    }

    public static void clear() {
        Map<Object, Object> m = getThreadMap();
        m.clear();
    }

    public static boolean containsKey(Object key) {
        Map<Object, Object> m = getThreadMap();
        return m.containsKey(key);
    }

    public static void putAll(Map<Object, Object> p) {
        Map<Object, Object> m = getThreadMap();
        m.putAll(p);
    }

    private static Map<Object, Object> getThreadMap() {
        return local.get();
    }

    public static void destory() {
        local.remove();
    }

    private static class ThreadLocal$Map<T> extends
            ThreadLocal<Map<Object, Object>> {
        @Override
        protected Map<Object, Object> initialValue() {
            return new HashMap<>();
        }
    }
}