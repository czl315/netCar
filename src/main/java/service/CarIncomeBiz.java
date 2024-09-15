package service;

import com.alibaba.fastjson.JSON;
import db.CarIncome;
import org.apache.commons.lang3.StringUtils;
import utils.AddressUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 网约车收入服务(业务处理)
 *
 * @author Administrator
 * @date 2023-05-28 00:49
 */
public class CarIncomeBiz {

    public static void main(String[] args) {
        String date = "2024-08-31";
        updateMinutes(date);//更新用时分钟
//        saveIncomeByDiDi();
//        saveIncomeByYangGuang("阳光");
//        saveIncomeByShouQi("首汽");
//        System.out.println(savePatch( date,plat,null,null,count));//阳光  嘀嗒 首汽

//        saveByJianDaoChenKeWuPin(date);
//        int count = 1;
//        String plat = "滴滴";//阳光  嘀嗒 首汽  滴滴
//        System.out.println(savePatch( "2024-07-30",plat,"奖金","活跃营-2199043712712",1));
        //  主城早峰完单补贴   热区早峰单单补贴 主城午晚峰流水加速     主城热区夜峰单单补贴
        //专属热区完单奖励  特惠专属热区完单奖励   热区多天完单奖励     10.31经海路热区夜高峰补贴
        //免佣卡彩蛋奖励 单单特快卡  双节专属单单特快卡 北京市热区流水+20% 北京市峰期流水+20% 连续签到奖励8   特惠专属冲刺 专属时段完单奖励  特惠专属热区单单奖 雨天膨胀卡
        // 晋级赛王牌加速卡福利流水+20% 晋级赛先锋加速卡福利
        //误工险订单取消责任赔偿金    空驶补偿 收到不顺路补贴  调度无单补偿
        //单单免佣卡 双节专属北京市峰期免佣卡
        //时空红包奖励  随机奖励    感谢红包  社群活动奖励 社群专属趣味答题
        //双旦专属-快车堵车卡 双旦专属时段完单奖励
        //特定时段：奖励日专属早峰流水加速 奖励日专属晚峰流水加速  北京早峰完单补贴奖励日专属 午晚峰完单补贴奖励日专属 晚峰完单补贴奖励日专属 晚峰完单奖励奖励日专属  北京主城早峰完单补贴
        //节日：端午专属雨天膨胀卡
        //特定地点：场站福利月火车站瓜分奖 早高峰热区单单补贴 早高峰热区单单补贴  奖励日专属   北京  大兴国际机场 热区   热区夜峰单单补贴 热区瓜分奖 主城热区夜峰单单补贴
        //区域：东城西城海淀热区专属夜峰单单补贴
        //季节：清凉一夏雨天膨胀卡 高温补贴
        // 北京合规专属奖励早峰完单补贴奖励日专属 您的专属 双证专属早峰完单补贴 专属时段完单奖励 早峰完单补贴 -春节专属    晚峰  夜峰  高峰  早午峰 午晚峰
        //  流水加速 单单补贴

//        findListByCondition(date);

        //根据地址，更新区县、村镇
//        updateEndTown(date);
    }

    /**
     * 更新用时分钟
     */
    private static void updateMinutes(String date) {
        int updateCount = 0;
        CarIncome condition = new CarIncome();
        condition.setDate(date);
        List<CarIncome> rs = CarIncomeService.findListByCondition(condition);
        if (rs != null) {
            for (CarIncome carIncome : rs) {
//                System.out.println(JSON.toJSONString(carIncome));

                //计算用时分钟(区分拼单)
                Integer orderCount = carIncome.getNum_order();
                BigDecimal minutes = handlerMinutes(carIncome);
                if (minutes == null) {
                    System.out.println("用时计算异常！");
                    continue;
                }
                if (orderCount != null && orderCount > 1) {
                    carIncome.setMins_share(minutes);
                    System.out.println("拼单用时：" + minutes);
                } else {
                    System.out.println("用时：" + minutes);
                    carIncome.setMins(minutes);
                }

                //计算-等待订单时间
                carIncome.setWait_mins(handlerMinutesWait(carIncome));

                updateCount += CarIncomeService.update(carIncome);
            }
        }
        System.out.println("更新个数：" + updateCount);
    }

