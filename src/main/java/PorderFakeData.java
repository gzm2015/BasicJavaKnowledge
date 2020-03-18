import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author:lmk
 * @Date: 2020/1/16   10:29
 * @Description: 制造虚假停车数据 和 对应停车收费数据
 */
public class PorderFakeData {


    public static void main(String[] args) {
        int loopNumber = 5;
        String[] array = {"2020-02-17"};
        makeInsertSql(loopNumber, array);
    }


    private static void makeInsertSql(int loopNumber, String[] array) {
        int length = array.length;
        for (int i = 0; i < loopNumber; i++) {
            String startDate = array[(i + 1) % length];
            String createTime = getCreateTime(startDate);
            String endTime = getEndTime(createTime);

            String porderId = "356" + getRandomString(13);
            String carId = generateCarID();
            String value = makeValue(porderId, createTime, endTime, carId);
            String sql = "INSERT INTO `onstreet_parking`.`porder`(" +
                    " `porder_id`," +
                    " `porder_no`," +
                    " `porder_pay_no`," +
                    " `porder_phone`," +
                    " `porder_parking_code`," +
                    " `porder_parking_id`," +
                    " `porder_license`," +
                    " `porder_road_name`," +
                    " `porder_business_id`," +
                    " `porder_road_id`," +
                    " `porder_lochus_name`, " +
                    "`porder_lochus_id`," +
                    " `porder_administration_id`," +
                    " `porder_area_id`," +
                    " `porder_area_name`," +
                    " `porder_apply_type`," +
                    " `porder_apply_time`," +
                    " `porder_geomagnetism_time`," +
                    " `porder_worker_name`," +
                    " `porder_worker_id`," +
                    " `porder_start_time`," +
                    " `porder_end_time`," +
                    " `porder_pic`, " +
                    "`porder_contract_temp_id`," +
                    " `porder_temp_rule_id`," +
                    " `porder_pre_money`," +
                    " `porder_receivable_money`, " +
                    "`porder_money`," +
                    " `entrance_type`," +
                    " `appearance_type`, " +
                    "`porder_arrearage`, " +
                    "`porder_finish_type`, " +
                    "`porder_status`, " +
                    "`create_time`, " +
                    "`creator`, " +
                    "`updator`, " +
                    "`update_time`," +
                    " `porder_app_user_id`," +
                    " `porder_refund_status`, " +
                    "`porder_coupon_id`," +
                    " `porder_car_type`," +
                    " `porder_car_category`," +
                    " `porder_parking_type`," +
                    " `porder_car_energy`," +
                    " `porder_audit_status`," +
                    " `refund_money`, " +
                    "`porder_road_code`, " +
                    "`worker_group_id`, " +
                    "`worker_group_name`," +
                    " `cyx_user_id`, " +
                    "`porder_remission_money`, " +
                    "`porder_discount_amount`," +
                    " `arrears_operate_type`," +
                    " `arrears_operator`) " +
                    "VALUES (" + value + ");";
            System.out.println(sql);

            makeFakePorderRecordSql(porderId, endTime, carId);

            System.out.println();
        }

    }


