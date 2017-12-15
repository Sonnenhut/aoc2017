import java.io.File

object Challenge {
    fun read(day: Int) : String {
        return File(javaClass.classLoader.getResource("day$day.txt").toURI())
                .readText()
    }
}