    /**
     * 计算-等待订单时间（分钟）
     *
     * @param carIncome 网约车收入
     */
    private static BigDecimal handlerMinutesWait(CarIncome carIncome) {
        if (carIncome == null) {
            return null;
        }
        String endTime = carIncome.getEnd_time();
        BigDecimal arriveTime = carIncome.getArrive_des_time();
        if (arriveTime == null || endTime == null) {
            return null;
        }
        String fgf = ".";
        String fgfSecond = ":";
        String hourEndTime = "0";
        String minuteEndTime = "0";
        String secondEndTime = "30";
        if (endTime.contains(fgf)) {
            fgf = "\\.";//特殊字符分隔符
            String[] arrayEndTime = endTime.split(fgf);
            hourEndTime = arrayEndTime[0];
            minuteEndTime = arrayEndTime[1];

        } else if (endTime.contains(fgfSecond)) {
            String[] arrayEndTime = endTime.split(fgfSecond);
            hourEndTime = arrayEndTime[0];
            minuteEndTime = arrayEndTime[1];
            secondEndTime = arrayEndTime[2];
        }
        BigDecimal hourBegTime = arriveTime.setScale(0, BigDecimal.ROUND_DOWN);
        int hourBegTimeInt = hourBegTime.intValue();
        String minuteBegTimeStr = arriveTime.subtract(hourBegTime).toString().replace("0.", "");
        int minuteBegTimeInt = Integer.parseInt(minuteBegTimeStr);
//            System.out.println("hourBegTimeInt：" + hourBegTimeInt + ";" + "minuteBegTimeInt：" + minuteBegTimeInt);
        int timeMinutesBegTime = hourBegTimeInt * 60 * 60 + minuteBegTimeInt * 60 + Integer.parseInt(secondEndTime);//默认30秒

        int hourEndTimeInt = Integer.parseInt(hourEndTime);
        int minuteEndTimeInt = Integer.parseInt(minuteEndTime);
//            System.out.println("hourEndTimeInt：" + hourEndTimeInt + ";" + "minuteEndTimeInt：" + minuteEndTimeInt);
        int timeMinutesEndTime = hourEndTimeInt * 60 * 60 + minuteEndTimeInt * 60 + 30;

        int minutes = (timeMinutesEndTime - timeMinutesBegTime) / 60;
//            System.out.println("minutes：" + minutes);
        return new BigDecimal("" + minutes);
    }

    /**
     * 计算用时分钟
     *
     * @param carIncome 网约车收入
     */
    private static BigDecimal handlerMinutes(CarIncome carIncome) {
        if (carIncome == null) {
            return null;
        }
        String begTime = carIncome.getStart_time();
        String endTime = carIncome.getEnd_time();
        String fgf = ".";
        String fgfSecond = ":";
        if (begTime == null || endTime == null) {
            return null;
        }
        String hourBegTime = "0";
        String minuteBegTime = "0";
        String secondBegTime = "0";
        String hourEndTime = "0";
        String minuteEndTime = "0";
        String secondEndTime = "0";
        if (begTime.contains(fgf) && endTime.contains(fgf)) {
            fgf = "\\.";//特殊字符分隔符
            String[] array = begTime.split(fgf);
            hourBegTime = array[0];
            minuteBegTime = array[1];
            secondBegTime = "30";//默认
            String[] arrayEndTime = endTime.split(fgf);
            hourEndTime = arrayEndTime[0];
            minuteEndTime = arrayEndTime[1];
            secondEndTime = "30";//默认
        } else if (begTime.contains(fgfSecond) && endTime.contains(fgfSecond)) {
            String[] array = begTime.split(fgfSecond);
            hourBegTime = array[0];
            minuteBegTime = array[1];
            secondBegTime = array[2];
            String[] arrayEndTime = endTime.split(fgfSecond);
            hourEndTime = arrayEndTime[0];
            minuteEndTime = arrayEndTime[1];
            secondEndTime = arrayEndTime[2];//默认
        }

        int hourBegTimeInt = Integer.parseInt(hourBegTime);
        int minuteBegTimeInt = Integer.parseInt(minuteBegTime);
        int secondBegTimeInt = Integer.parseInt(secondBegTime);
        int timeMinutesBegTime = hourBegTimeInt * 60 * 60 + minuteBegTimeInt * 60 + secondBegTimeInt;

        int hourEndTimeInt = Integer.parseInt(hourEndTime);
        int minuteEndTimeInt = Integer.parseInt(minuteEndTime);
        int secondEndTimeInt = Integer.parseInt(secondEndTime);
        int timeMinutesEndTime = hourEndTimeInt * 60 * 60 + minuteEndTimeInt * 60 + secondEndTimeInt;

//        int minutes = (timeMinutesEndTime - timeMinutesBegTime) / 60;
        BigDecimal second = new BigDecimal("" + timeMinutesEndTime).subtract(new BigDecimal("" + timeMinutesBegTime));
//            System.out.println("minutes：" + minutes);
        return second.divide(new BigDecimal("60"), 2, RoundingMode.HALF_UP);
    }

