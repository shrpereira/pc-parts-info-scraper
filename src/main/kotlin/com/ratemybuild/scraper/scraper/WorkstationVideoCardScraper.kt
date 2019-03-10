package com.ratemybuild.scraper.scraper

import com.ratemybuild.scraper.BaseComponentScraper
import com.ratemybuild.scraper.util.ACTIVE_WORKSTATION_VIDEO_CARDS
import com.ratemybuild.scraper.util.PAGE_NUM_MASK
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class WorkstationVideoCardScraper : ApplicationRunner, BaseComponentScraper() {

	override fun run(args: ApplicationArguments?) {
		scrap()
	}

	override val websiteUrl: String
		get() = "https://www.newegg.com/Workstation-Graphics-Cards/SubCategory/ID-449/Page-$PAGE_NUM_MASK?PageSize=96&order=BESTMATCH"
	override val resultFilePath: String
		get() = "workstationVideoCards.json"
	override val pagesCount: Int
		get() = 6
	override val isActive: Boolean
		get() = ACTIVE_WORKSTATION_VIDEO_CARDS
}