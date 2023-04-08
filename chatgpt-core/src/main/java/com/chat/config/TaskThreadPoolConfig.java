package com.chat.config;

import com.chat.properties.ThreadPoolProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池配置类
 *
 */
@Configuration
@EnableAsync
@Slf4j
public class TaskThreadPoolConfig {

	private static final String TASK_THREAD_POOL = "threadExecutor";
	private static final String TASK_SCHEDULER_THREAD_POOL = "taskSchedulerExecutor";

	@Autowired
	private ThreadPoolProperties threadPoolProperties;
	/**
	 * 任务线程池
	 * @return
	 */
	@Bean(name = TASK_THREAD_POOL)
    public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        // 设置最大线程数
        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        // 设置队列容量
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        // 设置线程工厂
        executor.setThreadFactory(new TaskExecutorFactory());
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new TaskExecutorRejectedExecution());
        // 核心线程超时也关闭
		executor.setAllowCoreThreadTimeOut(true);
        // 不等待关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(false);
        return executor;
    }

	/**
	 * 定时任务线程池
	 */
	@Bean(TASK_SCHEDULER_THREAD_POOL)
	public ThreadPoolTaskScheduler getThreadPoolTaskScheduler(){
		int core = threadPoolProperties.getCorePoolSize() / 2;
		// 定时任务线程池
		ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
		// 线程池大小
		executor.setPoolSize(core > 1 ? core : 1);
		// 线程执行前缀
		executor.setThreadNamePrefix(TASK_SCHEDULER_THREAD_POOL);
		// executor.setWaitForTasksToCompleteOnShutdown(true);
		// executor.setAwaitTerminationSeconds(60);
		executor.initialize();
		return executor;
	}

	/**
	 * 任务线程池工厂
	 * @author zhengchunguang
	 * @time 2020/6/18
	 */
	private class TaskExecutorFactory implements ThreadFactory {
		private final AtomicInteger mThreadNum = new AtomicInteger(1);
		@Override
		public Thread newThread(Runnable runable) {
			Thread thread = new Thread(runable,TASK_THREAD_POOL + mThreadNum.incrementAndGet());
			return thread;
		}
		
	}
	
	/**
	 * 任务线程拒绝策略
	 * @author zhengchunguang
	 * @time 2020/6/18
	 */
	private class TaskExecutorRejectedExecution implements RejectedExecutionHandler {
		@Override
		public void rejectedExecution(Runnable runable, ThreadPoolExecutor executor) {
			log.error("任务线程池拒绝 队列大小{} 活跃线程{}", executor.getQueue().size(), executor.getActiveCount());
		}
	}

}
