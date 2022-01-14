package ping.service.app.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.concurrent.CompletableFuture;

import static ping.service.app.util.ConnectorUtil.sendHttpRequest;
import static ping.service.app.util.EndpointUtil.endpointList;

public class SchedulerJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        for (String endpoint : endpointList()) {
            CompletableFuture.runAsync(() -> sendHttpRequest(endpoint));
        }
    }
}
