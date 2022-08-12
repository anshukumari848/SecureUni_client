# SecureUni_client

A secure platform for quiz where everything is secure and a teacher can add a quiz and multiple students can give the quiz.

Basic Features:

Create a Teacher/Student Account.

Teacher can add new subject and host multiple quizzes under one subject.

Teacher can add various sections to a quiz and allot different time limit to each section.

Teacher can also add solution to quiz.

Multiple students can login at the same time (From their own devices) and give the quiz.

All the students should have the same set of questions in one section but in random order.

There are individual timers for each section and the students can switch between the different sections throughout the exam.

The student is shown with the total points after the completion of the exam, if the teacher has added the solution by the time student submitted his solution otherwise show the status as pending for evaluation.

The students can ask a query to the teacher to which the teacher may/may not respond.

Advance Features:

The questions can be of different types(Single Choice Correct MCQs, Multiple Choice Correct MCQs, T/F).

The students can give a rating to the quiz and the platform.

All the network operations like transfer of quiz questions and responses must be encrypted.

In case of a connection breakdown all the answers should be stored in a file/local cache (Again in encrypted form) and restored automatically when the connection is re-established.

