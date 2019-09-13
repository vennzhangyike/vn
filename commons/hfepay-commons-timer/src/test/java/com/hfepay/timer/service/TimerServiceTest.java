package com.hfepay.timer.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * Created by Kevin on 2016/9/12 0012.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:springContext.xml")
public class TimerServiceTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    TimerService timerService;

    @Test
    public void testTimer(){
        timerService.addCronJob("first","0001",TestJob.class,"0/1 * * * * ? ");
    }
}

