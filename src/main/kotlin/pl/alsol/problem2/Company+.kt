package pl.alsol.problem2

import kotlin.random.Random

/**
 * Not fully optimized - for tests only
 */
internal fun Company.Companion.generate(levels: List<Int>): Company {
    val names = generateNames()
    val company = Company("ACME", "John Boss (CEO)")
    var currentLevelNodes = listOf(company.ceo)
    for (level in levels) {
        if (level <= 0) break

        var nodesLeft = level
        for (i in 0..currentLevelNodes.lastIndex) {
            val currentNode = currentLevelNodes[i]

            var nodesToAdd = if (i == currentLevelNodes.lastIndex) nodesLeft else Random.nextInt(0, nodesLeft)
            nodesLeft -= nodesToAdd
            while (nodesToAdd > 0) {
                val name = "${names.random().first} ${names.random().second}"
                currentNode.addSubordinate(Employee(name))
                nodesToAdd -= 1
            }

            if (nodesLeft == 0) break
        }

        currentLevelNodes = currentLevelNodes.flatMap { it.subordinates }.shuffled()
    }
    return company
}

private fun generateNames(): List<Pair<String, String>> {
    return listOf(
        "Gillian Bender",
        "Jayden-James Zhang",
        "Kiaan Dillard",
        "Cobie Corrigan",
        "Jessica Bray",
        "Ellouise Armitage",
        "Cordelia Cullen",
        "Precious Boyce",
        "Niyah Decker",
        "Macey Luna",
        "Tasnim Mckenna",
        "Lorraine Carver",
        "Alisa Saunders",
        "Tamara Talley",
        "Amrita Tran",
        "Yazmin Higgs",
        "Skyla John",
        "Lisa-Marie Riggs",
        "Faheem Parra",
        "Sahra Medrano",
        "Leslie Lu",
        "Meg Wolfe",
        "Miley Regan",
        "Abu Powers",
        "Giuseppe Wyatt",
        "Menaal Kaur",
        "Eshaan Peacock",
        "Pearce Rudd",
        "Seb Gould",
        "Halle Lim",
        "Keri Schwartz",
        "Menachem Logan",
        "Fatima Buck",
        "Sanna Davie",
        "Lila Keller",
        "Fynley Heaton",
        "Alfie-Lee Garrison",
        "Thalia Goodwin",
        "Ameena Oconnor",
        "Shelbie Keenan",
        "Usama Mohamed",
        "Ray Bond",
        "Judith Pritchard",
        "Kaydon Carty",
        "Corbin Tanner",
        "Karam Bowden",
        "Zaynah Daugherty",
        "Abraham Knights",
        "Seren Mair",
        "Henri Eastwood",
        "Ellie-Mai Wynn",
        "Corben Mclaughlin",
        "Myla Herman",
        "Emmy Trejo",
        "Darren Rawlings",
        "Bethan Braun",
        "Ayyan Hopper",
        "Casey Parks",
        "Raphael Dickerson",
        "Elliot Duncan",
        "Amalie Mccallum",
        "Ameera Clifford",
        "Laila Flynn",
        "Rhian Barnard",
        "Ronny Ware",
        "Aurora Pennington",
        "Giovanni Silva",
        "Tarik Booth",
        "Ashraf Mcfarland",
        "Pawel Wilder",
        "Kirandeep Hutchinson",
        "Milli Morrison",
        "Lukasz Avalos",
        "Shamima Walsh",
        "Kiana Molina",
        "Soraya Jaramillo",
        "Sofia Alvarado",
        "Macauly Salazar",
        "Primrose Mckay",
        "Andrew Faulkner",
        "Preston Odom",
        "Gabriel Page",
        "Millie-Mae Derrick",
        "Pamela Penn",
        "Tina Lewis",
        "Georgie Spence",
        "Bayley Mendez",
        "Aden Whittaker",
        "Velma Carney",
        "Giorgio Osborne",
        "Shamas Ellwood",
        "Zunairah Strong",
        "Heena Bull",
        "Humaira Rowley",
        "Star Morin",
        "Keiran Lawrence",
        "Dawn David",
        "Neelam Lowery",
        "Siana Nunez",
        "Andrea Cline"
    ).map {
        val nameSplited = it.split(" ")
        Pair(nameSplited.first(), nameSplited.last())
    }
}
