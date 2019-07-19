package com.ratemybuild.scraper.pcpartspicker.model

data class ComponentScrapModel(
		val url: String,
		val properties: List<PropertyScrapModel>
)