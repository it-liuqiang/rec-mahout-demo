package com.example.rec.core;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;
import java.util.concurrent.*;

public class Main {


    static Map<String, List<String>> batchMap = Collections.synchronizedMap(new WeakHashMap<>());
    public static Integer batchSize = 300;
    public static Integer waitTime = 10;

    public static boolean timerIsRun = false;
    private static Timer timer = new Timer();

    private final String[] tmp = new String[10000];

    private static TimerTask task = new TimerTask() {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            // 需要每秒执行的代码
            String[] dataBaseNames = batchMap.keySet().toArray(new String[0]);
            if (dataBaseNames.length > 0) {
                Arrays.stream(dataBaseNames).parallel().forEach(dataBaseName -> {
                    List<String> sqlLists = batchMap.remove(dataBaseName); //获取sql数据,清理batchMap中的值
                    if (CollectionUtil.isNotEmpty(sqlLists)){
//                        System.out.println(sqlLists.size());
                        System.out.println(JSONObject.toJSONString(sqlLists));
                        sqlLists.clear();
                    }
                });
                System.out.println("timer执行时间=====" + (System.currentTimeMillis() - startTime));

            }
        }
    };


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Random random = new Random();
            StringBuffer sql = new StringBuffer("update `dev2_imcenter`.`tiens_user` set `country`='HU',`user_status`=0,`language`='zh-CN',`source`=3,`sponsor_modified_flag`=0,`modify_time`=TIMESTAMP('2025-03-14 16:21:26'),`is_turntable`=0,`is_deleted`=0,`middle_code`='',`possible_enable`=0,`member`=0,`disabled`=0,`head_img`='https://v-moment-prod.jikeint.com/im/2023063009433107113-1080-1080.png',`nick_name`='user_51ECF0DB111111',`member_level`=0,`sex`=0,`member_count`=0,`is_experience_membership`=0,`joyo_code`='900947901',`fans`=0,`is_mix`=0,`notification_label`=0,`last_login_time`=TIMESTAMP('2025-03-14 16:13:25'),`create_by`='0',`contacts_enable`=0,`followers`=0,`create_time`=TIMESTAMP('2025-02-25 19:13:04'),`is_update`=1 where `acc_id`='51ecf0db40e64cb29d82cbd0a4376f0b'");
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            executor.execute(() -> {
                osExecuteSql("db" + finalI, sql.toString());
                try {
                    Thread.sleep(random.nextInt(9001) + 1000);
//                    Thread.sleep(1000 );
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }


    public static void osExecuteSql(String sql, String dataBaseName) {
//        List<String> orDefault = batchMap.getOrDefault(dataBaseName, new LinkedList<>());
//
//        orDefault.add(sql);
//        batchMap.put(dataBaseName, orDefault);
        batchMap.computeIfAbsent(dataBaseName, k -> new ArrayList<>()).add(sql);


        if (!timerIsRun) {
            System.out.println("初始化timer----osExecuteSql");
            timerIsRun = true;
            timer.schedule(task, 0, 5000); //1秒钟执行一次sql语句
        }

        if (batchMap.get(dataBaseName).size() >= batchSize) {
            try {
                System.out.println(batchMap.get(dataBaseName).size());
                System.out.println("-------------------------------------重置waitTime------------------------------------------------------");
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