    /**
     * 保存-根据-滴滴捡到乘客用品-照片字符串
     *
     * @param date 日期
     */
    public static void saveByJianDaoChenKeWuPin(String date) {
//        date = "2023-10-07";
        String oriStr = "";//原始数据字符串
        {
            oriStr = "" +
                    "" +
                    "" +
                    "" +
                    "";
        }

//        System.out.println(oriStr);
        String[] oriStrArr = oriStr.split("\n");
        List<CarIncome> entityList = new ArrayList<>();
        Set<Integer> ids = new HashSet<>();
        CarIncome entity = new CarIncome();
        boolean isStartAddrOk = false;
        boolean isSetAddrEnd = false;
        boolean isOrderBegin = false;//订单是否开始，订单开始标志：快车
        int orderNum = 0;
        for (String row : oriStrArr) {
//            row = row.replace(" ","");
//            if (row.contains("宣武门|新华通讯社-南2门")) {
//                System.out.println(row);
//            }
            row = row.replace(" ", "");
            if (row.equals("快车") || row.equals("特惠")) {
                entity = new CarIncome();
                if (row.equals("快车")) {
                    entity.setPlat("快车");
                } else if (row.equals("特惠")) {
                    entity.setFast_type("特惠快车");
                } else {
                    entity.setPlat(row);
                }
                isOrderBegin = true;
                isStartAddrOk = false;
                isSetAddrEnd = false;
                continue;
            }
            if (!isOrderBegin) {
                continue;//如果订单未开始，跳过
//                if (row.contains("找不到订单点此报备") || row.equals("请您选择想反馈的订单") || row.equals("请选择您想反馈的订单")) {
//                    continue;
//                }
            }
            if (row.startsWith("2023-") || row.startsWith("2024-")) {
                entity.setDate(row.substring(0, 10));
                String time = row;
//                time = time.replace("2023-08-01 ", "");
//                time = time.replace(":", ".");
//                time = time.substring(11, 15);
                time = time.substring(10);
                entity.setId(++orderNum);
//                System.out.println(time);
                entity.setStart_time(time);
                entity.setStart_time_real(time);
                continue;
            } else if (!isStartAddrOk) {
                if (row.startsWith("·")) {
                    row = row.replace("·", "");
                }
                entity.setStart_addr(row);
                isStartAddrOk = true;
                continue;
            } else if (isStartAddrOk && !isSetAddrEnd) {
                if (row.startsWith("·")) {
                    row = row.replace("·", "");
                }
                entity.setEnd_addr(row);
                isSetAddrEnd = true;
                continue;
            } else if (row.equals("已完成") || row.equals("已关闭") || row.equals("进行中")) {
                entity.setState(row);
//                if (ids.contains(orderNum)) {
//                    System.out.println("记录已存在");
//                } else {
                //判断特定日期
                if (StringUtils.isNotBlank(date)) {
                    if (date.equals(entity.getDate())) {
                        ids.add(orderNum);
                        entityList.add(entity);
                    } else {
                        System.out.println("非特定日期不保存：" + date + ":" + entity.getDate());
                    }
                } else {
                    System.out.println("判断特定日期不判定");

                }
//                }

                isStartAddrOk = false;
                isSetAddrEnd = false;
                isOrderBegin = false;
                continue;
            } else {
                if (row.contains("找不到订单点此报备") || row.equals("请您选择想反馈的订单") || row.equals("请选择您想反馈的订单")) {
//                    System.out.println(row);
                    continue;
                }
                System.out.println(row);

            }
//            System.out.print(",");
            System.out.println(row);
        }

        int i = 0;
        for (CarIncome carIncome : entityList) {
            System.out.print(++i + ":");
            System.out.println(JSON.toJSONString(carIncome));
            CarIncomeService.insert(carIncome);
        }
    }

