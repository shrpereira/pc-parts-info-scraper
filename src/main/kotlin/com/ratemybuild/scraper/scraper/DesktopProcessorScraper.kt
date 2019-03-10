package com.ratemybuild.scraper.scraper

import com.ratemybuild.scraper.BaseComponentScraper
import com.ratemybuild.scraper.util.ACTIVE_DESKTOP_PROCESSORS
import com.ratemybuild.scraper.util.PAGE_NUM_MASK
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DesktopProcessorScraper : ApplicationRunner, BaseComponentScraper() {

	override fun run(args: ApplicationArguments?) {
		scrap()
	}

	override val websiteUrl: String
		get() = "https://www.newegg.com/Processors-Desktops/SubCategory/ID-343/Page-$PAGE_NUM_MASK?Tid=7671&PageSize=96&order=BESTMATCH"
	override val resultFilePath: String
		get() = "desktopProcessors.json"
	override val pagesCount: Int
		get() = 12
	override val isActive: Boolean
		get() = ACTIVE_DESKTOP_PROCESSORS
}
