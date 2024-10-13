import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    runBlocking {
        val times = measureTimeMillis {
            val numbers = getRandomList(10, 0,100)
            val chars = getRandomList(10, 'a','z')

            val countNumbersAwait = async{ unpack(numbers) }
            val countCharAwait = async{ unpack(chars) }

            val allCount = countNumbersAwait.await() + countCharAwait.await()
            println("Общее кол-во элементов: $allCount\n")

            val allLists = concatenate(numbers, chars)
            println("Обьединенный список")
            println(allLists.second)
        }
        println("\nОбщее затраченное время: $times")
    }


}

fun getRandomList(length: Int, from:Int, to:Int): List<Int> {
    return List(length) {(from..to).random()}
}
fun getRandomList(length: Int, from:Char, to:Char): List<Char> {
    return List(length) {(from..to).random()}
}

suspend fun <T> unpack(list: List<T>):Int{
    for (i in list){
        delay(1000L)
        println(i)
    }
    return list.count()
}

fun <T> concatenate(listOne:List<T>, listTwo:List<T>):Pair<Int, MutableList<T>>{
    val sumList = mutableListOf<T>()
    sumList.addAll(listOne+listTwo)
    return Pair(sumList.count(), sumList)
}