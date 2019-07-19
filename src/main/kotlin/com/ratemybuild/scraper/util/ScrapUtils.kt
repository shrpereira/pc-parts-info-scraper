package com.ratemybuild.scraper.util

import com.ratemybuild.scraper.pcpartspicker.model.ComponentScrapModel
import com.ratemybuild.scraper.model.ConfigModel
import com.ratemybuild.scraper.pcpartspicker.model.UrlScrapModel
import org.springframework.stereotype.Component

@Component
class ScrapUtils {

	fun updateUrlsList(configModel: ConfigModel, compList: MutableList<ComponentScrapModel>) {
		val freshUrlList = configModel.urlFileName.parseFile<List<UrlScrapModel>>()
				?: mutableListOf()
		val cacheUrlsList = freshUrlList.map { urlModel ->
			if (!urlModel.scraped && compList.any { it.url == urlModel.url })
				urlModel.copy(scraped = true)
			else urlModel
		}

		cacheUrlsList.toMutableList().convertToJson()?.saveToFile(configModel.urlFileName)
		val scrapedCount = cacheUrlsList.filter { it.scraped }.size.toString()

		println("${configModel.urlFileName} - Found: ${cacheUrlsList.size} | Scraped: $scrapedCount")
	}
}