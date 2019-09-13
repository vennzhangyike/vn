package com.hfepay.timer.service;

/**
 * Created by Kevin on 2016/9/12 0012.
 */
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * corn语法请参考  http://cron.qqe2.com/
 * */
public class TimerService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Scheduler scheduler;

    public TimerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    private SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow();

    public boolean removeJob(String jobId, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobId, jobGroup);
        return scheduler.deleteJob(jobKey);
    }

    /**
     * 添加一个每月运行一次的job
     * @param jobId
     * @param jobGroup
     * @param jobClass
     */
    public void addMonthJob(String jobId, String jobGroup,  Class<? extends Job> jobClass) {

        JobDetail job = JobBuilder.newJob(jobClass).withIdentity(jobId, jobGroup).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobId, jobGroup).withSchedule(CronScheduleBuilder.cronSchedule("0 45 1 1 * ?").withMisfireHandlingInstructionFireAndProceed()).forJob(job).build();
        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
             logger.error(e.getMessage(),e);
        }
    }

    /**
     * 添加一个循环的按cron 表达式运行的job
     * @param jobId
     * @param jobGroup
     * @param jobClass
     * @param cron  格式 sec min hour day month week   e.g. 每月1号，1点45分运行   "0 45 1 1 * ?"
     */
    public void addCronJob(String jobId, String jobGroup, Class<? extends Job> jobClass, String cron) {

        JobDetail job = JobBuilder.newJob(jobClass).withIdentity(jobId, jobGroup).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobId, jobGroup).withSchedule(CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionFireAndProceed()).forJob(job).build();
        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(),e);
        }
    }
}
