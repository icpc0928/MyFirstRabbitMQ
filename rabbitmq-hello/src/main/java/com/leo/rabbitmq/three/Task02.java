package com.leo.rabbitmq.three;

import com.leo.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 消息在手動應答時是不丟失的，消費者斷線後消息將放回隊列中重新消費
 */
public class Task02 {

    //隊列名稱
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();

        //聲明隊列
        channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
        //從控制台中輸入信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("", TASK_QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生產者發出消息: " + message);

        }
    }

}
