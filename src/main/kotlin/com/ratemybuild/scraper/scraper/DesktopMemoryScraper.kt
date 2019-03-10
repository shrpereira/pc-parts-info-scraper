package com.ratemybuild.scraper.scraper

import com.ratemybuild.scraper.BaseComponentScraper
import com.ratemybuild.scraper.util.ACTIVE_DESKTOP_MEMORY
import com.ratemybuild.scraper.util.PAGE_NUM_MASK
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DesktopMemoryScraper : ApplicationRunner, BaseComponentScraper() {

	override fun run(args: ApplicationArguments?) {
		scrap()
	}

	override val websiteUrl: String
		get() = "https://www.newegg.com/Desktop-Memory/SubCategory/ID-147/Page-$PAGE_NUM_MASK?PageSize=96&order=BESTMATCH"
	override val resultFilePath: String
		get() = "desktopMemories.json"
	override val pagesCount: Int
		get() = 100
	override val isActive: Boolean
		get() = ACTIVE_DESKTOP_MEMORY

}