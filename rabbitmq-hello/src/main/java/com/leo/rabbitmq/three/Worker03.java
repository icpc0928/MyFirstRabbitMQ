package com.leo.rabbitmq.three;

import com.leo.rabbitmq.utils.RabbitMqUtils;
import com.leo.rabbitmq.utils.SleepUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Worker03 {

    //隊列名稱
    public static final String TASK_QUEUE_NAME = "ack_queue";

    //接收消息
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();

        System.out.println("Worker03等待接收消息處理...(處理時間長)");

        //消息的接收
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            //要求接收到消息之後沉睡30S (模擬處理時間)
            SleepUtils.sleet(30);
            System.out.println("接收到的消息: " + new String(message.getBody(), StandardCharsets.UTF_8));

            //結束後要記得手動應答(因為我們採用手動應答)
            /**
             * 1. 消息的標記 tag (表示列隊中消息的標籤)
             * 2. 是否批量應答 (批量應答有可能使消息丟失,就不用惹) --> 不批量應答信道中的消息,處理一個應答一個
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        //消息接收被取消時 執行下面的內容
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println(consumerTag + " 消費者取消消費接口回調邏輯");
        };

        //採用手動應答 autoAck
        boolean autoAck = false;

        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, cancelCallback);

    }
}
