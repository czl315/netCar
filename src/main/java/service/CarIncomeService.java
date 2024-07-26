package service;

import dao.CarIncomeDao;
import db.CarIncome;

import java.util.List;

/**
 * CarIncomeService简介
 *
 * @author Administrator
 * @date 2023-05-28 00:49
 */
public class CarIncomeService {

    /**
     * 插入
     *
     * @param entity
     * @return
     */
    public static Integer insert(CarIncome entity) {
        Integer rs = 0;
        if (entity == null) {
            return rs;
        }
        /**
         * 插入数据库-K线
         */
        return CarIncomeDao.insert(entity);
    }

    /**
     * 查询列表-根据
     *
     * @param condition 条件
     * @return 结果
     */
    public static List<CarIncome> findListByCondition(CarIncome condition) {
        return CarIncomeDao.findListByCondition(condition);
    }

    /**
     * 更新
     *
     * @param condition 更新内容和条件
     * @return 结果
     */
    public static Integer update(CarIncome condition) {
        return CarIncomeDao.update(condition);
    }

}