    /**
     * 保存订单-根据我的行程-滴滴
     */
    public static void saveIncomeByDiDi() {
        String date = "2024-12-31";
        String plat = "滴滴";
        String year = "2024";
        String oriStr = "";//原始数据字符串
        {
            oriStr = "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "";
        }
        {
            oriStr = "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "08月31日\n" +
                    "快车\n" +
                    "21:23:37\n" +
                    "瀛海地铁站A口\n" +
                    "亦城亦禧-南门\n" +
                    "已支付\n" +
                    "14.44元\n" +
                    "快车\n" +
                    "20:48:55\n" +
                    "北京住总万科广场-B2西门外-上\n" +
                    "车点\n" +
                    "途经1地:万源西里-西门\n" +
                    "芳源里-西北门\n" +
                    "已支付\n" +
                    "16.69元\n" +
                    "三\n" +
                    "011:16 0\n" +
                    "我的行程\n" +
                    "全部订单、\n" +
                    "优享\n" +
                    "18:51:14\n" +
                    "小悦中心-西门\n" +
                    "双花园南里一区-西门\n" +
                    "已支付\n" +
                    "31.53元\n" +
                    "优享\n" +
                    "17:57:23\n" +
                    "上海沙龙-东南门\n" +
                    "北京朝阳站-北进站口送站单\n" +
                    "已支付\n" +
                    "48.90元\n" +
                    "快车\n" +
                    "17:38:54\n" +
                    "人大附中经开学校-南门\n" +
                    "眉州东坡酒楼（亦庄店)\n" +
                    "已支付\n" +
                    "10.70元\n" +
                    "优享\n" +
                    "17:35:50\n" +
                    "卡尔生活馆62号楼\n" +
                    "Ling Long\n" +
                    "无责已关闭\n" +
                    "优享\n" +
                    "17:08:17\n" +
                    "北京城建·兴悦居-北门\n" +
                    "途经2地：北京城建·海梓府-北门、\n" +
                    "听涛雅苑-东门\n" +
                    "亦品阁四季涮肉（亦庄店)\n" +
                    "三\n" +
                    "已支付\n" +
                    "30.42元\n" +
                    "<\n" +
                    "优享\n" +
                    "10:14:37\n" +
                    "南海家园四里-北门\n" +
                    "星巴克（文化园西路店)\n" +
                    "顺路订单\n" +
                    "已支付\n" +
                    "17.03元\n" +
                    "快车\n" +
                    "09:53:16\n" +
                    "北京爱育华妇儿医院-西北门\n" +
                    "南海家园五里-北门\n" +
                    "顺路订单\n" +
                    "已支付\n" +
                    "11.94元\n" +
                    "优享\n" +
                    "09:29:35\n" +
                    "中海寰宇时代2期南区-南门\n" +
                    "北京爱育华医院\n" +
                    "顺路订单\n" +
                    "已支付\n" +
                    "22.81元\n" +
                    "快车\n" +
                    "09:08:50\n" +
                    "首航（瀛海店)\n" +
                    "清航空天科技有限公司\n" +
                    "顺路订单\n" +
                    "已支付\n" +
                    "11.58元\n" +
                    "<\n";
        }


//        System.out.println(oriStr);

        String[] oriStrArr = oriStr.split("\n");
        //处理滴滴订单不规则字符
        oriStrArr = handlerDiDiOrderStr(oriStrArr);

        List<CarIncome> entityList = new ArrayList<>();
        Set<Integer> ids = new HashSet<>();
        CarIncome entity = null;
        boolean isStartAddrOk = false;
        boolean isSetAddrEnd = false;
        boolean isOkTime = false;//时间是否处理
        boolean isOkPay = false;//是否支付
        boolean isOrderBegin = false;//订单是否开始，订单开始标志：快车
        int orderNum = 0;
        for (String row : oriStrArr) {
//            row = row.replace(" ","");
//            if (row.contains("宣武门|新华通讯社-南2门")) {
//                System.out.println(row);
//            }
            row = row.replace(" ", "");

            //日期
            if ((row.length() == 4 || row.length() == 5 || row.length() == 6) && row.contains("月") && row.contains("日")) {
                date = parseDate(year, row);
                System.out.println("当前日期:" + date);
            }

            if (row.equals("优享") || row.equals("滴滴特快") || row.equals("快车") || row.equals("特惠") || row.equals("特惠快车")
                    || (row.startsWith("优享") && row.length() == 10)
                    || (row.startsWith("特惠") && row.length() == 10)
                    || (row.startsWith("特惠快车") && row.length() == 12)
                    || (row.startsWith("滴滴特快") && row.length() == 12)
                    || (row.startsWith("快车") && row.length() == 10)) {
                //快车 16:45:15

                if (entity != null) {
                    entityList.add(entity);//如果记录非空，将数据插入集合
                }

                entity = new CarIncome();

                entity.setFast_type(row);
                //快车 10:49:22
                if ((row.startsWith("优享") && row.length() == 10) || (row.startsWith("特惠") && row.length() == 10) || (row.startsWith("快车") && row.length() == 10)) {
                    entity.setFast_type(row.substring(0, 2));
                    row = row.substring(2);
                }
                if ((row.startsWith("特惠快车") && row.length() == 12)) {
                    entity.setFast_type(row.substring(0, 4));
                    row = row.substring(4);
                }
                //滴滴特快07:53:09
                if ((row.startsWith("滴滴特快") && row.length() == 12)) {
                    entity.setFast_type(row.substring(0, 4));
                    row = row.substring(4);
                }
//                else if (row.equals("特惠")) {
//                    entity.setFast_type("特惠快车");
//                }else{
//                    entity.setPlat(row);
//                }
                isOrderBegin = true;
                isOkTime = false;
                isStartAddrOk = false;
                isSetAddrEnd = false;
            }
            //01月12日16：22:07抢单
            if (row.contains("抢单")) {
                entity.setGrab(row);
                continue;
            }
            if (!isOrderBegin) {
                continue;//如果订单未开始，跳过
//                if (row.contains("找不到订单点此报备") || row.equals("请您选择想反馈的订单") || row.equals("请选择您想反馈的订单")) {
//                    continue;
//                }
            }
            //08:38:14
            if (row.length() == 8 && row.contains(":")) {
                entity.setDate(date);
                entity.setPlat(plat);
                entity.setStart_time(row);
                entity.setStart_time_real(row);
                entity.setId(++orderNum);
                entity.setUPDATE_TIME(new Date());
                entity.setMonth(date.substring(0, 7));
//                System.out.println(time);
                isOkTime = true;
                continue;
            }
//            else if (row.equals("无责乘客取消") || row.equals("无责") || row.equals("乘客取消")|| row.equals("核实中乘客取消")) {
//                entity.setState(row);
//            }
            else if (isOkTime && !isStartAddrOk) {
                if (row.startsWith("·")) {
                    row = row.replace("·", "");
                }
                entity.setStart_addr(row);
                isStartAddrOk = true;
                continue;
            } else if (isStartAddrOk && !isSetAddrEnd) {
                if (row.startsWith("·")) {
                    row = row.replace("·", "");
                }
                entity.setEnd_addr(row);
                isSetAddrEnd = true;
                continue;
            } else if (row.equals("优享") || row.equals("快车") || row.equals("特惠快车") || row.equals("滴滴特快") || row.equals("节假日司机服务费5元") || row.equals("催款")) {
                continue;
            } else if (row.equals("顺路订单")) {
                entity.setType_way("顺路");
                continue;
            } else if (row.equals("送站单") || row.equals("接站单") || row.equals("送机单") || row.equals("接机单")) {
                entity.setType_addr(row);
                continue;
            } else if (row.equals("已完成") || row.equals("已支付") || row.equals("平台已垫付") || row.equals("未支付")
                    || row.equals("无责已关闭") || row.equals("已空驶补偿") || row.equals("无责乘客取消") || row.equals("核实中乘客取消")) {
                String state = entity.getState();
                String state_empty = "空驶补偿";
                if (row.equals("已支付") || row.equals("平台已垫付") || row.equals("未支付")) {
                    entity.setState("已完成");
                } else if (row.equals("已空驶补偿")) {
                    entity.setState(state_empty);
                    entity.setFast_type(state_empty);
                } else if (state != null && state.equals("空驶补偿") && row.equals("无责已关闭")) {
//                    entity.setState("空驶补偿");
                } else {
                    entity.setState(row);
//                    entityList.add(entity);
                }
                continue;
            } else if (row.endsWith("元")) {
                row = row.replace("元", "");
                BigDecimal fare = new BigDecimal(row);
                entity.setFare(fare);

//                entityList.add(entity);

                isStartAddrOk = false;
                isSetAddrEnd = false;
                isOrderBegin = false;
                continue;
            } else {
                if (row.contains("三") || row.equals("我的行程") || row.equals("全部订单、") || row.equals("无责乘客取消")) {
//                    System.out.println(row);
                    continue;
                }
                System.out.println(row);

            }
//            System.out.print(",");
            if (row.contains("三") || row.equals("我的行程") || row.equals("全部订单、") || row.equals("无责乘客取消") || row.equals("无责") || row.equals("乘客取消")) {
//                    System.out.println(row);
                continue;
            }
            System.out.println(row);
        }
        if (entity != null) {
            entityList.add(entity);//最后一条记录，将数据插入集合
        }

        int i = 0;
        for (CarIncome carIncome : entityList) {
            System.out.print(++i + ":");
            System.out.println(JSON.toJSONString(carIncome));
            CarIncomeService.insert(carIncome);
        }
    }


