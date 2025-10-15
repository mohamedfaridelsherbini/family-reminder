package com.mfe.family.infra.scheduling

import org.quartz.JobExecutionContext
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component

@Component
class ReminderNotifierJob : QuartzJobBean() {
    override fun executeInternal(context: JobExecutionContext) {
        // TODO: wire reminder notification logic
    }
}
