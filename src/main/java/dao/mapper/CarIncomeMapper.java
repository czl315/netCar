package dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import db.CarIncome;

import java.util.List;

/**
 * CarIncomeMapper简介
 *
 * @author Administrator
 * @date 2023-05-28 00:24
 */
public interface CarIncomeMapper {
    @Insert({"<script>",
            "INSERT INTO `bank19`.`car_income` (" +
                    "`plat`, `fast_type`, `date`, `start_time`, " +
                    "`end_time`, `mins`, `mins_share`, `fare`, `fare_share`, " +
                    "`fare_pay`, `fare_pay_tatol`, `fare_bounty`, `fare_cost`, `fare_tip`, " +
                    "`num_pass`, `num_order`, `phone`, `phone_share`, `distance`, " +
                    "`seat`, `seat_rs`, `start_addr`, `end_addr`, `order_time`, " +
                    "`arrive_ori_time`, `on_car_time`, `arrive_des_time`, `trip_mins`, `wait_mins`, " +
                    "`go_sta_mins`, `end_empty_mins`, `find_mins`, `rs_high`, `rs_low`, " +
                    "`remark`, `start_county`, `start_village`, `end_county`, `end_village`, " +
                    "`start_city`, `end_city`," +
                    "`bonus_type`," +
                    "`start_time_real`," +
                    "`type_addr`," +
                    "`type_way`," +
                    "`UPDATE_TIME`," +
                    "`month`," +
                    "`grab`," +
                    "`state`" +
                    ") " +
                    "VALUES (" +
                    "#{plat}, #{fast_type}, #{date}, #{start_time}, " +
                    "#{end_time}, #{mins}, #{mins_share}, #{fare}, #{fare_share}, " +
                    "#{fare_pay}, #{fare_pay_tatol}, #{fare_bounty}, #{fare_cost}, #{fare_tip}, " +
                    "#{num_pass}, #{num_order}, #{phone}, #{phone_share}, #{distance}, " +
                    "#{seat}, #{seat_rs}, #{start_addr}, #{end_addr}, #{order_time}, " +
                    "#{arrive_ori_time}, #{on_car_time}, #{arrive_des_time}, #{trip_mins}, #{wait_mins}, " +
                    "#{go_sta_mins}, #{end_empty_mins}, #{find_mins}, #{rs_high}, #{rs_low}, " +
                    "#{remark}, #{start_county}, #{start_village}, #{end_county}, #{end_village}, " +
                    "#{start_city}, #{end_city}," +
                    "#{bonus_type}," +
                    "#{start_time_real}," +
                    "#{type_addr}," +
                    "#{type_way}," +
                    "#{UPDATE_TIME}," +
                    "#{month}," +
                    "#{grab}," +
                    "#{state}" +
                    ") ;" +
                    "</script>"})
    void insert(CarIncome entity);

    @Select({"<script>",
            "   SELECT ",
            "       * ",
            "   FROM car_income ",
            "   WHERE 1=1  ",
            "       <if test='date != null'> ",
            "       AND car_income.date = #{date}  ",
            "       </if> ",
            "   ORDER BY date DESC,start_time DESC ",
            "</script>"})
    List<CarIncome> findListByCondition(CarIncome condition);

    @Update({"<script>",
            "update car_income",
            "  <set>",
            "    <if test='mins != null'>mins=#{mins},</if>",
            "    <if test='mins_share != null'>mins_share=#{mins_share},</if>",
            "    <if test='end_county != null'>end_county=#{end_county},</if>",
            "    <if test='end_village != null'>end_village=#{end_village},</if>",
            "    <if test='wait_mins != null'>wait_mins=#{wait_mins},</if>",
            "    <if test='start_time_real != null'>start_time_real=#{start_time_real},</if>",
            "  </set>",
            "where id=#{id} ",
//            "where date>=#{date} AND f12=#{f12}",
            "</script>"})
    void update(CarIncome entity);
}
