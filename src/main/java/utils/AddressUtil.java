package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @date 2021/7/1
 * 数据来源：http://www.tcmap.com.cn/beijing/
 */
public class AddressUtil {

    /**
     * 北京
     */
    public static Map<String, String> BEI_JING_MAP_TOWN_COUNTY = new HashMap<>();
    /**
     * 北京
     */
    public static List<String> BEI_JING_LIST = new ArrayList<>();

    /**
     * 东城
     */
    public static List<String> DONG_CHENG = new ArrayList<>();
    /**
     * 原始数据字符串
     */
    public static String DONG_CHENG_STR = "东华门街道 景山街道 交道口街道 安定门街道 北新桥街道 东四街道 朝阳门街道 建国门街道 东直门街道 和平里街道 前门街道 崇文门外街道 东花市街道 龙潭街道 体育馆路街道 天坛街道 永定门外街道";

    static {
        String[] addressArray = DONG_CHENG_STR.split(" ");
        for (String address : addressArray) {
            if (address.contains("崇文门外街道")) {
                address = "崇文门";
            }
            if (address.contains("街道")) {
                address = address.replace("街道", "");
            }
            if (address.contains("地区")) {
                address = address.replace("地区", "");
            }
            if (address.contains("镇")) {
                address = address.replace("镇", "");
            }
            DONG_CHENG.add(address);
            BEI_JING_MAP_TOWN_COUNTY.put(address, "东城");
        }
    }

    /**
     * 通州
     */
    public static List<String> TONG_ZHOU = new ArrayList<>();
    /**
     * 原始数据字符串
     */
    public static String TONG_ZHOU_STR = "中仓街道 新华街道 北苑街道 玉桥街道 潞源街道 文景街道 通运街道 九棵树街道 临河里街道 杨庄街道 潞邑街道 永顺镇 梨园镇 宋庄镇 张家湾镇 漷县镇 马驹桥镇 西集镇 台湖镇 永乐店镇 潞城镇 于家务回族乡";

    static {
        String[] addressArray = TONG_ZHOU_STR.split(" ");
        for (String address : addressArray) {
            if (address.contains("街道")) {
                address = address.replace("街道", "");
            }
            if (address.contains("地区")) {
                address = address.replace("地区", "");
            }
            if (address.contains("镇")) {
                address = address.replace("镇", "");
            }
            TONG_ZHOU.add(address);
            BEI_JING_MAP_TOWN_COUNTY.put(address, "通州");
        }
    }

    /**
     * 丰台
     */
    public static List<String> FENG_TAI = new ArrayList<>();
    /**
     * 原始数据字符串
     */
    public static String FENG_TAI_STR = "右安门街道 太平桥街道 西罗园街道 大红门街道 南苑街道 东高地街道 东铁匠营街道 六里桥街道 丰台街道 新村街道 长辛店街道 云岗街道 方庄街道 宛平街道 马家堡街道 和义街道 卢沟桥街道 花乡街道 成寿寺街道 石榴庄街道 玉泉营街道 看丹街道 五里店街道 青塔街道 北宫镇 王佐镇";

    static {
        String[] addressArray = FENG_TAI_STR.split(" ");
        for (String address : addressArray) {
            if (address.contains("街道")) {
                address = address.replace("街道", "");
            }
            if (address.contains("地区")) {
                address = address.replace("地区", "");
            }
            if (address.contains("镇")) {
                address = address.replace("镇", "");
            }
            FENG_TAI.add(address);
            BEI_JING_MAP_TOWN_COUNTY.put(address, "丰台");
        }
    }

    /**
     * 西城
     */
    public static List<String> XI_CHENG = new ArrayList<>();
    /**
     * 原始数据字符串
     */
    public static String XI_CHENG_STR = "西长安街街道 新街口街道 月坛街道 展览路街道 德胜街道 金融街街道 什刹海街道 大栅栏街道 天桥街道 椿树街道 陶然亭街道 广内街道 牛街街道 白纸坊街道 广外街道";

    static {
        String[] addressArray = XI_CHENG_STR.split(" ");
        for (String address : addressArray) {
            if (address.contains("街道")) {
                address = address.replace("街道", "");
            }
            if (address.contains("地区")) {
                address = address.replace("地区", "");
            }
            if (address.contains("镇")) {
                address = address.replace("镇", "");
            }
            XI_CHENG.add(address);
            BEI_JING_MAP_TOWN_COUNTY.put(address, "西城");
        }
    }

