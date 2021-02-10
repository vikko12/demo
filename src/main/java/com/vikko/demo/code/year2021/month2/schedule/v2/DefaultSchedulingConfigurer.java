package com.vikko.demo.code.year2021.month2.schedule.v2;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;

/**
 * @author: vikko
 * @Date: 2021/2/5 18:12
 * @Description:
 */
@EnableScheduling
public class DefaultSchedulingConfigurer implements SchedulingConfigurer {

	private final String FIELD_SCHEDULED_FUTURES = "scheduledFutures";
	private ScheduledTaskRegistrar taskRegistrar;
	private Set<ScheduledFuture<?>> scheduledFutures = null;
	private Map<String, ScheduledFuture<?>> taskFutures = new ConcurrentHashMap<String, ScheduledFuture<?>>();

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		this.taskRegistrar = taskRegistrar;
	}

	@SuppressWarnings("unchecked")
	private Set<ScheduledFuture<?>> getScheduledFutures() {
		if (scheduledFutures == null) {
			try {
				scheduledFutures = (Set<ScheduledFuture<?>>) BeanUtils
						.getProperty(taskRegistrar, FIELD_SCHEDULED_FUTURES);
			} catch (NoSuchFieldException e) {
				throw new SchedulingException("not found scheduledFutures field.");
			}
		}
		return scheduledFutures;
	}

	/**
	 * 添加任务
	 */
	public void addTriggerTask(String taskId, TriggerTask triggerTask) {
		if (taskFutures.containsKey(taskId)) {
			throw new SchedulingException("the taskId[" + taskId + "] was added.");
		}
		TaskScheduler scheduler = taskRegistrar.getScheduler();
		ScheduledFuture<?> future = scheduler
				.schedule(triggerTask.getRunnable(), triggerTask.getTrigger());
		getScheduledFutures().add(future);
		taskFutures.put(taskId, future);
	}

	/**
	 * 取消任务
	 */
	public void cancelTriggerTask(String taskId) {
		ScheduledFuture<?> future = taskFutures.get(taskId);
		if (future != null) {
			future.cancel(true);
		}
		taskFutures.remove(taskId);
		getScheduledFutures().remove(future);
	}

	/**
	 * 重置任务
	 */
	public void resetTriggerTask(String taskId, TriggerTask triggerTask) {
		cancelTriggerTask(taskId);
		addTriggerTask(taskId, triggerTask);
	}

	/**
	 * 任务编号
	 */
	public Set<String> taskIds() {
		return taskFutures.keySet();
	}

	/**
	 * 是否存在任务
	 */
	public boolean hasTask(String taskId) {
		return this.taskFutures.containsKey(taskId);
	}

	/**
	 * 任务调度是否已经初始化完成
	 */
	public boolean inited() {
		return this.taskRegistrar != null && this.taskRegistrar.getScheduler() != null;
	}
}
