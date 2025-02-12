package cc.wangzijie.springboot;

import org.springframework.context.ConfigurableApplicationContext;

public class SpringHelper {

    /**
     * 任何地方都可以通过这个applicationContext获取springboot的上下文
     */
    private static ConfigurableApplicationContext applicationContext;

    /**
     * 启动SpringBoot时注入上下文
     *
     * @param applicationContext springboot的上下文
     */
    public static void init(ConfigurableApplicationContext applicationContext) {
        SpringHelper.applicationContext = applicationContext;
    }

    /**
     * 关闭SpringBoot
     */
    public static void stop() {
        applicationContext.close();
    }

}
