package com.ratemybuild.scraper.util

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.WebDriverWait

const val PAGE_NUM_MASK = "#PAGE_NUMBER"
const val JSON_FOLDER = "scraped/"

const val ACTIVE_DESKTOP_PROCESSORS = true

const val ACTIVE_DESKTOP_VIDEO_CARDS = false
const val ACTIVE_WORKSTATION_VIDEO_CARDS = false

const val ACTIVE_DESKTOP_MEMORY = false

const val ACTIVE_AMD_MOTHERBOARD = false
const val ACTIVE_INTEL_MOTHERBOARD = false

const val ACTIVE_POWER_SUPPLY = false

class ScraperUtils {

	companion object {
		fun waitUntilPageIsReady(driver: ChromeDriver) {
			val executor = driver as JavascriptExecutor
			WebDriverWait(driver, 1)
					.until { executor.executeScript("return document.readyState") == "complete" }
		}

		fun getDriverOptions() = ChromeOptions().apply {
			addArguments("--incognito")
			addArguments("--no-sandbox")
			addArguments("--disable-dev-shm-usage")
			addArguments("--allow-running-insecure-content")
			addArguments("--allow-insecure-localhost")
		}
	}
}