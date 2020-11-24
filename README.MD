# Problem 1

## Description
Define a class structure to save the following organization charts including basic constructor(s)
and destructor if needed. Each person (and subordinates) has the same set of attributes. (Age,
EmployeeID, Address, current reports and current boss).

(a) Write a function to add employee records.
(b) Write a function to return an employee record by given name for reading and writing.
You can modify your classes or add any new data structure to improve the performance.

## Build instructions
Requirements: *Minimum JDK 1.6*

Inside project's main directory:\
To compile: *./gradlew compileKotlin*\
To run tests: *./gradlew test*\
To run application: *./gradlew run*

##Console log
>Company with 415167 employees and 10 levels deep structure\
>Looking for Serge Bottom:\
>Single threaded search...\
>result: name: Serge Bottom, level: 9, boss: Lisa-Marie Daugherty
>
>Multi threaded search...
>result: name: Serge Bottom, level: 9, boss: Lisa-Marie Daugherty
>-------------------------
>Looking for all employees which names starts with 'Andrew' and their max level is 5...\
>Found 3 Andrews. Listing:\
>name: Andrew Parra, level: 5, boss: Emmy Zhang\
>name: Andrew Bond, level: 5, boss: Aurora Mclaughlin\
>name: Andrew Ellwood, level: 5, boss: Aurora Mclaughlin\
>
>Process finished with exit code 0
