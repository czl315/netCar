package db;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 汽车收入
 *
 * @author Administrator
 * @date 2023-05-28 00:35
 */
public class CarIncome {
    int id;
    String plat;
    String fast_type;
    /**
     * 奖金类型
     */
    String bonus_type;
    String date;
    String start_time;
    /**
     * 实际开始时间
     */
    String start_time_real;
    String end_time;
    BigDecimal mins;
    BigDecimal mins_share;
    BigDecimal fare;
    BigDecimal fare_share;
    BigDecimal fare_pay;
    BigDecimal fare_pay_tatol;
    BigDecimal fare_bounty;
    BigDecimal fare_cost;
    BigDecimal fare_tip;
    Integer num_pass;
    Integer num_order;
    String phone;
    String phone_share;
    BigDecimal distance;
    String seat;
    String seat_rs;
    String start_addr;
    String end_addr;
    BigDecimal order_time;
    BigDecimal arrive_ori_time;
    BigDecimal on_car_time;
    BigDecimal arrive_des_time;
    BigDecimal trip_mins;
    BigDecimal wait_mins;
    BigDecimal go_sta_mins;
    BigDecimal end_empty_mins;
    BigDecimal find_mins;
    String rs_high;
    String rs_low;
    String remark;
    String start_county;
    String start_village;
    String end_county;
    String end_village;
    String start_city;
    String end_city;
    /**
     * 状态
     */
    String state;
    /**
     * 地址类型
     */
    String type_addr;
    /**
     * 类型：顺路订单
     */
    String type_way;
    /**
     * 更新时间
     */
    Date UPDATE_TIME;
    /**
     * 月份
     */
    String month;
    /**
     * 抢单信息
     */
    String grab;

    public String getGrab() {
        return grab;
    }

    public void setGrab(String grab) {
        this.grab = grab;
    }

    public Date getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(Date UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType_way() {
        return type_way;
    }

    public void setType_way(String type_way) {
        this.type_way = type_way;
    }

    public String getType_addr() {
        return type_addr;
    }

    public void setType_addr(String type_addr) {
        this.type_addr = type_addr;
    }

    public String getStart_time_real() {
        return start_time_real;
    }

    public void setStart_time_real(String start_time_real) {
        this.start_time_real = start_time_real;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public String getFast_type() {
        return fast_type;
    }

    public void setFast_type(String fast_type) {
        this.fast_type = fast_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public BigDecimal getMins() {
        return mins;
    }

    public void setMins(BigDecimal mins) {
        this.mins = mins;
    }

    public BigDecimal getMins_share() {
        return mins_share;
    }

    public void setMins_share(BigDecimal mins_share) {
        this.mins_share = mins_share;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public BigDecimal getFare_share() {
        return fare_share;
    }

    public void setFare_share(BigDecimal fare_share) {
        this.fare_share = fare_share;
    }

    public BigDecimal getFare_pay() {
        return fare_pay;
    }

    public void setFare_pay(BigDecimal fare_pay) {
        this.fare_pay = fare_pay;
    }

    public BigDecimal getFare_pay_tatol() {
        return fare_pay_tatol;
    }

    public void setFare_pay_tatol(BigDecimal fare_pay_tatol) {
        this.fare_pay_tatol = fare_pay_tatol;
    }

    public BigDecimal getFare_bounty() {
        return fare_bounty;
    }

    public void setFare_bounty(BigDecimal fare_bounty) {
        this.fare_bounty = fare_bounty;
    }

    public BigDecimal getFare_cost() {
        return fare_cost;
    }

    public void setFare_cost(BigDecimal fare_cost) {
        this.fare_cost = fare_cost;
    }

    public BigDecimal getFare_tip() {
        return fare_tip;
    }

    public void setFare_tip(BigDecimal fare_tip) {
        this.fare_tip = fare_tip;
    }

    public Integer getNum_pass() {
        return num_pass;
    }

    public void setNum_pass(Integer num_pass) {
        this.num_pass = num_pass;
    }

    public Integer getNum_order() {
        return num_order;
    }

    public void setNum_order(Integer num_order) {
        this.num_order = num_order;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_share() {
        return phone_share;
    }

    public void setPhone_share(String phone_share) {
        this.phone_share = phone_share;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getSeat_rs() {
        return seat_rs;
    }

    public void setSeat_rs(String seat_rs) {
        this.seat_rs = seat_rs;
    }

    public String getStart_addr() {
        return start_addr;
    }

    public void setStart_addr(String start_addr) {
        this.start_addr = start_addr;
    }

    public String getEnd_addr() {
        return end_addr;
    }

    public void setEnd_addr(String end_addr) {
        this.end_addr = end_addr;
    }

    public BigDecimal getOrder_time() {
        return order_time;
    }

    public void setOrder_time(BigDecimal order_time) {
        this.order_time = order_time;
    }

    public BigDecimal getArrive_ori_time() {
        return arrive_ori_time;
    }

    public void setArrive_ori_time(BigDecimal arrive_ori_time) {
        this.arrive_ori_time = arrive_ori_time;
    }

    public BigDecimal getOn_car_time() {
        return on_car_time;
    }

    public void setOn_car_time(BigDecimal on_car_time) {
        this.on_car_time = on_car_time;
    }

    public BigDecimal getArrive_des_time() {
        return arrive_des_time;
    }

    public void setArrive_des_time(BigDecimal arrive_des_time) {
        this.arrive_des_time = arrive_des_time;
    }

    public BigDecimal getTrip_mins() {
        return trip_mins;
    }

    public void setTrip_mins(BigDecimal trip_mins) {
        this.trip_mins = trip_mins;
    }

    public BigDecimal getWait_mins() {
        return wait_mins;
    }

    public void setWait_mins(BigDecimal wait_mins) {
        this.wait_mins = wait_mins;
    }

    public BigDecimal getGo_sta_mins() {
        return go_sta_mins;
    }

    public void setGo_sta_mins(BigDecimal go_sta_mins) {
        this.go_sta_mins = go_sta_mins;
    }

    public BigDecimal getEnd_empty_mins() {
        return end_empty_mins;
    }

    public void setEnd_empty_mins(BigDecimal end_empty_mins) {
        this.end_empty_mins = end_empty_mins;
    }

    public BigDecimal getFind_mins() {
        return find_mins;
    }

    public void setFind_mins(BigDecimal find_mins) {
        this.find_mins = find_mins;
    }

    public String getRs_high() {
        return rs_high;
    }

    public void setRs_high(String rs_high) {
        this.rs_high = rs_high;
    }

    public String getRs_low() {
        return rs_low;
    }

    public void setRs_low(String rs_low) {
        this.rs_low = rs_low;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStart_county() {
        return start_county;
    }

    public void setStart_county(String start_county) {
        this.start_county = start_county;
    }

    public String getStart_village() {
        return start_village;
    }

    public void setStart_village(String start_village) {
        this.start_village = start_village;
    }

    public String getEnd_county() {
        return end_county;
    }

    public void setEnd_county(String end_county) {
        this.end_county = end_county;
    }

    public String getEnd_village() {
        return end_village;
    }

    public void setEnd_village(String end_village) {
        this.end_village = end_village;
    }

    public String getStart_city() {
        return start_city;
    }

    public void setStart_city(String start_city) {
        this.start_city = start_city;
    }

    public String getEnd_city() {
        return end_city;
    }

    public void setEnd_city(String end_city) {
        this.end_city = end_city;
    }

    public String getBonus_type() {
        return bonus_type;
    }

    public void setBonus_type(String bonus_type) {
        this.bonus_type = bonus_type;
    }
}
