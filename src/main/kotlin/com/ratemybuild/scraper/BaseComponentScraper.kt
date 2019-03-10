package com.ratemybuild.scraper

import com.google.gson.GsonBuilder
import com.ratemybuild.scraper.model.ComponentProperty
import com.ratemybuild.scraper.model.ComputerComponent
import com.ratemybuild.scraper.util.JSON_FOLDER
import com.ratemybuild.scraper.util.PAGE_NUM_MASK
import com.ratemybuild.scraper.util.ScraperUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import java.io.PrintWriter
import kotlin.system.measureTimeMillis

abstract class BaseComponentScraper {

	abstract val websiteUrl: String
	abstract val resultFilePath: String
	abstract val pagesCount: Int
	abstract val isActive: Boolean

	internal fun scrap() {
		if (!isActive) return

		val componentList = mutableListOf<ComputerComponent>()

		val driver = ChromeDriver(ScraperUtils.getDriverOptions())

		try {
			for (page in 1..pagesCount) {

				driver.getAndWait("https://www.newegg.com/CPUs-Processors/Category/ID-34")
				driver.getAndWait(websiteUrl.replace(PAGE_NUM_MASK, page.toString()))

				driver.findElementByClassName("centerPopup-close").safeClick()
				driver.findElementById("ListView").safeClick()

				driver.getEachItemUrl { url ->
					getComponentData(url, componentList)
					runBlocking { delay((Math.random() * 3000).toLong()) }
				}
			}
		} finally {
			driver.close()
		}

		if (!componentList.isNullOrEmpty()) saveDataToFile(componentList)
	}

	private fun getComponentData(url: String, componentList: MutableList<ComputerComponent>) {
		measureTimeMillis {
			val properties = mutableListOf<ComponentProperty>()
			val driver = ChromeDriver()

			try {
				driver.getAndWait(url)

				if (driver.findElementById("Details_Tab").safeClick()) {
					val title = driver.findElementByClassName("sectionTitle")
							?.findElement(By.tagName("span"))?.text ?: ""

					driver.findElementById("Specs").findElements(By.tagName("dl")).map {
						ComponentProperty(
								it.findElement(By.tagName("dt")).text,
								it.findElement(By.tagName("dd")).text
						)
					}.forEach {
						if (!it.name.isBlank() && !it.value.isBlank()) {
							properties.add(it)
						}
					}

					if (properties.isNotEmpty()) {
						componentList.add(ComputerComponent(title, url, properties))
						println(title)
					}
				}
			} catch (e: Exception) {
				println("Erro: ${e.message}")
			} finally {

			}
		}.also {
			println("Elapsed time: ${it.toDouble() / 1000}")
		}
	}

	private fun ChromeDriver.getAndWait(url: String) {
		this.get(url)
		ScraperUtils.waitUntilPageIsReady(this)
	}

	private fun WebElement?.safeClick(): Boolean = try {
		this?.click()
		true
	} catch (e: Exception) {
		false
	}

	private fun ChromeDriver.getEachItemUrl(block: (String) -> Unit) {
		this.findElementsByClassName("item-title").map { itemTitle ->
			itemTitle.getAttribute("href")
		}.forEach {
			block(it)
		}
	}

	private fun convertToJson(cpuList: MutableList<ComputerComponent>): String? {
		val gson = GsonBuilder()
				.setPrettyPrinting()
				.create()

		return gson.toJson(cpuList)
	}

	private fun saveDataToFile(cpuList: MutableList<ComputerComponent>) {
		val jsonString = convertToJson(cpuList)

		PrintWriter(JSON_FOLDER + resultFilePath).use { it.println(jsonString) }
	}
}