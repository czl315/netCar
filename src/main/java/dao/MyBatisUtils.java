package dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author
 * @date 2021/4/27
 */
/*工具类MyBatisUtils，用双检锁简单的实现了一个线程安全的工具类。核心代码在最内层的if判断中。
 * 由于配置文件在类路径上，所以我们只需要指定文件名即可。这里用到了MyBatis提供的Resources工具类，
 * 创建一个输入流，然后交给SqlSessionFactoryBuilder来创建一个SqlSessionFactory。*/
public abstract class MyBatisUtils {
    //SqlSessionFactory  如上所述应为单例模式，应为成员变量
    private static SqlSessionFactory sqlSessionFactory;
    //初始静态化块， 类加载的时候就会加载
    static {
        try {
            //创建sqlSessionFactoryBuilder对象  , 该对象只是用一次，故只需在方法内的变量即可。
            SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
            //创建核心配置文件输入流
            InputStream inputStream = Resources.getResourceAsStream("config-mybatis.xml");
            //通过输入流创建SqlSessionFactory对象
            sqlSessionFactory = ssfb.build(inputStream);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    // 提供get方法，获取sqlSessionFactory
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
