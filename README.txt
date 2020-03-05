Hibernate aggregate queries.
-------------------------------------------------------------------------

The objective of this coding challenge is to demonstrate understanding of 
writing queries to aggregate data. In this problem you have to create apis 
for getting the number of photos per owner and owner with maximum photos.

Requirements
-------------------
2. Write code to
		- get photo counts per owner
		- get owner with maximum photos.


Deliverables
----------------
1. Following urls should work..
	- 	url : localhost:8082/photoByOwner
		type: GET
		return JSON object string of owners and their respective photo count
	- 	url : localhost:8082/mostActive
		type: GET
		return JSON object string of owner who owns maximum photos and the corresponding count.           


2. Make sure your implementation passes the given test code.

How to use the programming environment
--------------------------------------------------------
 a) The development environment is configured with Java 8 runtime 
    and Maven is setup with Hibernate, Mysql.
 b) The workspace provided with the Web IDE has all project files already opened in it
 c) You can write the application classes in the source folder (src/main/java).
 d) All test classes are already implemented and provided in in the test folder (src/test/java)
 e) Project dir: /home/{username}/projects/applications
 f) To compile and the test cases, use Maven in a terminal (already open): 
    mvn clean integration-test -P integration

    Note: Please save your code before you go to next question else you will loose your work.
