package pl.alsol.problem2

import kotlinx.coroutines.*

fun main() = runBlocking {
    /*
    val company = Company("ACME", "John (CEO)") {
        employee("Mary") {
            employee("Sam")
            employee("Will") {
                employee("Jackie")
                employee("Frank")
            }
        }
        employee("Peter")
        employee("Michael")
    }
    */

    val levels = listOf(2, 3, 10, 50, 100, 2000, 13000, 400000)
    val company = Company.generate(levels)

    //Add name to the bottom of company tree
    val nameToSearch = "Serge Bottom"
    company.getAllEmployees().firstOrNull { it.level == levels.lastIndex + 1}?.addSubordinate(Employee(nameToSearch))
    val numberOfLevels = company.getAllEmployees().map { it.level }.maxOrNull() ?: 0

    println("Company with ${company.countCompanySize()} employees and ${numberOfLevels + 1} levels deep structure")

    println("Looking for Serge Bottom:")
    println("Single threaded search...")
    var employee = company.getAllEmployees().firstOrNull { it.name == nameToSearch }
    println("result: $employee")
    println()
    println("Multi threaded search...")
    employee = company.findFirstEmployee(nameToSearch, true)
    println("result: $employee")

    println("-------------------------")
    val maxLevelToSearch = 5
    println("Looking for all employees which names starts with 'Andrew' and their max level is $maxLevelToSearch...")
    val andrews = company.getAllEmployees().filter { it.name.startsWith("Andrew") && it.level <= maxLevelToSearch }.toList()
    println("Found ${andrews.size} Andrews. Listing:")
    andrews.forEach { println(it) }
}
