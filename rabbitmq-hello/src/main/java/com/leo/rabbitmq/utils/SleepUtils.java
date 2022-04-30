package com.leo.rabbitmq.utils;

public class SleepUtils {
    public static void sleet(int second){
        try{
            Thread.sleep(1000L * second);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
