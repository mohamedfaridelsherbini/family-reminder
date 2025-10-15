package com.mfe.family.infra.scheduling

import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.SimpleScheduleBuilder
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SchedulingConfig {

    @Bean
    fun reminderJobDetail(): JobDetail =
        JobBuilder.newJob(ReminderNotifierJob::class.java)
            .withIdentity("reminderNotifierJob")
            .withDescription("Send due reminders")
            .storeDurably()
            .build()

    @Bean
    fun reminderTrigger(reminderJobDetail: JobDetail): Trigger =
        TriggerBuilder.newTrigger()
            .forJob(reminderJobDetail)
            .withIdentity("reminderNotifierTrigger")
            .withSchedule(
                SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInMinutes(1)
                    .repeatForever()
            )
            .build()
}
