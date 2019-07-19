package com.ratemybuild.scraper.pcpartspicker

import com.ratemybuild.scraper.config.SHOULD_SCRAP_COMPONENTS
import com.ratemybuild.scraper.config.ScraperConfiguration
import com.ratemybuild.scraper.pcpartspicker.model.ComponentScrapModel
import com.ratemybuild.scraper.pcpartspicker.model.PropertyScrapModel
import com.ratemybuild.scraper.pcpartspicker.model.UrlScrapModel
import com.ratemybuild.scraper.util.*
import kotlinx.coroutines.*
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

private const val URLS_PER_SCOPE = 1500

@Component
class BaseComponentScraper @Autowired constructor(
		val scrapUtils: ScrapUtils
) : ApplicationRunner {

	override fun run(args: ApplicationArguments?) {
		@Suppress("ConstantConditionIf")
		if (!SHOULD_SCRAP_COMPONENTS) return

		ScraperConfiguration.configurationList.forEach { configModel ->
			println("Starting file: ${configModel.urlFileName}")
			val componentsList = configModel.componentsFileName.parseFile<List<ComponentScrapModel>>()?.toMutableList()
					?: mutableListOf()
			val urlsModelList = configModel.urlFileName.parseFile<List<UrlScrapModel>>()?.filter { !it.scraped }

			safeLet(componentsList, urlsModelList) { compList, urlsList ->

				try {
					val scopedUrlsList = mutableListOf<MutableList<UrlScrapModel>>()
					var tempUrlsList: MutableList<UrlScrapModel>? = null
					urlsList.forEachIndexed { index, model ->
						if (index % URLS_PER_SCOPE == 0) {
							tempUrlsList?.let { scopedUrlsList.add(it) }
							tempUrlsList = mutableListOf()
						}

						tempUrlsList?.add(model)
					}.also { tempUrlsList?.let { scopedUrlsList.add(it) } }

					var scopeCount = 0
					val jobList = mutableListOf<Job>()

					scopedUrlsList.forEach { list ->
						scopeCount++
						val scopeNumber = scopeCount
						println("Initializing Scope $scopeNumber")
						GlobalScope.launch {
							measureTimeMillis {
								safeChromeDriver { driver ->
									list.forEach { model ->
										scrapComponent(model, driver)?.let { compList.add(it) }
									}
								}
							}.also { println("Time Elapsed for Scope $scopeNumber: ${it / 1000}") }
						}.let { jobList.add(it) }
					}

					runBlocking { jobList.forEach { it.join() } }
				} catch (e: Exception) {
					e.printStackTrace()
				}

				compList.convertToJson()?.saveToFile(configModel.componentsFileName)

				scrapUtils.updateUrlsList(configModel, compList)
				println("Finished file: ${configModel.urlFileName}")
			}
		}
	}

	private fun scrapComponent(urlScrapModel: UrlScrapModel, driver: ChromeDriver): ComponentScrapModel? {
		val properties = mutableListOf<PropertyScrapModel>()
		driver.getAndWait(urlScrapModel.url)
		runBlocking { delay(800) }

		retry(800, 3) {
			driver.findElement(By.xpath("//div[@class='specs block']")).let { specs ->
				val specsList = specs.text.split("\n")
				val specsTitlesList = specs.findElements(By.tagName("H4")).map { it.text.toLowerCase().capitalize() }


				var title = ""
				var attributes = mutableListOf<String>()

				specsList.forEachIndexed { index, text ->
					if (index > 0) { // Avoid first item == Specifications
						if (specsTitlesList.contains(text.toLowerCase().capitalize())) {
							if (title.isNotBlank()) {
								properties.add(PropertyScrapModel(title, attributes.joinToString(",")))
								title = ""
								attributes = mutableListOf()
							}

							title = text.toLowerCase().capitalize()
						} else {
							attributes.add(text)
						}
					}
				}
			}
		}

		return ComponentScrapModel(urlScrapModel.url, properties)
	}
}