    /**
     * 保存订单-根据我的行程-阳光
     *
     * @param plat 111
     */
    public static void saveIncomeByYangGuang(String plat) {
        String date = "2024-12-31";
        String oriStr = "";//原始数据字符串
        {
            oriStr = "" +
                    "" +
                    "" +
                    "" +
                    "";
        }
        {
            oriStr = "" +
                    "" +
                    "22:40\n" +
                    "<\n" +
                    "默认日期\n" +
                    "2024-08-31\n" +
                    "即时\n" +
                    "10:36\n" +
                    "山河屯铁锅炖（旧宫店)\n" +
                    "桃源里-25号楼\n" +
                    "2486XFL8ECWX4F6\n" +
                    "我的行程\n" +
                    "改派记录\n" +
                    "已完成▼\n" +
                    "已完成\n" +
                    "复制\n" +
                    "￥14.77\n" +
                    "2024-08-30\n" +
                    "即时\n" +
                    "顺\n" +
                    "22:52\n" +
                    "君安国际-西北侧\n" +
                    "豪客烟酒行（清逸西园店)\n" +
                    "2486XBQQ1GWX4F7\n" +
                    "已完成\n" +
                    "复制\n" +
                    "￥23.06\n" +
                    "客服助手\n" +
                    "即时\n" +
                    "09:23\n" +
                    "荣京丽都-东南门-上车点\n" +
                    "鸿坤理想城礼域府-东2门\n" +
                    "2486X1608AWX4F7\n" +
                    "复制\n" +
                    "已完成\n" +
                    "￥44.86\n" +
                    "<\n" +
                    "22:39\n" +
                    "<\n" +
                    "默认日期\n" +
                    "2024-08-28\n" +
                    "即时\n" +
                    "顺\n" +
                    "09:59\n" +
                    "南方庄甲70号院-南1门\n" +
                    "一品嘉园\n" +
                    "2486WDGWNVWX4F9\n" +
                    "我的行程\n" +
                    "改派记录\n" +
                    "已完成▼\n" +
                    "已完成\n" +
                    "复制\n" +
                    "￥46.43\n" +
                    "2024-08-27\n" +
                    "即时\n" +
                    "09:24\n" +
                    "南海家园二里-东门\n" +
                    "龙湖北京亦庄天街-西南侧\n" +
                    "2486W10670WX4F4\n" +
                    "复制\n" +
                    "已完成\n" +
                    "￥9.94\n" +
                    "客服助手\n" +
                    "2024-08-26\n" +
                    "即时\n" +
                    "顺\n" +
                    "14:10\n" +
                    "地铁同济南路站-公交站-东北侧\n" +
                    "北京振国中西医结合肿瘤医院\n" +
                    "2486VRB9F7WX4F7\n" +
                    "复制\n" +
                    "已完成\n" +
                    "￥11.50\n" +
                    "<\n" +
                    "22:39\n" +
                    "<\n" +
                    "默认日期\n" +
                    "2024-08-26\n" +
                    "我的行程\n" +
                    "改派记录\n" +
                    "已完成▼\n" +
                    "即时\n" +
                    "10:56\n" +
                    "P1停车楼1M夹层04-06号上车点\n" +
                    "五连环商用车城-2号门\n" +
                    "2486VOUTWVWX4BC\n" +
                    "已完成\n" +
                    "复制\n" +
                    "￥65.88\n" +
                    "即时\n" +
                    "09:37\n" +
                    "熙悦林语B区二区（南门)\n" +
                    "北京大兴国际机场航站楼-4F国...\n" +
                    "2486VNS1ITWX4F3\n" +
                    "复制\n" +
                    "已完成\n" +
                    "￥85.78\n" +
                    "客服助手\n" +
                    "即时\n" +
                    "顺\n" +
                    "09:01\n" +
                    "亦园公寓\n" +
                    "保利绿城和锦诚园25-1号楼东南侧\n" +
                    "2486VN0HY4WX4FS\n" +
                    "复制\n" +
                    "已完成\n" +
                    "￥29.56\n";
        }

//        System.out.println(oriStr);
        String[] oriStrArr = oriStr.split("\n");
        List<CarIncome> entityList = new ArrayList<>();
        CarIncome entity = null;
        boolean isStartAddrOk = false;
        boolean isSetAddrEnd = false;
        boolean isOkTime = false;//时间是否处理
        boolean isOrderBegin = false;//订单是否开始，订单开始标志：即时
        int orderNum = 0;
        for (String row : oriStrArr) {
//            row = row.replace(" ","");
//            if (row.contains("宣武门|新华通讯社-南2门")) {
//                System.out.println(row);
//            }
            row = row.replace(" ", "");

            if (row.length() == 10 && row.startsWith("202") && row.contains("-")) {
                //处理日期，格式："2024-02-27"
                date = row;
            }

            if (row.equals("即时")) {
                if (entity != null) {
                    entityList.add(entity);//如果记录非空，将上一条数据插入集合
                }

                entity = new CarIncome();

                entity.setFast_type("经济型");//默认经济型，需要到明细中更改
                isOrderBegin = true;
                isOkTime = false;
                isStartAddrOk = false;
                isSetAddrEnd = false;
                continue;
            }
            if (!isOrderBegin) {
                continue;//如果订单未开始，跳过
//                if (row.contains("我的行程") || row.equals("默认日期") ) {
//                    continue;
//                }
            }

            //处理开始时间和日期，格式："22:24"
            if (row.length() == 5 && row.contains(":")) {
                String startTime = row + ":00";
                entity.setPlat(plat);
                entity.setDate(date);
                entity.setStart_time(startTime);
                entity.setStart_time_real(startTime);
                entity.setId(++orderNum);
                entity.setUPDATE_TIME(new Date());
                entity.setMonth(date.substring(0, 7));
//                System.out.println(time);
                isOkTime = true;
                continue;
            }
//            else if (row.equals("无责乘客取消") || row.equals("无责") || row.equals("乘客取消")|| row.equals("核实中乘客取消")) {
//                entity.setState(row);
//            }
            //处理起点地址,"芳源里丙13号楼西北侧"
            else if (isOkTime && !isStartAddrOk) {
                if (row.startsWith("·")) {
                    row = row.replace("·", "");
                }
                entity.setStart_addr(row);
                isStartAddrOk = true;
                continue;
            } else if (isStartAddrOk && !isSetAddrEnd) {
                //处理起点地址, "北京大兴国际机场航站楼"
                if (row.startsWith("·")) {
                    row = row.replace("·", "");
                }
                entity.setEnd_addr(row);
                isSetAddrEnd = true;
                continue;
            } else if (row.startsWith("24") && row.length() == 15) {
                //  处理订单号："2425BKI6C5WX4F3"
                continue;
            } else if (row.equals("复制") || row.equals("客服助手") || row.equals("改派记录") || row.equals("已完成▼")) {
                //  处理忽略信息："复制"、"客服助手"、"改派记录"
                continue;
            } else if (row.equals("顺")) {
                entity.setType_way("顺路");
                continue;
            } else if (row.equals("已完成")) {
                entity.setState(row);
                continue;
            } else if (row.contains("￥")) {
                //处理订单金额："￥60.21"
                row = row.replace("￥", "");
                BigDecimal fare = new BigDecimal(row);
                entity.setFare(fare);

                isStartAddrOk = false;
                isSetAddrEnd = false;
                isOrderBegin = false;
                continue;
            } else {
                if (row.contains("三")) {
//                    System.out.println(row);
                    continue;
                }
                System.out.println(row);

            }
//            System.out.print(",");
            if (row.contains("三") || row.equals("我的行程") || row.equals("全部订单、") || row.equals("无责乘客取消") || row.equals("无责") || row.equals("乘客取消")) {
//                    System.out.println(row);
                continue;
            }
            System.out.println(row);
        }
        if (entity != null) {
            entityList.add(entity);//最后一条记录，将数据插入集合
        }

        int i = 0;
        for (CarIncome carIncome : entityList) {
            System.out.print(++i + ":");
            System.out.println(JSON.toJSONString(carIncome));
            CarIncomeService.insert(carIncome);
        }
    }


