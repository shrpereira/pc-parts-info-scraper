package com.ratemybuild.scraper.scraper

import com.ratemybuild.scraper.BaseComponentScraper
import com.ratemybuild.scraper.util.ACTIVE_INTEL_MOTHERBOARD
import com.ratemybuild.scraper.util.PAGE_NUM_MASK
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class IntelMotherboardScraper : ApplicationRunner, BaseComponentScraper() {

	override fun run(args: ApplicationArguments?) {
		scrap()
	}

	override val websiteUrl: String
		get() = "https://www.newegg.com/Intel-Motherboards/SubCategory/ID-280/Page-$PAGE_NUM_MASK?PageSize=96&order=BESTMATCH"
	override val resultFilePath: String
		get() = "intelMotherboards.json"
	override val pagesCount: Int
		get() = 54
	override val isActive: Boolean
		get() = ACTIVE_INTEL_MOTHERBOARD

}