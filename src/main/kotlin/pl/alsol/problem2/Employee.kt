package pl.alsol.problem2

import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class Employee(
    var name: String,
    var id: UUID = UUID.randomUUID(),
    var age: Int? = null,
    var address: String? = null) {

    var boss: Employee? = null //On Android it would be WeakReference
        set(newBoss) {
            if (newBoss != field) {
                field?._subordinates?.remove(this)
                newBoss?._subordinates?.add(this)
                field = newBoss
            }
        }

    private val _subordinates = mutableListOf<Employee>()
    val subordinates: Sequence<Employee>
        get() = _subordinates.asSequence()

    val level: Int
        get() {
            var level = 0
            var employee = boss
            while(employee != null) {
                level += 1
                employee = employee.boss
            }
            return level
        }

    override fun toString(): String {
        return "name: $name, level: $level, boss: ${boss?.name ?: "<no boss>"}"
    }

    fun addSubordinate(employee: Employee) {
        employee.boss = this
    }

    fun addSubordinates(employees: Collection<Employee>) {
        employees.forEach { addSubordinate(it) }
    }

    fun removeSubordinate(employee: Employee) {
        employee.boss = null
    }

    /**
     * Allows creating company structure in DSL style like this:
     * val company = Company("ACME", "John (CEO)") {
     *     employee("Mary") {
     *         employee("Sam")
     *         employee("Will") {
     *            employee("Jackie")
     *            employee("Frank")
     *         }
     *    }
     *    employee("Peter")
     *    employee("Michael")
     * }
     */
    fun employee(name: String, initialize: (Employee.() -> Unit)? = null) {
        val subordinate = Employee(name)
        addSubordinate(subordinate)
        if (initialize != null) {
            subordinate.initialize()
        }
    }

    fun thisAndAllSubordinates(levelOrder: Boolean = true): Sequence<Employee> {
        return when (levelOrder) {
            true -> sequence {
                val queue = LinkedBlockingQueue<Employee>()
                yield(this@Employee)
                _subordinates.forEach { queue.put(it) }
                do when (val employee = queue.poll()) {
                    null -> break
                    else -> {
                        yield(employee)
                        employee._subordinates.forEach { queue.put(it) }
                    }
                } while (true)
            }
            false -> sequence {
                yield(this@Employee)
                yieldAll(_subordinates.asSequence().flatMap { it.thisAndAllSubordinates() })
            }
        }
    }
}
