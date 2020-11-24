package pl.alsol.problem2

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlin.math.min

class Company(val name: String, ceoName: String, initialize: (Employee.() -> Unit)? = null) {
    val ceo: Employee = Employee(ceoName)

    init {
        if (initialize != null) {
            ceo.initialize()
        }
    }

    fun getAllEmployees(levelOrder: Boolean = true): Sequence<Employee> {
        return ceo.thisAndAllSubordinates(levelOrder)
    }

    fun countCompanySize(): Int {
        return getAllEmployees().count()
    }

    suspend fun visitAllParallel(levelOrder: Boolean = true, visit: (Employee) -> Boolean) = coroutineScope {
        val channel = Channel<Employee>(1000)
        val flow = channel.receiveAsFlow()

        launch(Dispatchers.Default) {
            getAllEmployees(levelOrder).forEach { channel.send(it) }
            channel.close()
        }

        var result: Employee? = null
        val jobs = (1..min(1, Runtime.getRuntime().availableProcessors()/2)).map {
            flow.onEach {
                if (visit(it)) {
                    result = it
                    coroutineContext.cancelChildren()
                }
            }.launchIn(this + Dispatchers.Default)
        }
        jobs.joinAll()

        result
    }

    /**
     * Searches for employees using multiple threads
     * For single thread search equivalent would be:
     * getAllEmployees().first { it.name == "John" }
     */
    suspend fun findFirstEmployee(name: String, levelOrder: Boolean = true): Employee? {
        return visitAllParallel(levelOrder) { it.name == name }
    }

    companion object
}
