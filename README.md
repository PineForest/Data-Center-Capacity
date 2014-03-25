Data Center Capacity
====================

Purpose
-------
This was originally created as a solution to a job interview coding problem. I have since created a newer solution using a third party library in place of custom code.

Author
------
David Williams
June 11, 2013

Building and Running
--------------------
This is a Java project which builds with Maven. See "Usage" and "Tool Versions" at the end.

data-center-capacity-v1
-----------------------
The original solution which uses a partial, custom implementation of a Multiset to solve the problem. Multisets were chosen because they provide for mathematical operations on sets of elements which may contain duplicates of the included elements.

Worst case (calculateTotalUsableMachineGroups operation): O(N^2)

data-center-capacity-v2
-----------------------
A newer solution which uses Google's implementation of a Multiset to solve the problem. Multisets were chosen because they provide for mathematical operations on sets of elements which may contain duplicates of the included elements.

Worst case performance (calculateTotalUsableMachineGroups operation): O(N^3) - Custom operations are O(N^2) with a call to Guava library's retainAll from the inner loop adding an additional O(N) loop

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