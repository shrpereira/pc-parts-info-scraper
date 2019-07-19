package com.ratemybuild.scraper.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.io.FileNotFoundException
import java.io.PrintWriter

inline fun <reified T> Gson.fromJson(json: String): T? = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

inline fun <reified T> Gson.createJson(objects: T): String? = this.toJson(objects, object : TypeToken<T>() {}.type)

inline fun <reified T> MutableList<T>.convertToJson(): String? {
	val gson = GsonBuilder()
			.setPrettyPrinting()
			.create()

	return gson.createJson(this)
}

inline fun <reified T> String.parseFile(): T? = try {
	val jsonContent: String = File(this).readText()

	Gson().fromJson<T>(jsonContent)
} catch (e: FileNotFoundException) {
	null
}

fun ChromeDriver.waitUntilPageIsReady() {
	(this as JavascriptExecutor).let { executor ->
		WebDriverWait(this, 1)
				.until { executor.executeScript("return document.readyState") == "complete" }
	}
}

fun ChromeDriver.getAndWait(url: String) {
	this.get(url)
	this.waitUntilPageIsReady()
}

fun String.saveToFile(path: String) {
	val file = File(path)
	if (!file.exists()) file.createNewFile()

	PrintWriter(path).use { it.println(this) }
}

fun WebElement?.safeClick(): Boolean = try {
	this?.click()
	true
} catch (e: Exception) {
	false
}

inline fun <T> Iterable<T>.safeFirst(predicate: (T) -> Boolean): T? {
	try {
		for (element in this) if (predicate(element)) return element
	} catch (e: Exception) {
	}
	return null
}

inline fun <T1, T2, R> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R): R? {
	return p1?.let { safeP1 ->
		p2?.let { safeP2 ->
			block(safeP1, safeP2)
		}
	}
}