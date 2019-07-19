package com.ratemybuild.scraper.parser

import com.ratemybuild.scraper.config.ScraperConfiguration
import com.ratemybuild.scraper.parser.model.MANUFACTURER_JSON_PATH
import com.ratemybuild.scraper.parser.model.ManufacturerModel
import com.ratemybuild.scraper.pcpartspicker.model.ComponentScrapModel
import com.ratemybuild.scraper.util.convertToJson
import com.ratemybuild.scraper.util.parseFile
import com.ratemybuild.scraper.util.safeFirst
import com.ratemybuild.scraper.util.saveToFile
import org.springframework.stereotype.Component

@Component
class ManufacturerParserComponent {

	fun run() {
		val manufacturerList = mutableListOf<ManufacturerModel>()

		ScraperConfiguration.configurationList.forEach { config ->

			config.componentsFileName.parseFile<List<ComponentScrapModel>>()?.forEach { component ->
				component.properties.safeFirst { it.name == "Manufacturer" }?.let { property ->
					if (manufacturerList.none { it.name == property.value }) {
						manufacturerList.add(ManufacturerModel(property.value))
					}
				}
			}
		}

		manufacturerList.convertToJson()?.saveToFile(MANUFACTURER_JSON_PATH)
	}
}