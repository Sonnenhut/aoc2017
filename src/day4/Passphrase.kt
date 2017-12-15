package day4

/**
 * passphrase that checks for unique values
 */
abstract class PassphraseBase(val input: String){
    companion object {
        fun countValidPassphrase(passes: List<PassphraseBase>): Int {
            return passes.filter { it.valid() }.count()
        }
    }

    abstract fun valid(): Boolean
}

/**
 * passphrase that checks for unique values
 */
class PassphrasePartOne(input: String) : PassphraseBase(input) {

    override fun valid(): Boolean {
        return input.split(" ").groupingBy { it }.eachCount().none { it.value > 1 }
    }
}

/**
 * passphrase that checks for values that are not anagrams
 */
class PassphrasePartTwo(input: String) : PassphraseBase(input) {

    override fun valid(): Boolean {
        val parts = input.split(" ")
        // contains none that are an anagram of another
        return parts.none { part ->
            val otherParts = parts.minus(part)
            otherParts.any { it.isAnagramOf(part) }
        }
    }
}

fun String.isAnagramOf(other: String) : Boolean {
    return if(this.length == other.length) {
        // really check how many of a char a string has
        val thisMap = this.groupingBy { it }.eachCount()
        val otherMap = other.groupingBy { it }.eachCount()
        thisMap == otherMap
    } else {
        // the strings don't even match by size -> cannot be an anagram
        false
    }
}