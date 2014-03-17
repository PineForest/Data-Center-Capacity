Data Center Capacity
====================

Purpose
-------
This was created as a solution to coding test for a job application.

Author
------
David Williams
June 11, 2013

Directories
-----------
Assets - cross version assets
Data-Center-Capacity-V1 - the original version

Files
-----
+ "Assets\Data Center Capacity Exercise.pdf" - the document I was given that describes what to write
+ Assets\input.txt - a sample input file to test the code. Produces the output "300"
+ Data-Center-Capacity-V1\target\artifacts\Data-Center-Capacity-V1.jar - contains the compiled Java byte code
+ Data-Center-Capacity-V1\target\artifacts\Data-Center-Capacity-V1-javadocs.jar - contains the JavaDocs for this project
+ Data-Center-Capacity-V1\target\artifacts\Data-Center-Capacity-V1-sources.jar - contains the source files for this project

Usage
-----

1. Open command shell
2. cd to the module folder (e.g. Data-Center-Capacity-V1)
3. To build enter:

        mvn package

            OR

        mvn jar:jar

4. To build and run enter:

        mvn exec:exec

            OR

        mvn verify

Tool Versions
-------------
Maven: 3.2.1
Java Runtime and SDK: 1.7.0_51