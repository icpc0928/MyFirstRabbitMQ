package com.leo.rabbitmq.one;

import com.rabbitmq.client.*;

/**
 * 消費者: 接收消息的
 */
public class Consumer {

    //隊列的名稱
    public static final String QUEUE_NAME = "hello";
    //接收消息
    public static void main(String[] args) throws Exception{
        //創建連接工廠
        ConnectionFactory factory = new ConnectionFactory();
        //
        factory.setHost("127.0.0.1");
        factory.setUsername("root");
        factory.setPassword("root");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //聲明 接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("Message: " + new String(message.getBody()));
        };
        //聲明 取消消息時的回調
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消息消費被中斷");
        };

        /**
         * 消費者消費消息
         * 1.消費哪個隊列
         * 2.消費成功之後是否要自動應答 true自動應答 false手動應答
         * 3.消費者消費的回調
         * 4.消費者取消消費的回調
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }

}
