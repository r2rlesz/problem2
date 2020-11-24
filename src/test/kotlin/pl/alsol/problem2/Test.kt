package pl.alsol.problem2

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Test {

    @Test
    fun testAddingAndMovingEmployees() {
        val root = Employee("root node")
        val child = Employee("child node")
        val newRoot = Employee("new root")

        root.addSubordinate(child)
        assertEquals(root, child.boss)
        assertTrue(root.subordinates.contains(child))

        newRoot.addSubordinate(child)
        assertEquals(false, root.subordinates.contains(child))
        assertEquals(newRoot, child.boss)
        assertTrue(newRoot.subordinates.contains(child))
    }

    @Test
    fun testChangingEmployeesBoss() {
        val root = Employee("root node")
        val child = Employee("child node")
        val newRoot = Employee("new root")

        child.boss = root
        assertEquals(root, child.boss)
        assertTrue(root.subordinates.contains(child))

        child.boss = newRoot
        assertFalse(root.subordinates.contains(child))
        assertEquals(newRoot, child.boss)
        assertEquals(true, newRoot.subordinates.contains(child))
    }

    @Test
    fun testDSLStyleEmployeeCreation() {
        val company = Company("ACME", "0") {
            employee("1")
            employee("1") {
                employee("2") {
                    employee("3")
                }
                employee("2")
            }
            employee("1")
            employee("1")
        }

        for (employee in company.getAllEmployees()) {
            assertEquals(employee.level, employee.name.toInt(), "Employee level (${employee.level}) not matched name (${employee.name})")
        }
    }

    @Test
    fun testSearch() = runBlocking {
        val levels = listOf(2, 4, 8, 16, 32, 64)
        val company = Company.generate(levels)

        //Add name to the bottom of company tree
        val nameToSearch = "Serge Bottom"
        company.getAllEmployees().firstOrNull { it.level == levels.lastIndex + 1}?.addSubordinate(Employee(nameToSearch))

        assertNotNull(company.findFirstEmployee(nameToSearch))
    }

    @Test
    fun testLevelOrderListing() {
        val levels = listOf(2, 4, 8, 16, 32, 64)
        val company = Company.generate(levels)

        var prevLevel = 0
        for (employee in company.getAllEmployees(true)) {
            assertTrue(prevLevel <= employee.level)
            prevLevel = employee.level
        }
    }
}