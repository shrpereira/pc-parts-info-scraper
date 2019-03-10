package com.ratemybuild.scraper.scraper

import com.ratemybuild.scraper.BaseComponentScraper
import com.ratemybuild.scraper.util.ACTIVE_DESKTOP_VIDEO_CARDS
import com.ratemybuild.scraper.util.PAGE_NUM_MASK
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DesktopVideoCardScraper : ApplicationRunner, BaseComponentScraper() {

	override fun run(args: ApplicationArguments?) {
		scrap()
	}

	override val websiteUrl: String
		get() = "https://www.newegg.com/Desktop-Graphics-Cards/SubCategory/ID-48/Page-$PAGE_NUM_MASK?PageSize=96&order=BESTMATCH"
	override val resultFilePath: String
		get() = "desktopVideoCards.json"
	override val pagesCount: Int
		get() = 29
	override val isActive: Boolean
		get() = ACTIVE_DESKTOP_VIDEO_CARDS
}