package com.leo.rabbitmq.three;

import com.leo.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

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

        //隊列聲明為持久化
        //表示需要讓queue隊列進行持久化 萬一當機了重啟MQ依然有隊列記錄，但如果之前聲明的隊列不是持久化的,需要先把原先隊列刪除,或者重新創建一個持久化的隊列,不然會出現錯誤
        boolean durable = true;
        //聲明隊列
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
        //從控制台中輸入信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            //消息標記為持久化 BasicProperties => MessageProperties.PERSISTENT_TEXT_PLAIN  (消息要求保存到磁盤上)
            //將消息標記為持久化並不能完全保證不會丟失消息，儘管他告訴rabbitMQ將消息保存到磁盤，但是這裡依然存在消息剛準備儲存在磁盤的時候,但是還沒有儲存完畢，消息還在緩存的一個間隔點。
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生產者發出消息: " + message);

        }
    }

}