    private static String makeValue(String porderId, String createTime, String endTime, String carId) {

        String value =
                porderId + "," +//`porder_id`,
                        " '10013201910110958551893'," +//`porder_no`,
                        " '100000860000220191011115158512'," +//`porder_pay_no`,
                        " NULL, " +//`porder_phone`,
                        "'Z00008'," +//`porder_parking_code`,
                        " 3363924853506464, " +//`porder_parking_id`,
                        "'" + carId + "', " +//`porder_license`,
                        "'展会测试1'," +//`porder_road_name`,
                        " 3354054436897184," +//`porder_business_id`,商户id
                        " 3363879910424992, " +//`porder_road_id`,
                        "'展会测试1', " +// `porder_lochus_name`,
                        "3363915397382560, " +//`porder_lochus_id`,中队Id
                        "NULL, " +//`porder_administration_id`,
                        "3355001630310816, " +//`porder_area_id`,
                        "'绵阳市', " +//`porder_area_name`,
                        "0, " +//`porder_apply_type`,订单来源 \'0.POS机  1.APP 2移动采集车 3蓝牙地磁 4侧向视频桩 5高位视频杆
                        "'" + createTime + "', " +//`porder_apply_time`,
                        "'" + createTime + "', " +//`porder_geomagnetism_time`,
                        "'展会收费员1'," +//`porder_worker_name`,
                        " 3363470147928481, " +//`porder_worker_id`,
                        "'" + createTime + "', " +//`porder_start_time`,
                        "'" + endTime + "'," +//`porder_end_time`,
                        " '/park-ecs/download/downloadImage?key=img/1570759125208Z00008.jpg&cacheable=0,/park-ecs/download/downloadImage?key=img/1570759130178Z00008.jpg&cacheable=0,/park-ecs/download/downloadImage?key=img/1570759132602Z00008.jpg&cacheable=0'," +// `porder_pic`,
                        " NULL, " +//`porder_contract_temp_id`,
                        "3369296380101024, " +//`porder_temp_rule_id`,
                        "0.0000, " +//`porder_pre_money`,预收金额
                        "3.0000, " +// `porder_receivable_money`,应收金额
                        "3.0000, " +//`porder_money`,实收金额
                        "1, " +//`entrance_type`,入场方式 0.地磁  1.PDA
                        "1," +// `appearance_type`,出场方式  0.地磁 1.PDA 2.车主自己 3.系统
                        "NULL, " +//`porder_arrearage`,欠费金额
                        "1, " +//`porder_finish_type`,0.追缴完成订单  1.正常完成订单\'; 2退款完成'
                        "2, " +//`porder_status`,0.进行中1.欠费待缴。2已完成
                        "'" + createTime + "'," +//`create_time`,
                        " 3363470147928481," +//`creator`,
                        " NULL, " +//`updator`,
                        "'" + endTime + "'," +//`update_time`,
                        " NULL, " +//`porder_app_user_id`,
                        "0, " +// `porder_refund_status`,无效字段——退款状态0:未申请 1：申请中 2：退款失败 3：申请成功 4：成功
                        "NULL, " +//`porder_coupon_id`,停车券id
                        "1, " +//`porder_car_type`,车辆类型 1.小车 2.中型车 3.大型车 4.新能源小车 5.新能源中型车 6.新能源大型车 7.特殊车辆 8.特种车辆 9.军警车辆
                        "1, " +//`porder_car_category`,车辆类别 0 无牌车 1有牌车
                        "0, " +//`porder_parking_type`,停车类型 0 临时停车 1 租赁停车
                        "1, " +//`porder_car_energy`,新能源车标识 0 不是 1 是
                        "NULL, " +//`porder_audit_status`,欠费审核状态(0:待审核;1:审核通过;2:无效)
                        "NULL, " +// `refund_money`,
                        "'5250000008N', " +//`porder_road_code`,路段编码
                        "3363917914177952, " +//`worker_group_id`,收费点id
                        "'展会测试1'," +//`worker_group_name`,收费点名称
                        " NULL," +// `cyx_user_id`,车娱星用户Id
                        " 0.0000," +//`porder_remission_money`,减免金额（元）
                        "0.00, " +//`porder_discount_amount`, 抵扣金额
                        "0, " +//`arrears_operate_type`, 0 收费员 1 NB地磁  2 系统
                        "'李平'";// `arrears_operator`) //经办人
        return value;
    }