    /**
     * 朝阳
     */
    public static List<String> CHAO_YANG_LIST = new ArrayList<>();
    /**
     * 原始数据字符串
     */
    public static String CHAO_YANG_STR = "建外街道 朝外街道 呼家楼街道 三里屯街道 左家庄街道 香河园街道 和平街街道 安贞街道 亚运村街道 小关街道 酒仙桥街道 麦子店街道 团结湖街道 六里屯街道 八里庄街道 双井街道 劲松街道 潘家园街道 垡头街道 南磨房地区 高碑店地区 将台地区 太阳宫地区 大屯街道 望京街道 小红门地区 十八里店地区 平房地区 东风地区 奥运村街道 来广营地区 常营地区 三间房地区 管庄地区 金盏地区 孙河地区 崔各庄地区 东坝地区 黑庄户地区 豆各庄地区 王四营地区 东湖街道 首都机场街道";

    static {
        String[] addressArray = CHAO_YANG_STR.split(" ");
        for (String address : addressArray) {
            if (address.contains("街道")) {
                address = address.replace("街道", "");
            }
            if (address.contains("地区")) {
                address = address.replace("地区", "");
            }
            if (address.contains("镇")) {
                address = address.replace("镇", "");
            }
            CHAO_YANG_LIST.add(address);
            BEI_JING_MAP_TOWN_COUNTY.put(address, "朝阳");
        }
    }

    /**
     * 昌平
     */
    public static List<String> CHANG_PING_LIST = new ArrayList<>();
    /**
     * 原始数据字符串
     */
    public static String CHANG_PING_STR = "城北街道 南口地区 马池口地区 沙河地区 城南街道 东小口地区 天通苑北街道 天通苑南街道 霍营街道 回龙观街道 龙泽园街道 史各庄街道 阳坊镇 小汤山镇 南邵镇 崔村镇 百善镇 北七家镇 兴寿镇 流村镇 十三陵镇 延寿镇";

    static {
        String[] addressArray = CHANG_PING_STR.split(" ");
        for (String address : addressArray) {
            if (address.contains("街道")) {
                address = address.replace("街道", "");
            }
            if (address.contains("地区")) {
                address = address.replace("地区", "");
            }
            if (address.contains("镇")) {
                address = address.replace("镇", "");
            }
            CHANG_PING_LIST.add(address);
            BEI_JING_MAP_TOWN_COUNTY.put(address, "昌平");
        }
    }


    /**
     * DA_XING_LIST
     */
    public static List<String> DA_XING_LIST = new ArrayList<>();
    /**
     * DA_XING_STR
     */
    public static String DA_XING_STR = "兴丰街道 林校路街道 清源街道 亦庄地区 黄村地区 旧宫地区 西红门地区 瀛海镇（瀛海地区） 观音寺街道 天宫院街道 高米店街道 青云店镇 采育镇 安定镇 礼贤镇 榆垡镇 庞各庄镇 北臧村镇 魏善庄镇 长子营镇";

    static {
        //大兴
        String[] addressArray = DA_XING_STR.split(" ");
        for (String address : addressArray) {
//            System.out.println(address);
            if (address.contains("亦庄")) {
                continue;
            }

            if (address.contains("瀛海镇")) {
                address = "瀛海";
            } else if (address.contains("街道")) {
                address = address.replace("街道", "");
            } else if (address.contains("地区")) {
                address = address.replace("地区", "");
            } else if (address.contains("镇")) {
                address = address.replace("镇", "");
            }
            DA_XING_LIST.add(address);
            BEI_JING_MAP_TOWN_COUNTY.put(address, "大兴");
        }
//        for (String address : DA_XING_LIST) {
//            System.out.println(address);
//        }
    }

    /**
     * YI_ZHUANG
     */
    public static List<String> YI_ZHUANG = new ArrayList<>();
    /**
     * YI_ZHUANG_STR
     */
    public static String YI_ZHUANG_STR = "亦庄";

    static {
        //大兴
        String[] addressArray = YI_ZHUANG_STR.split(" ");
        for (String address : addressArray) {
//            System.out.println(address);
            if (address.contains("亦庄")) {
                address = "亦庄";
            }

            YI_ZHUANG.add(address);
            BEI_JING_MAP_TOWN_COUNTY.put(address, "亦庄");
        }
//        for (String address : DA_XING_LIST) {
//            System.out.println(address);
//        }
    }


    static {
        BEI_JING_LIST.addAll(DA_XING_LIST);
        BEI_JING_LIST.addAll(YI_ZHUANG);
        BEI_JING_LIST.addAll(CHANG_PING_LIST);
        BEI_JING_LIST.addAll(CHAO_YANG_LIST);
        BEI_JING_LIST.addAll(XI_CHENG);
        BEI_JING_LIST.addAll(DONG_CHENG);
        BEI_JING_LIST.addAll(FENG_TAI);
        BEI_JING_LIST.addAll(TONG_ZHOU);
    }


    public static void main(String[] args) {
        for (String address : CHAO_YANG_LIST) {
            System.out.println(address);
        }
    }

}
