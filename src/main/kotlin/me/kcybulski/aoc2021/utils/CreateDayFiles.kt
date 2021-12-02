package me.kcybulski.aoc2021.utils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.nio.file.Paths
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit.DAYS

class CreateDayFiles(
    private val day: Long
) {

    private val document: Document = Jsoup.connect("https://adventofcode.com/2021/day/${day}").get()
    private val data: Document = Jsoup.connect("https://adventofcode.com/2021/day/${day}/input")
        .cookie("session", System.getenv("SESSION"))
        .get()

    fun getName(): String {
        val rawTitle = document.getElementsByTag("h2").first().text()
        val title = titleRegex.matchEntire(rawTitle)?.groupValues?.get(1) ?: ""
        return "${day.toString().padStart(2, '0')}${title.replace("[^a-zA-Z]+".toRegex(),"")}"
    }

    fun getData(): String {
        return data.body().wholeText()
    }

    companion object {

        val titleRegex = "^.*: (.*) ---$".toRegex()
    }
}

fun main() {
    val firstDay = LocalDate.parse("2021-12-01")
    val time = LocalDateTime.now()
    val day = DAYS.between(firstDay, time) + 1

    val createDayFiles = CreateDayFiles(day)
    val name = createDayFiles.getName()

    createFile("resources/${name}.txt", createDayFiles.getData())
    createFile("kotlin/me/kcybulski/aoc2021/${name}.kt", kotlinFile(name))
}

fun createFile(path: String, data: String) {
    val file = Paths.get("./src/main", path).toFile()
    file.writeText(data)
}

fun kotlinFile(name: String) = """
    package me.kcybulski.aoc2021
    import me.kcybulski.aoc2021.utils.lines

    class ${name.substring(2)}() {
    }
    
    fun main() {
        val lines = lines("$name")
        println(lines.size)
    }
""".trimIndent()
