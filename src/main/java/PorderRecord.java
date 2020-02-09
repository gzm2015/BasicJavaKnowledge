/**
 * @Author:lmk
 * @Date: 2020/1/16   14:41
 * @Description:
 */
public class PorderRecord extends PorderFakeData{


    public static void main(String[] args) {
        int loopNumber = 10;
        String[] array = {"2020-01-11", "2020-01-12"};
        makeInsertSql(loopNumber, array);
        System.out.println();
    }

    public static void makeInsertSql(int loopNumber, String[] array) {

        int length = array.length;
        for (int i = 0; i < loopNumber; i++) {
            String startDate = array[(i + 1) % length];
            String value = makeValue(startDate);
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
                    "VALUES ("+value+");";
            System.out.println(sql);
        }

    }

    public static String makeValue(String startDate) {
        String startTime = getCreateTime(startDate);
        String endTime = getEndTime(startTime);
        String value = "" +
                "3424359899431328," +//`porder_record_id`,
                " 3424355208446368," +//`porder_record_porder_id`,
                " 3363917914177952," +// `porder_record_group_id`,
                " 3363470147928481," +//`porder_record_worker_id`,
                " 1, " +// `porder_record_type`, 缴费类别(0:预交； 1:直缴（正常缴费）；  2:追缴(欠费补缴);  3:租赁)
                "1, " +//`porder_record_pay_method`,缴费方式(0.现金缴费；  1.扫码缴费； 2.自助缴费；3免密支付)
                "1, " +//`porder_record_pay_type`,'支付渠道(0.现金 1.扫码  2.支付宝 3.微信. 4.钱包余额;5.其他;  6银行 7.云闪付)',
                "'P00000708290120191011145947527', " +//`porder_record_serial_no`,
                "105.0000," +// `porder_record_money`, 实际收入
                " NULL, " +//`porder_record_repayment_time`,
                "1," +//`porder_record_finish_type`,'0.追缴完成订单  1.正常完成订单'
                " '"+generateCarID()+"'," +//`porder_record_license`,
                " 0," +//`porder_record_pay_source`,0.收费员 1.停车APP 2.微信公众号 3.扫码  4.扫小票支付 5.银行APP 6.微信小程序 7.刷卡支付'
                " NULL," +//`porder_record_callback_time`,
                " 0, " +// `porder_record_callback_status`, 支付状态:(0:待支付,1:支付成功,2:支付失败,4:支付取消)'
                "NULL, " +//`porder_record_app_user_id`,
                "'"+startTime+"'," +// `create_time`,
                " 3363470147928481, " +//`creator`,
                "NULL, " +//`updator`,
                "'"+endTime+"', " +//`update_time`,
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


}
