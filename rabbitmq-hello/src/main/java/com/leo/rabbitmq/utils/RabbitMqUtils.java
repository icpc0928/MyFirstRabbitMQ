package com.leo.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 此類 為連接工廠創建信道(channel)的工具類
 */
public class RabbitMqUtils {

    public static Channel getChannel() throws Exception{
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

        return channel;
    }
}
