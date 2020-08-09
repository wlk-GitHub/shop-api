package com.fh.shop.api.job;


import com.fh.shop.api.product.biz.ProductService;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class StockJob {

  /* @Resource(name = "productService")
    private ProductService productService;

    @Autowired
    private MailUtil mailUtil;

    //@Scheduled(cron = "0/20 * * * * ?")
    public void checkStock(){
        System.out.println(new Date());
        //获取库存不足的商品列表
        List<Product> stockList = productService.findStockList();
        //生成表格
        StringBuffer productHtml = new StringBuffer();
        productHtml.append("<table border=\"1px\" cellspacing=\"0\" BGCOLOR=\"#7fffd4\" width=\"500px\" cellpadding=\"0\">\n" +
                "        <thead>\n" +
                "            <tr>\n" +
                "                <th>商品名</th>\n" +
                "                <th>价格</th>\n" +
                "                <th>库存</th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <tbody>");

        for (Product product:stockList) {
            productHtml.append(" <tr>\n" +
                    "                <td>"+product.getProductName()+"</td>\n" +
                    "                <td>"+product.getPrice().toString()+"</td>\n" +
                    "                <td>"+product.getStock()+"</td>\n" +
                    "            </tr>");
        }
        productHtml.append("</tbody>\n" +
                "\n" +
                "    </table>");

        String tableHtml = productHtml.toString();
        //发送邮件

    mailUtil.sendMail("2509450456@qq.com","库存不足提醒",tableHtml);

    }*/


}
