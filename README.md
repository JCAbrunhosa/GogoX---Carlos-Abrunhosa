# GogoX
Coding Challenge

This repository is dedicated for the GogoX Coding Challenge


Overview:

This REST API Server works as a backend for a simple Lottery game. Each 20 seconds, a new draw starts, and each user must apply using their First Name, Last Name, and Winning Key. Each user has a UUID attached to them that is verified. No user can apply more than once, and many users can share the prize pool. If no one wins the draw, the prize pool increases until someone wins (which will then be reseted).

This RESTful API Server was done using Spring Boot. This is just a simple Server, with some basic Commands (allows for Get and Post).

There are still things that could be improved: concurrency could be improved and the overall project could be streamlined if given time. Security and authentication could also be improved, but the main goal(playing the game) is covered.