    public static void makeFakePorderRecordSql(String porderId, String startTime, String carId) {

        //默认使停车记录收费开始时间为 停车记录结束时间
        //默认使停车记录更新开始时间为 停车记录收费开始时间
        String recordId = "356" + getRandomString(13);
        String value = makePorderRecordValue(recordId, porderId, startTime, startTime, carId);
        String sql =
                "INSERT INTO `onstreet_parking`.`porder_record`(" +
                        "`porder_record_id`," +
                        " `porder_record_porder_id`," +
                        " `porder_record_group_id`, " +
                        "`porder_record_worker_id`," +
                        " `porder_record_type`, " +
                        "`porder_record_pay_method`," +
                        " `porder_record_pay_type`," +
                        " `porder_record_serial_no`," +
                        " `porder_record_money`, " +
                        "`porder_record_repayment_time`," +
                        " `porder_record_finish_type`," +
                        " `porder_record_license`," +
                        " `porder_record_pay_source`," +
                        " `porder_record_callback_time`," +
                        " `porder_record_callback_status`, " +
                        "`porder_record_app_user_id`," +
                        " `create_time`, " +
                        "`creator`, " +
                        "`updator`, " +
                        "`update_time`, " +
                        "`discount_sum`, " +
                        "`discount_amount`," +
                        " `brokerage`, " +
                        "`porder_record_receivable_money`, " +
                        "`asses_status`, " +
                        "`invoice_id`, " +
                        "`pay_channel`, " +
                        "`pay_model`, " +
                        "`worder_porder_recorde_id`, " +
                        "`porder_record_online`, " +
                        "`porder_account_check`, " +
                        "`asses_object`, " +
                        "`asses_time`, " +
                        "`porder_asses_history_id`, " +
                        "`service_money`, " +
                        "`porder_record_pay_chanle`, " +
                        "`wallet_id`, " +
                        "`porder_record_remission_money`, " +
                        "`query_flag`, " +
                        "`porder_record_refund_status`, " +
                        "`porder_record_refund_id`" +
                        ") " +
                        "VALUES (" + value + ");";
        System.out.println(sql);


    }


    /**
     * porder_record_callback_status 属性改为1 在详情中才会显示  现在创建的全部都是待支付订单
     */
    private static String makePorderRecordValue(String recordId, String porderId, String startTime, String endTime, String carId) {

        String value = "" + recordId +
                "," +//`porder_record_id`,
                porderId + "," +//`porder_record_porder_id`,
                " 3363917914177952," +// `porder_record_group_id`,
                " 3363470147928481," +//`porder_record_worker_id`,
                " 1, " +// `porder_record_type`, 缴费类别(0:预交； 1:直缴（正常缴费）；  2:追缴(欠费补缴);  3:租赁)
                "1, " +//`porder_record_pay_method`,缴费方式(0.现金缴费；  1.扫码缴费； 2.自助缴费；3免密支付)
                "1, " +//`porder_record_pay_type`,'支付渠道(0.现金 1.扫码  2.支付宝 3.微信. 4.钱包余额;5.其他;  6银行 7.云闪付)',
                "'P00000708290120191011145947527', " +//`porder_record_serial_no`,
                "105.0000," +// `porder_record_money`, 实际收入
                " NULL, " +//`porder_record_repayment_time`,
                "1," +//`porder_record_finish_type`,'0.追缴完成订单  1.正常完成订单'
                " '" + carId + "'," +//`porder_record_license`,
                " 0," +//`porder_record_pay_source`,0.收费员 1.停车APP 2.微信公众号 3.扫码  4.扫小票支付 5.银行APP 6.微信小程序 7.刷卡支付'
                " NULL," +//`porder_record_callback_time`,
                " 0, " +// `porder_record_callback_status`, 支付状态:(0:待支付,1:支付成功,2:支付失败,4:支付取消)'
                "NULL, " +//`porder_record_app_user_id`,
                "'" + startTime + "'," +// `create_time`,
                " 3363470147928481, " +//`creator`,
                "NULL, " +//`updator`,
                "'" + endTime + "', " +//`update_time`,
                "0," +//`discount_sum`, 优惠券使用数量
                " NULL," +//`discount_amount`,优惠或抵扣金额
                " 0, " +// `brokerage`, 交易手续费
                "NULL, " +//`porder_record_receivable_money`, 应收金额
                "0," +//`asses_status`, 核销状态(0/null:未核销;1:已核销;2:已撤销)
                " NULL, " +//`invoice_id`,
                "NULL," +//`pay_channel`, 停车场支付渠道(无效)
                " NULL, " +//`pay_model`, 停车场支付方式(无效)
                "NULL, " +//`worder_porder_recorde_id`, 现金代缴记录id
                "NULL," +//`porder_record_online`, 线上、线下缴费标识(线下缴费:Offline 线上缴费:Offline'
                " 0, " +//`porder_account_check`, (0:未对账;1:已对账)'
                "'展会收费员1', " +//`asses_object`,
                "NULL, " +//`asses_time`, 核销时间
                "NULL," +//`porder_asses_history_id`, 核销记录id
                " NULL," +//`service_money`, 手续费
                " 1, " +//`porder_record_pay_chanle`, 支付数据来源 1 道路 2 封闭
                "NULL," +//`wallet_id`,
                " NULL," +//`porder_record_remission_money`, '减免金额（元）'
                " 65," +//`query_flag`, '支付结果查询标识'
                " 0, " +//`porder_record_refund_status`, '退款状态(0:未退款,1:退款中，2:退款成功，3：退款失败)'
                "NULL";//`porder_record_refund_id`退款单号
        return value;
    }


