package com.fh.shop.api.pay.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.config.WXConfig;
import com.fh.shop.api.order.mapper.OrderMapper;
import com.fh.shop.api.order.po.Order;
import com.fh.shop.api.paylog.mapper.IPayLogMapper;
import com.fh.shop.api.paylog.po.PayLog;
import com.fh.shop.api.util.BigDecimalUtil;
import com.fh.shop.api.util.DateUtil;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.github.wxpay.sdk.WXPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("payService")
public class IPayServiceImpl implements IPayService {

    @Autowired
    private IPayLogMapper payLogMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ServerResponse createNative(Long memberId) {
        //获取会员对应的支付日志
        String payLogJson = RedisUtil.get(KeyUtil.buildPayLogKey(memberId));
        PayLog payLog = JSONObject.parseObject(payLogJson, PayLog.class);
        //获取支付相关信息
        String outTradeNo = payLog.getOutTradeNo();
        BigDecimal payMoney = payLog.getPayMoney();
        String orderId = payLog.getOrderId();
        //调用微信接口进行统一下单
        try {
        WXConfig config = new WXConfig();
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "飞狐乐购-订单支付");
        int money = BigDecimalUtil.mul(payMoney.toString(), "100").intValue();
        data.put("out_trade_no", outTradeNo);
        data.put("total_fee", money+"");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
           //设置二维码过期时间
            String time = DateUtil.addMinutes(new Date(), 2, DateUtil.FULL_TIME_INFO);
            data.put("time_expire", time);


            Map<String, String> resp = wxpay.unifiedOrder(data);
            String return_code = resp.get("return_code");
            String return_msg = resp.get("return_msg");
            if(!return_code.equals("SUCCESS")){
                return ServerResponse.error(111111,return_msg);//这时候的错误消息是微信平台返回的
            }
            String result_code = resp.get("result_code");
            String err_code_des = resp.get("err_code_des");
            if(!result_code.equals("SUCCESS")){
                return ServerResponse.error(111111,err_code_des);
            }
            String code_url = resp.get("code_url");
            HashMap<String, String> resultMap = new HashMap<>();
            resultMap.put("code_url", code_url);
            resultMap.put("orderId", orderId);
            resultMap.put("totalPrice", payMoney.toString());
            return ServerResponse.success(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }

    }

    @Override
    public ServerResponse queryStatus(Long memberId) {
        WXConfig config = new WXConfig();
        try {
            String payLogJson = RedisUtil.get(KeyUtil.buildPayLogKey(memberId));
            PayLog payLog = JSONObject.parseObject(payLogJson, PayLog.class);
            String orderId = payLog.getOrderId();
            String outTradeNo = payLog.getOutTradeNo();
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", outTradeNo);
            int count = 0;
            while (true){
                Map<String, String> resp = wxpay.orderQuery(data);
                System.out.println(resp);
                String return_code = resp.get("return_code");
                String return_msg = resp.get("return_msg");
                if(!return_code.equals("SUCCESS")){
                    return ServerResponse.error(111111,return_msg);
                }
                String result_code = resp.get("result_code");
                String err_code_des = resp.get("err_code_des");
                if(!result_code.equals("SUCCESS")){
                    return ServerResponse.error(111111,err_code_des);
                }
                String trade_state = resp.get("trade_state");
                if(trade_state.equals("SUCCESS")){
                    //证明支付成功
                    String transaction_id = resp.get("transaction_id");
                    //更新订单
                    Order order = new Order();
                    order.setId(orderId);
                    order.setPayTime(new Date());
                    order.setStatus(SystemConstant.OrderStatus.PAY_SUCCESS);
                    orderMapper.updateById(order);
                    //更新支付日志
                    PayLog payLogInfo = new PayLog();
                    payLogInfo.setOutTradeNo(outTradeNo);
                    payLogInfo.setPayTime(new Date());
                    payLogInfo.setPayStatus(SystemConstant.PayStatus.PAY_SUCCESS);
                    payLogInfo.setTransactionId(transaction_id);
                    payLogMapper.updateById(payLogInfo);
                    //删除redis中的支付日志
                    RedisUtil.delete(KeyUtil.buildPayLogKey(memberId));
                    //响应给客户端
                    return ServerResponse.success();
                }else {
                    Thread.sleep(2000);
                    count++;
                    if(count >60){
                        //未支付成功
                        return ServerResponse.error(ResponseEnum.PAY_IS_FAIL);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }

    }
}
