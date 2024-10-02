package github.com.Vy4cheSlave.frequency_words_analysis

import java.util.Scanner

val PUNCTUATION_CHARS: List<Char> = listOf(',', '.', ';', ':', '…', '?', '!', '\"')

fun main() {
	var rawData: String = ""
	val scanner = Scanner(System.`in`)
	while(scanner.hasNext()) {
		rawData += scanner.nextLine() + '\n'
	}
	if (rawData.length == 0) {
		println("во входящий поток была передана пустая строка")
			System.exit(1)
	}
	val cleanedData = rawData.filterNot{it in PUNCTUATION_CHARS}.lowercase()
	// 1
	val countRussianChars = getCountRussianChars(cleanedData)
	println("[частота русских символов]\n" + countRussianChars.toString())
	// 2
	val countWords = getCountWords(cleanedData)
	println("[частота слов длинны больше 3]\n" + countWords.toString())
	// 3
	val splitedRawData = rawData.split("\n")
	println("[последние 10 строк текста на входящем потоке]")
	if (splitedRawData.count() > 10) {
		val lengthRawData = splitedRawData.count()
		for (string in splitedRawData.slice(lengthRawData-11..lengthRawData-1)) {
			println(string)
		}
	} else {
		for (string in splitedRawData) {
			println(string)
		}
	}
}

fun getCountRussianChars(inputData: String): Map<Char, Int> {
	val russianCharacters = inputData.filter{it in 'а'..'я'}
	val uniqueRussianChars = russianCharacters.toSet()
	var countChars: MutableMap<Char, Int> = mutableMapOf()
	for (character in uniqueRussianChars) {
		val countChar = russianCharacters.count{
			it == character
		}
		countChars.put(character, countChar)
	}
	val sortedCounts: MutableMap<Char, Int> = mutableMapOf()
	countChars.entries.sortedBy{it.value}.reversed().forEach{sortedCounts[it.key] = it.value}
	return sortedCounts
}

fun getCountWords(inputData: String): Map<String, Int>? {
	val splitedWords = inputData.split(" ", "\n", "\t").filter{it.length > 3}
	val uniqueWords = splitedWords.toSet()
	var countWords: MutableMap<String, Int> = mutableMapOf()
	for (word in uniqueWords) {
		val countWord = splitedWords.count{
			it == word
		}
		countWords[word] = countWord
	}
	val sortedCounts: MutableMap<String, Int> = mutableMapOf()
	countWords.entries.sortedBy{it.value}.reversed().slice(0..10).forEach{sortedCounts[it.key] = it.value}
	val sortedBySecondChar: MutableMap<String, Int> = mutableMapOf()
	sortedCounts.entries.sortedWith(compareBy{it.key.slice(1..it.key.length-1)}).forEach{sortedBySecondChar[it.key] = it.value}
	return sortedBySecondChar
}