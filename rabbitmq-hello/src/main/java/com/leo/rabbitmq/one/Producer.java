package com.leo.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 生產者: 發消息
 */
public class Producer {
    //隊列名稱
    public static final String QUEUE_NAME = "hello";

    //發消息
    public static void main(String[] args) throws Exception{
        //創建一個連接工廠
        ConnectionFactory factory = new ConnectionFactory();
        //設置工廠IP連接rabbitMQ的隊列
        factory.setHost("127.0.0.1");
        //用戶名
        factory.setUsername("root");
        //密碼
        factory.setPassword("root");

        //創建連接
        Connection connection = factory.newConnection();
        //獲取信道Channel
        Channel channel = connection.createChannel();
        //生成一個隊列 (聲明一個隊列)
        /**
         * queueDeclare(隊列名稱, 是否需要持久化, 是否需要排他, 是否自動刪除, 傳遞隊列的參數)
         * 1.隊列名稱
         * 2.隊列裡面的消息是否要持久化, 默認情況消息存在內存中, 是不能持久化的
         * 3.該隊列是否只供一個消費者進行消費 是否進行消息的共享, true:可以多個消費者消費 默認情況是false 只能一個消費者消費
         * 4.是否自動刪除 最後一個消費者端開連接以後 該隊列是否自動刪除 true:自動刪除 false:不自動刪除
         * 5.其他參數 以後講
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //發消息
        String message = "Hello World";

        /**
         * 發布一個消息
         * 1.發送到哪個交換機
         * 2.路由的key值 本次是隊列的名稱
         * 3.其他參數的信息
         * 4.發送消息的消息體 (轉二進制)
         */

        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("消息發送完畢");
    }
}
