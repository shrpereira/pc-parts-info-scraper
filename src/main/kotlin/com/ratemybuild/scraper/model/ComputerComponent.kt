package com.ratemybuild.scraper.model

data class ComputerComponent(
		val title: String,
		val url: String,
		val properties: List<ComponentProperty>
)