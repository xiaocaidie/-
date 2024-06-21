package li.common;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登入用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrent(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取值
     * @return
     */
    public static Long getCurrent(){
        return threadLocal.get();
    }
}
