package com.ratemybuild.scraper.model

data class ConfigModel(
		val url: String,
		val pageCount: Int,
		val urlFileName: String,
		val componentsFileName: String,
		val dataStoreFileName: String,
		val type: ComponentType
)