    //依次变化
    //create_time
    //porder_start_time porder_apply_time porder_geomagnetism_time
    //porder_end_time  update_time

    /**
     * 依据给定日期生成一个随机时间
     */
    static String getCreateTime(String startDate) {
        int hour = ThreadLocalRandom.current().nextInt(24);
        String hourChar = hour < 10 ? ("0" + hour) : "" + hour;
        int min = ThreadLocalRandom.current().nextInt(60);
        String minChar = min < 10 ? ("0" + min) : "" + min;
        int second = ThreadLocalRandom.current().nextInt(60);
        String secondChar = second < 10 ? ("0" + second) : "" + second;
        return startDate + " " + hourChar + ":" + minChar + ":" + secondChar;
    }


    static String getEndTime(String createTime) {
        Integer year = Integer.valueOf(createTime.split(" ")[0].split("-")[0]);
        Integer mon = Integer.valueOf(createTime.split(" ")[0].split("-")[1]);
        Integer day = Integer.valueOf(createTime.split(" ")[0].split("-")[2]);
        String hour = createTime.split(" ")[1].split(":")[0];
        String min = createTime.split(" ")[1].split(":")[1];
        String se = createTime.split(" ")[1].split(":")[2];

        LocalTime late = LocalTime.of(Integer.valueOf(hour), Integer.valueOf(min), Integer.valueOf(se));
        LocalTime change = late.plusHours(2).plusMinutes(15);

        if (change.getHour() < late.getHour()) {
            LocalDate da = LocalDate.of(year, mon, day);
            LocalDate changeData = da.plusDays(1);
            return changeData.toString() + " " + change.toString();
        }
        return createTime.split(" ")[0] + " " + change.toString();
    }

    // 车牌号的组成一般为：省份+地区代码+5位数字/字母
    static String generateCarID() {

        char[] provinceAbbr = { // 省份简称 4+22+5+3
                '京', '津', '沪', '渝',
                '冀', '豫', '云', '辽', '黑', '湘', '皖', '鲁', '苏', '浙', '赣',
                '鄂', '甘', '晋', '陕', '吉', '闽', '贵', '粤', '青', '川', '琼',
                '宁', '新', '藏', '桂', '蒙',
                '港', '澳', '台'
        };
        String alphas = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890"; // 26个字母 + 10个数字

        Random random = new Random(); // 随机数生成器

        String carID = "";

        // 省份+地区代码+·  如 湘A· 这个点其实是个传感器，不过加上美观一些
        carID += provinceAbbr[random.nextInt(34)]; // 注意：分开加，因为加的是2个char
        carID += alphas.charAt(random.nextInt(26)) + "·";

        // 5位数字/字母
        for (int i = 0; i < 5; i++) {
            carID += alphas.charAt(random.nextInt(36));
        }
        return carID;
    }


    public static String getRandomString(int length) {
        // 定义length个字符串（A-Z，a-z?0-9）即62位；
        String str ="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //String str = "1234567890";
        // 由Random生成随机随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        // 长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            // 产生0-61的数字
            int number = random.nextInt(9);
            // 产生0-10的数字
            //int number = random.nextInt(10);
            // 将产生的数字通过length次承载到sb
            sb.append(number);
        }
        // 将承载的字符转换成字符串
        return sb.toString();
    }

}
