package com.ratemybuild.scraper.scraper

import com.ratemybuild.scraper.BaseComponentScraper
import com.ratemybuild.scraper.util.ACTIVE_POWER_SUPPLY
import com.ratemybuild.scraper.util.PAGE_NUM_MASK
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class PowerSupplyScraper : ApplicationRunner, BaseComponentScraper() {

	override fun run(args: ApplicationArguments?) {
		scrap()
	}

	override val websiteUrl: String
		get() = "https://www.newegg.com/Power-Supplies/SubCategory/ID-58/Page-$PAGE_NUM_MASK?order=BESTMATCH&PageSize=96"
	override val resultFilePath: String
		get() = "powerSupplies.json"
	override val pagesCount: Int
		get() = 100
	override val isActive: Boolean
		get() = ACTIVE_POWER_SUPPLY

}