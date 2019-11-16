package com.norman.job51.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
//@Component
public class TaskTest {
    /**
     * cron表达式
     * 没5秒执行一次
     */
    @Scheduled(cron = "0/5 * * * * *")
    public void test() {
        System.out.println("+++++++++++++");
    }
}
