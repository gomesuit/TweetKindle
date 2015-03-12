package myclass.main;

import java.util.List;
import java.util.TimeZone;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import myclass.bo.ShcedulerBo;
import myclass.model.MyScheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistScheduler {
    private static Logger logger = LogManager.getLogger(RegistScheduler.class);
	
    public static void main(String[] args) throws Exception {
    	logger.info("==============Start RegistScheduler==============");
    	try{
        	List<MyScheduler> schedulerList = ShcedulerBo.getSchedulerList();
            for (MyScheduler myScheduler : schedulerList){
            	String jobName = myScheduler.getJobName();
            	String jobclass = myScheduler.getJobClass();
            	String jobSchedule = myScheduler.getJobSchedule();
            	
            	JobDetail jobDetail = createjobDetail(jobName, jobclass);
            	CronTrigger cronTrigger = createCronTrigger(jobName, jobSchedule);
            	createScheduler(jobDetail, cronTrigger);
            }
    	}catch(Exception e){
            logger.error("Exception of RegistScheduler", e);
    	}
    	Thread.sleep(5000);
    }
    
    private static void createScheduler(JobDetail jobDetail, CronTrigger cronTrigger) throws SchedulerException {
    	Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
    	scheduler.scheduleJob(jobDetail, cronTrigger);
    	scheduler.start();
        logger.info("Registration of JOB has been completed successfully. {}", jobDetail, cronTrigger);
	}

	private static CronTrigger createCronTrigger(String triggerName, String triggerSchedule) {		
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(triggerName)
                .withSchedule(CronScheduleBuilder.cronSchedule(triggerSchedule).inTimeZone(TimeZone.getTimeZone("Asia/Tokyo")))
                .startNow()
                .build();
		return trigger;
	}

	@SuppressWarnings("unchecked")
	private static JobDetail createjobDetail(String jobName, String jobclass) throws Exception {
		Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(jobclass);
		
        JobDetail jobDetail = JobBuilder.newJob(clazz)
                .withIdentity(jobName)
                .storeDurably()
                .build();
        
		return jobDetail;
	}
    
}