    /**
     * 保存订单-根据我的行程-首汽
     *
     * @param plat 平台
     */
    public static void saveIncomeByShouQi(String plat) {
        String year = "2024";
        String date = null;
        String oriStr = "";//原始数据字符串
        {
            oriStr = "" +
                    "" +
                    "" +
                    "" +
                    "";
        }
        {
            oriStr = "" +
                    "" +
                    "" +
                    "" +
                    "22:43\n" +
                    "改派记录\n" +
                    "待结算\n" +
                    "我的订单\n" +
                    "已完成\n" +
                    "已取消\n" +
                    "待服务\n" +
                    "8月29日\n" +
                    "BZ240829114206724005154\n" +
                    "即时用车11:42\n" +
                    "行宫园四里4号楼西南侧\n" +
                    "广阳城（地铁站)\n" +
                    "已完成\n" +
                    "顺路\n" +
                    "￥12.06\n" +
                    "开发票\n" +
                    "评价乘客\n";
        }

//        System.out.println(oriStr);
        String[] oriStrArr = oriStr.split("\n");
        List<CarIncome> entityList = new ArrayList<>();
        Set<Integer> ids = new HashSet<>();
        CarIncome entity = null;
        boolean isStartAddrOk = false;
        boolean isSetAddrEnd = false;
        boolean isOkTime = false;//时间是否处理
        boolean isOkPay = false;//是否支付
        boolean isOrderBegin = false;//订单是否开始，订单开始标志：即时
        int orderNum = 0;
        for (String row : oriStrArr) {
//            row = row.replace(" ","");
//            if (row.contains("宣武门|新华通讯社-南2门")) {
//                System.out.println(row);
//            }
            row = row.replace(" ", "");

            String[] passStrArr = {"我的订单", "待结算", "改派记录", "已完成", "已取消", "待服务", "开发票", "评价乘客", "已评价乘客", "平台已垫付", "<"};//忽略字符
            //"即时用车 10:03\n" +
//            if (row.equals() || row.equals("待结算") || row.equals("改派记录") || row.equals("已完成") || row.equals("已取消") || row.equals("待服务")) {
            if (Arrays.asList(passStrArr).contains(row)) {
                //  处理忽略信息："复制"、"客服助手"、"改派记录"
                continue;
            }
            //  处理忽略信息
            if (row.startsWith("含免佣￥") || row.startsWith("上划查看20")) {
                continue;
            }

            //处理日期，格式：7月9日
            if ((row.length() == 4 || row.length() == 5 || row.length() == 6) && row.contains("月") && row.contains("日")) {
                date = CarIncomeBiz.parseDate(year, row); //处理日期，格式：7月9日
            }

            if (row.startsWith("即时")) {
                //即时接机 即时用车
                if (entity != null) {
                    entityList.add(entity);//如果记录非空，将上一条数据插入集合
                }

                entity = new CarIncome();

                entity.setFast_type("畅享型");//默认经济型，需要到明细中更改
                isOrderBegin = true;
                isOkTime = false;
                isStartAddrOk = false;
                isSetAddrEnd = false;

                //处理开始时间和日期，格式："22:24"
                String startTime = row.substring(4) + ":00";
                entity.setPlat(plat);
                entity.setDate(date);
                entity.setStart_time(startTime);
                entity.setStart_time_real(startTime);
                entity.setId(++orderNum);
                entity.setUPDATE_TIME(new Date());
                entity.setMonth(date.substring(0, 7));
                entity.setState("已完成");
//                System.out.println(time);
                isOkTime = true;
                continue;
            }
            //处理起点地址,"芳源里丙13号楼西北侧"
            else if (isOkTime && !isStartAddrOk) {
                if (row.startsWith("·")) {
                    row = row.replace("·", "");
                }
                entity.setStart_addr(row);
                isStartAddrOk = true;
                continue;
            } else if (isStartAddrOk && !isSetAddrEnd) {
                //处理起点地址, "北京大兴国际机场航站楼"
                if (row.startsWith("·")) {
                    row = row.replace("·", "");
                }
                entity.setEnd_addr(row);
                isSetAddrEnd = true;
                continue;
            } else if (row.startsWith("BZ24")) {
                //  处理订单号："BZ240709102724705004035"
                continue;
            } else if (row.equals("顺")) {
                entity.setType_way("顺路");
                continue;
            } else if (row.startsWith("￥")) {
                //处理订单金额："￥14.3含免佣￥4.00"
                row = row.replace("￥", "");
                if (row.contains("含免佣")) {
                    row = row.substring(0, row.indexOf("含免佣"));
                }
                BigDecimal fare = new BigDecimal(row);
                entity.setFare(fare);

                continue;
            } else {
                if (row.contains("三")) {
//                    System.out.println(row);
                    continue;
                }
                System.out.println(row);

            }
//            System.out.print(",");
            if (row.contains("三") || row.equals("我的行程") || row.equals("全部订单、") || row.equals("无责乘客取消") || row.equals("无责") || row.equals("乘客取消")) {
//                    System.out.println(row);
                continue;
            }
            System.out.println(row);
        }
        if (entity != null) {
            entityList.add(entity);//最后一条记录，将数据插入集合
        }

        int i = 0;
        for (CarIncome carIncome : entityList) {
            System.out.print(++i + ":");
            System.out.println(JSON.toJSONString(carIncome));
            CarIncomeService.insert(carIncome);
        }
    }


