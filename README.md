Data Center Capacity
====================

Purpose
-------
This was originally created as a solution to a job interview coding test.

Author
------
David Williams
June 11, 2013

Note
----
This is a Java project which builds with Maven. See "Usage" and "Tool Versions" at the end.

Directories
-----------
assets - cross version assets, including a document with a description of the problem being solved
data-center-capacity-v1 - the original version which uses a custom class specific to the problem and which borrows on multiset concepts
data-center-capacity-v2 - updated version which uses Google's Multiset implementation
parent - contains the parent Maven POM file for all versions

Usage
-----
1. Open the command shell
2. cd to the module folder (e.g. data-center-capacity-v1)
3. To build, execute:

        mvn package

            OR

        mvn jar:jar

4. To build and run, execute:

        mvn verify

            OR

        mvn exec:exec

Tool Versions
-------------
Maven: 3.2.1
Java Runtime and SDK: 1.7.0_51