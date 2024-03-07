package kr.smhrd.renewen.global.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchedulerConfiguration implements SchedulingConfigurer {

    @Bean(destroyMethod="shutdown")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10); // 스케줄러 스레드 풀 크기 
        scheduler.setThreadNamePrefix("plant-scheduler-"); // 스레드 이름 접두사
        scheduler.initialize(); // 스케줄러 초기화
        return scheduler;
    }

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskScheduler());
		
	}
}
