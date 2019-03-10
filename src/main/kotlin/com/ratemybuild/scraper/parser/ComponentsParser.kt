package com.ratemybuild.scraper.parser

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ratemybuild.scraper.model.ComputerComponent
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.io.File

@Component
class ComponentsParser : ApplicationRunner {

	private val listOfFiles = mutableListOf<String>().apply {
		//		add("json/intelMotherboards.json")
//		add("json/amdMotherboards.json")
//		add("json/desktopVideoCards.json")
//		add("json/desktopMemories.json")
//		add("json/desktopProcessors.json")
//		add("json/powerSupplies.json")
	}

	override fun run(args: ApplicationArguments?) {
		listOfFiles.forEach {
			printBrands(it)
		}
	}

	private fun printBrands(filePath: String) {
		val jsonContent: String = File(filePath).readText()

		val componentListType = object : TypeToken<List<ComputerComponent>>() {}.type
		val componentsList = Gson().fromJson<List<ComputerComponent>>(jsonContent, componentListType)

		println("File: $filePath List Size: ${componentsList.size}")
	}
}