    /**
     * 根据地址，更新村镇
     *
     * @param date
     */
    private static void updateEndTown(String date) {
        int updateCount = 0;
        List<CarIncome> rs = null;
        CarIncome condition = new CarIncome();
        condition.setDate(date);
        List<CarIncome> carIncomeList = CarIncomeService.findListByCondition(condition);
        if (carIncomeList == null) {
            return;
        }
        for (CarIncome carIncome : carIncomeList) {
            String endAddr = carIncome.getEnd_addr();
            if (endAddr == null) {
                continue;
            }
            String addressTown = null;
            String addressCounty = null;
            System.out.print(endAddr);
            System.out.print(":::");
            for (String addr : AddressUtil.BEI_JING_LIST) {
                if (endAddr.contains(addr)) {
                    addressTown = addr;
                }
            }
            System.out.print(addressTown);
            System.out.print(":::");
            addressCounty = AddressUtil.BEI_JING_MAP_TOWN_COUNTY.get(addressTown);
            System.out.print(addressCounty);
            System.out.println();

            CarIncome entity = new CarIncome();
            entity.setId(carIncome.getId());
            entity.setEnd_county(addressCounty);
            entity.setEnd_village(addressTown);
            updateCount += CarIncomeService.update(entity);
        }
        System.out.println("更新地址个数：" + updateCount);
    }


