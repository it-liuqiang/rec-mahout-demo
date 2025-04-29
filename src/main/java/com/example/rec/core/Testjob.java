package com.example.rec.core;

import cn.hutool.core.date.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.Date;

@Component

public class Testjob {

    // 上一次任务完成后等待 5 秒再执行
    @Scheduled(fixedDelay = 100)
    public void fixedDelayTask() throws InterruptedException {
        Thread.sleep(5000);

        System.out.println("fixedDelayTask 执行：" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//        System.out.println("fixedDelayTask 执行：" + System.currentTimeMillis());
    }

}
