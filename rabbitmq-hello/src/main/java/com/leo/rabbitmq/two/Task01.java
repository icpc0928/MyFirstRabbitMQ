package com.leo.rabbitmq.two;

import com.leo.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * 生產者 發送大量消息
 */
public class Task01 {
    //隊列名稱
    public static final String QUEUE_NAME = "hello";

    //發送大量消息
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();

        //隊列的聲明
        /**
         * queueDeclare(隊列名稱, 是否需要持久化, 是否需要排他, 是否自動刪除, 傳遞隊列的參數)
         * 1.隊列名稱
         * 2.隊列裡面的消息是否要持久化, 默認情況消息存在內存中, 是不能持久化的
         * 3.該隊列是否只供一個消費者進行消費 是否進行消息的共享, true:可以多個消費者消費 默認情況是false 只能一個消費者消費
         * 4.是否自動刪除 最後一個消費者端開連接以後 該隊列是否自動刪除 true:自動刪除 false:不自動刪除
         * 5.其他參數 以後講
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for(int i = 0; i < 100000; i++){
            String msg = String.valueOf(i);
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("發送消息完成: " + msg);
        }


        //從控制台當中接受信息
        Scanner scanner = new Scanner(System.in);
        System.out.println("輸入要發送的消息");
        while(scanner.hasNext()){
            String message = scanner.next();
            /**
             * 發布一個消息
             * 1.發送到哪個交換機
             * 2.路由的key值 本次是隊列的名稱
             * 3.其他參數的信息
             * 4.發送消息的消息體 (轉二進制)
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("發送消息完成: " + message);
        }


    }
}
