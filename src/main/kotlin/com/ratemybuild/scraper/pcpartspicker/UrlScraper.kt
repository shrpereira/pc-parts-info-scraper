package com.ratemybuild.scraper.pcpartspicker

import com.ratemybuild.scraper.config.PAGE_NUM_MASK
import com.ratemybuild.scraper.config.SHOULD_SCRAP_URLS
import com.ratemybuild.scraper.config.ScraperConfiguration
import com.ratemybuild.scraper.pcpartspicker.model.ComponentScrapModel
import com.ratemybuild.scraper.model.ConfigModel
import com.ratemybuild.scraper.pcpartspicker.model.UrlScrapModel
import com.ratemybuild.scraper.util.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.openqa.selenium.By
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

private const val FIRST_PAGE = "1"

@Component
class UrlScraper @Autowired constructor(
		val scrapUtils: ScrapUtils
) : ApplicationRunner {

	override fun run(args: ApplicationArguments?) {
		@Suppress("ConstantConditionIf")
		if (!SHOULD_SCRAP_URLS) return

		ScraperConfiguration.configurationList.forEach {
			scrapUrls(it)
		}
	}

	private fun scrapUrls(model: ConfigModel) {
		val urlsList = mutableListOf<UrlScrapModel>()
		safeChromeDriver { driver ->
			driver.getAndWait(model.url.replace(PAGE_NUM_MASK, FIRST_PAGE))

			val pagesCount = retry(3, 800) {
				driver.findElementByClassName("page-button-row")
						?.findElements(By.className("page-button"))
						?.last()?.text?.toInt()
			} ?: model.pageCount

			for (page in 1..pagesCount) {
				driver.getAndWait(model.url.replace(PAGE_NUM_MASK, page.toString()))

				runBlocking { delay(800) }

				driver.findElement(By.xpath("//a[@class='cc-btn cc-allow']")).safeClick()

				retry(800, 3) {
					driver.findElementById("category_content").let { content ->
						content.findElements(By.tagName("TR")).map {
							it.findElement(By.className("tdname"))
									.findElement(By.tagName("A"))
						}.map {
							it.getAttribute("href")
						}
					}.map {
						UrlScrapModel(it, false)
					}.also {
						urlsList.addAll(it)
					}
				}
			}

			urlsList.convertToJson()?.saveToFile(model.urlFileName)
			updateWithScrapedComponents(model)
		}
	}

	private fun updateWithScrapedComponents(model: ConfigModel) {
		val componentsList = model.componentsFileName.parseFile<List<ComponentScrapModel>>()
				?.toMutableList() ?: mutableListOf()

		scrapUtils.updateUrlsList(model, componentsList)
	}
}