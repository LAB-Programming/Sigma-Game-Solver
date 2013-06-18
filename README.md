#σ-game Solver

---
###Description:
The goal of this project is to create a program that can take a directed graph and two configurations to
represent whether the vertices of the graph are on or off for the start and end configurations inputed
through a GUI and then to find the solutions for that pair of configurations in the σ-game or σ<sup>+</sup>-game.
####How the σ-game works:
The σ-game is played on a [directed graph](https://en.wikipedia.org/wiki/Directed_graph). Every vertex in the
graph can have two states either on or off. A move in the σ-game consists of picking a vertex in the graph and
then switching the state of all vertices to which the chosen vertex is connected (note that this does not
necessarily include the chosen vertex). The object of the game is to get from a given start configuration
of on and off vertices to a given end configuration of on and off vertices. The σ<sup>+</sup>-game is the same
as the σ-game except for a move the chosen vertex also switches state.
#####NOTE: This program depends JScience which can be downloaded <a href="http://jscience.org/">here</a>

---
###Goals:
####Completed:
* Readme
* Board class

####Short Term Plans:
* Create and write class SigmaGame which will control the GUI of the program
* GUI

####Long Term Plans:
* Make Board more efficient at finding when there is no solution possible for a pair of configurations