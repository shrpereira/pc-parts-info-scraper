package com.ratemybuild.scraper.config

import com.ratemybuild.scraper.model.ComponentType
import com.ratemybuild.scraper.model.ConfigModel

const val PAGE_NUM_MASK = "#PAGE_NUMBER"
const val URLS_FOLDER = "scraped/pcpartspicker/urls/"
const val COMPONENTS_FOLDER = "scraped/pcpartspicker/components/"
const val DATASTORE_FOLDER = "scraped/datastore/"

const val SHOULD_SCRAP_COMPONENTS = false
const val SHOULD_SCRAP_URLS = false

object ScraperConfiguration {

	val configurationList = mutableListOf<ConfigModel>().apply {
		add(ConfigModel("https://pcpartpicker.com/products/cpu/#page=$PAGE_NUM_MASK", 12, "${URLS_FOLDER}cpuUrls.json", "${COMPONENTS_FOLDER}cpuComponents.json", "${DATASTORE_FOLDER}cpus.json", ComponentType.CPU))
		add(ConfigModel("https://pcpartpicker.com/products/cpu-cooler/#page=$PAGE_NUM_MASK", 10, "${URLS_FOLDER}cpuCoolersUrls.json", "${COMPONENTS_FOLDER}cpuCoolersComponents.json", "${DATASTORE_FOLDER}cpuCoolers.json", ComponentType.CPU_COOLER))
		add(ConfigModel("https://pcpartpicker.com/products/internal-hard-drive/#page=$PAGE_NUM_MASK", 33, "${URLS_FOLDER}storageUrls.json", "${COMPONENTS_FOLDER}storageComponents.json", "${DATASTORE_FOLDER}storages.json", ComponentType.STORAGE))
		add(ConfigModel("https://pcpartpicker.com/products/video-card/#page=$PAGE_NUM_MASK", 38, "${URLS_FOLDER}videoCardsUrls.json", "${COMPONENTS_FOLDER}videoCardsComponents.json", "${DATASTORE_FOLDER}videoCards.json", ComponentType.VIDEO_CARD))
		add(ConfigModel("https://pcpartpicker.com/products/motherboard/#page=$PAGE_NUM_MASK", 30, "${URLS_FOLDER}motherboardsUrls.json", "${COMPONENTS_FOLDER}motherboardsComponents.json", "${DATASTORE_FOLDER}motherboards.json", ComponentType.MOTHERBOARD))
		add(ConfigModel("https://pcpartpicker.com/products/power-supply/#page=$PAGE_NUM_MASK", 19, "${URLS_FOLDER}powerSuppliesUrls.json", "${COMPONENTS_FOLDER}powerSuppliesComponents.json", "${DATASTORE_FOLDER}powerSupplies.json", ComponentType.POWER_SUPPLY))
		add(ConfigModel("https://pcpartpicker.com/products/memory/#page=$PAGE_NUM_MASK", 66, "${URLS_FOLDER}memoriesUrls.json", "${COMPONENTS_FOLDER}memoriesComponents.json", "${DATASTORE_FOLDER}memories.json", ComponentType.MEMORY))
		add(ConfigModel("https://pcpartpicker.com/products/case/#page=$PAGE_NUM_MASK", 36, "${URLS_FOLDER}casesUrls.json", "${COMPONENTS_FOLDER}casesComponents.json", "${DATASTORE_FOLDER}cases.json", ComponentType.CASE))
	}

//	sealed class ComponentType {
//		data class Cpu(val model: ConfigModel) : ComponentType()
//		data class CpuCooler(val model: ConfigModel) : ComponentType()
//		data class HardDrive(val model: ConfigModel) : ComponentType()
//		data class VideoCard(val model: ConfigModel) : ComponentType()
//		data class Motherboard(val model: ConfigModel) : ComponentType()
//		data class PowerSupply(val model: ConfigModel) : ComponentType()
//		data class Memory(val model: ConfigModel) : ComponentType()
//		data class Case(val model: ConfigModel) : ComponentType()
//	}
//
//	fun getCpuConfig(): ComponentType.Cpu {
//		return ComponentType.Cpu(configurationList[0])
//	}
//
//	fun getCpuCoolerConfig(): ComponentType.CpuCooler {
//		return ComponentType.CpuCooler(configurationList[1])
//	}
//
//	fun getHardDriveConfig(): ComponentType.HardDrive {
//		return ComponentType.HardDrive(configurationList[2])
//	}
//
//	fun getVideoCardConfig(): ComponentType.VideoCard {
//		return ComponentType.VideoCard(configurationList[3])
//	}
//
//	fun getMotherboardConfig(): ComponentType.Motherboard {
//		return ComponentType.Motherboard(configurationList[4])
//	}
//
//	fun getPowerSupplyConfig(): ComponentType.PowerSupply {
//		return ComponentType.PowerSupply(configurationList[5])
//	}
//
//	fun getMemoryConfig(): ComponentType.Memory {
//		return ComponentType.Memory(configurationList[6])
//	}
//
//	fun getCaseConfig(): ComponentType.Case {
//		return ComponentType.Case(configurationList[7])
//	}
}

