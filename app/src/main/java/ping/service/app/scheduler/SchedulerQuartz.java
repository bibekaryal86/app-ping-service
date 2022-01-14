package ping.service.app.scheduler;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerQuartz {
    public void start() {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            // schedule to send email
            JobDetail jobDetail = JobBuilder.newJob(SchedulerJob.class)
                    .withIdentity("JOB_DETAIL")
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("TRIGGER")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(10))
                    .forJob(jobDetail)
                    .build();
            scheduler.scheduleJob(trigger);
        } catch (SchedulerException ex) {
            ex.printStackTrace();
        }
    }
}