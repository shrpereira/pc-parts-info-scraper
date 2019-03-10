package com.ratemybuild.scraper.scraper

import com.ratemybuild.scraper.BaseComponentScraper
import com.ratemybuild.scraper.util.ACTIVE_AMD_MOTHERBOARD
import com.ratemybuild.scraper.util.PAGE_NUM_MASK
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class AmdMotherboardScraper : ApplicationRunner, BaseComponentScraper() {

	override fun run(args: ApplicationArguments?) {
		scrap()
	}

	override val websiteUrl: String
		get() = "https://www.newegg.com/AMD-Motherboards/SubCategory/ID-22/Page-$PAGE_NUM_MASK?PageSize=96&order=BESTMATCH"
	override val resultFilePath: String
		get() = "amdMotherboards.json"
	override val pagesCount: Int
		get() = 10
	override val isActive: Boolean
		get() = ACTIVE_AMD_MOTHERBOARD

}