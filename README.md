# JadeTreasureHunt
A multi-agent JADE application simulating a treasure hunt.

This application runs two agents in a JADE environment. One is a "game master" that hides a treasure on a grid. The second is a "player" that tries to find said treasure. At each turn, the Player agent moves an avatar by one square on the grid, and the game master tells the player whether it is getting warmer or colder (closer or further away from the treasure).

This is a toy application that models simple asymmetrical 2-agent communication. The structure is based on Aymen Sassi's _Hidden Number_ application in which one agent tries to guess the number chosen by the other agent. It reuses his `AgentLogger` class as well as his `Launcher` class (with minimal changes).
