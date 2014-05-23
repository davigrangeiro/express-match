package br.usp.ime.escience.expressmatch.batch;

import java.text.MessageFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskSchedulerRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskSchedulerRunner.class);
	
	@Autowired
	private JobLauncher jobLauncher;

	private Job job;

	public void run() {

		try {
			
			final String params[] = {
					job.getName(),
					new Date().toString(),
					(job.hashCode()) +""
			};
			
			JobParameters param = new JobParametersBuilder()
											.addString("batch_name", params[0])
											.addString("start_time", params[1])
											.addString("hash_id", params[2])
									.toJobParameters();

			LOGGER.info(MessageFormat.format("Batch {0} has been started", params[0]));

			JobExecution execution = jobLauncher.run(job, param);
			
			LOGGER.info(MessageFormat.format("Batch {0} finished with status: {1}", params[0], execution.getStatus()));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}
}
