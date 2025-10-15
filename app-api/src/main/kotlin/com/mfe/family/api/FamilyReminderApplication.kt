package com.mfe.family.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.mfe.family"])
class FamilyReminderApplication

fun main(args: Array<String>) {
    runApplication<FamilyReminderApplication>(*args)
}
