package dao;

import db.CarIncome;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class CarIncomeDao {
    /**
     * sqlSessionFactory mybatis
     */
    static SqlSessionFactory sqlSessionFactory = MyBatisUtils.getSqlSessionFactory();

    /**
     * 业务排行-插入
     *
     * @param entity
     */
    public static int insert(CarIncome entity) {
        SqlSession session = sqlSessionFactory.openSession();
        int rs = 0;
        try {
            rs = session.insert("dao.mapper.CarIncomeMapper.insert", entity);
            session.commit();
        } finally {
            session.close();
        }
        return rs;
    }

    /**
     * 查询列表-根据
     * @param condition
     * @return
     */
    public static List<CarIncome> findListByCondition(CarIncome condition) {
        SqlSession session = sqlSessionFactory.openSession();
        List<CarIncome> rs = null;
        try {
            rs = session.selectList("dao.mapper.CarIncomeMapper.findListByCondition", condition);
            session.commit();
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return rs;
    }

    public static int update(CarIncome entity) {
        SqlSession session = sqlSessionFactory.openSession();
        int rs = 0;
        try {
            rs = session.update("dao.mapper.CarIncomeMapper.update", entity);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return rs;
    }

}