    /**
     * //处理滴滴订单不规则字符:
     * 如果包含：北京市丰台区***北京市大兴区***顺路订单，需要修正为***\n
     *
     * @param oriStrArr
     */
    private static String[] handlerDiDiOrderStr(String[] oriStrArr) {
        List<String> strList = new ArrayList<>();
        for (String row : oriStrArr) {
            //如果包含：北京市丰台区***北京市大兴区***顺路订单，需要修正为***\n
            if (row.contains("***北京市")) {
//                System.out.println("原始字符串：");
//                System.out.println(row);

                String[] newRowArr = row.split("\\*\\*\\*");
                int maxLenth = newRowArr.length;
//                System.out.println("处理后字符串：");
                for (String newRow : newRowArr) {
                    if (--maxLenth != 0) {
                        //最后一位不加,其他需要添加
                        newRow = newRow + "***";
                    }
//                    System.out.println(newRow);
                    strList.add(newRow);
                }
            } else if (row.contains("***顺路订单")) {
                //如果包含：北京市大兴区***顺路订单，需要修正为
//                System.out.println("原始字符串：");
//                System.out.println(row);

                String[] newRowArr = row.split("\\*\\*\\*");
                int maxLenth = newRowArr.length;
//                System.out.println("处理后字符串：");
                for (String newRow : newRowArr) {
                    if (--maxLenth != 0) {
                        //最后一位不加,其他需要添加
                        newRow = newRow + "***";
                    }
//                    System.out.println(newRow);
                    strList.add(newRow);
                }
            } else {
                strList.add(row);
            }
        }
        return strList.toArray(new String[strList.size()]);
    }

    /**
     * 解析日期：格式：7月9日
     *
     * @param date
     * @return
     */
    public static String parseDate(String year, String date) {
        //处理日期，格式：7月9日
        if ((date.length() == 4 || date.length() == 5 || date.length() == 6) && date.contains("月") && date.contains("日")) {
            String month = date.substring(0, date.indexOf("月"));
            if (month.length() == 1) {
                month = "0" + month;
            }
            String day = date.substring(date.indexOf("月") + 1, date.indexOf("日"));
            if (day.length() == 1) {
                day = "0" + day;
            }
            date = year + "-" + month + "-" + day;
        }
        return date;
    }

    /**
     * 批量保存
     *
     * @param count      个数
     * @param date       日期
     * @param fastType   订单类型
     * @param bonus_type 奖金类型
     * @return rs
     */
    private static int savePatch(String date, String plat, String fastType, String bonus_type, int count) {
        int rs = 0;
        String STATE_FINISH = "已完成";
        for (int i = 0; i < count; i++) {
            CarIncome entity = new CarIncome();
            entity.setDate(date);
            entity.setMonth(date.substring(0, 7));
            entity.setPlat(plat);
            entity.setFast_type(fastType);
            entity.setBonus_type(bonus_type);
            entity.setFare_bounty(new BigDecimal("0"));
            entity.setFare_cost(new BigDecimal("0"));
            entity.setUPDATE_TIME(new Date());
            entity.setState(STATE_FINISH);
            rs += CarIncomeService.insert(entity);
        }
        return rs;
    }

}
