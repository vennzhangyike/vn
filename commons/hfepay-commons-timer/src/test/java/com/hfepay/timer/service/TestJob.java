package com.hfepay.timer.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by Kevin on 2016/9/12 0012.
 */
public class TestJob implements Job {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("============="+new Date());
    }
}