package com.ratemybuild.scraper

import com.ratemybuild.scraper.parser.ManufacturerParserComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class ScraperApplication

fun main(args: Array<String>) {
	runApplication<ScraperApplication>(*args)
}

@Component
class ScraperConfig @Autowired constructor(
		val manufacturerParserComponent: ManufacturerParserComponent
) : ApplicationRunner {

	override fun run(args: ApplicationArguments?) {

//		configurationList.forEach { config ->
//			config.componentsFileName.parseFile<List<ComponentScrapModel>>()?.let {
//				println("${config.componentsFileName} size: ${it.size}")
//			}
//		}

		manufacturerParserComponent.run()
	}
}