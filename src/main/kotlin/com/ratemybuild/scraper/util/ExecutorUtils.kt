package com.ratemybuild.scraper.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

fun <T> retry(waitTime: Long, times: Int, block: () -> T): T? {
	var retriedCount = 0

	while (retriedCount < times) {
		runBlocking { delay(waitTime) }
		try {
			return block()
		} catch (e: Exception) {
			retriedCount++
		}
	}

	return null
}

fun safeChromeDriver(options: List<String> = emptyList(), block: (ChromeDriver) -> Unit) {
	val chromeOptions = ChromeOptions().apply {
		options.forEach {
			addArguments(it)
		}
	}

	val driver = ChromeDriver(chromeOptions)
	try {
		block(driver)
	} finally {
		driver.close()
	}
}
