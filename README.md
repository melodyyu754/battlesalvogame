# BattleSalvo - CS3500
BattleSalvo AI-AI/Manual-AI game created for Object Oriented Design class

Collaborators: Melody Yu and Olivia Sedarski

## Introduction
BattleSalvo is a strategic naval warfare game, a variant of the classic Battleship, implemented in Java. In this game, players strategically position ships on customizable grids ranging from 6x6 to 15x15. Each turn, players launch one missile per remaining ship in their fleet simultaneously, aiming to sink their opponent's ships. This Java version allows both single-player gameplay against an AI opponent in the terminal, offering an engaging and challenging experience, and multi-player gameplay where one player's AI algorithm can battle against other players on a shared server.

## Human vs. AI Mode
### How to play:
After running the program, the human player will be prompted to enter the dimensions of the board they would like to play with. A "valid" height and width are dimensions between 6 and 15, inclusive.

<img width="619" alt="Screenshot 2023-11-01 at 9 49 26 PM" src="https://github.com/melodyyu754/battlesalvogame/assets/118621363/910f3163-3c33-4019-80bd-232e6360e0c4">

Next, the player will be prompted to enter size of the "fleet" they want to play with. There must be at least one ship of each type, and the maximum number of total ships is equal to the smaller coordinate of the board. This completes the setup of the game.

<img width="1011" alt="Screenshot 2023-11-01 at 9 54 06 PM" src="https://github.com/melodyyu754/battlesalvogame/assets/118621363/deef2879-2a80-460b-95f2-d8eb695e5057">

Two boards are then displayed to the player. The first, "Your Board", represents the board belonging to the human user. The ships, placed by a random algorithm, are displayed. The second, "Opponent Board", represents the board belonging to the AI player. The human user does not know where the AI player's ships are, so nothing is displayed.

<img width="149" alt="Screenshot 2023-11-01 at 9 54 19 PM" src="https://github.com/melodyyu754/battlesalvogame/assets/118621363/e17bdb3b-ff48-4ccb-aaad-cca4be612163">

The user is also prompted to enter a number of shots equal to the number of ships they have remaining (not fully sunk) on their board. These shots will be taken on the opponent's board. Shots are entered in [x, y] format.

When all shots have been entered, the program prints updated boards. "S" stands for ships on "Your Board" that have not yet been discovered, "M" stands for spots on a board that have been hit but do not contain a ship, and "H" stands for spots on a board that have a ship and have also been hit.

<img width="227" alt="Screenshot 2023-11-01 at 9 56 24 PM" src="https://github.com/melodyyu754/battlesalvogame/assets/118621363/4f0c3bfc-7d20-473d-8580-12e31bc070f6">

This continues until either the AI player or the human player has sunk all of the other's points. In this case, the human player won.

<img width="338" alt="Screenshot 2023-11-01 at 10 03 53 PM" src="https://github.com/melodyyu754/battlesalvogame/assets/118621363/ac2cfa6b-35bc-4e6c-9e91-17b0cdfb3a8b">

### Code Walkthrough/Explanation
When the main method is run, runManualVersusAi is called because there are zero arguments passed in. A ManualAiController is initialized and two methods, initGame() and runGame() are called on it.

#### initGame():
initGame() performs the initial actions to set up the game. This includes prompting the user to enter the specifications (board size, fleet size) that they want for the game, and checking to see if the user's inputs are valid. Then, it initializes both the manual player and its boards and the opponent's player and boards. 4 boards are in the game, which in reality is two (some just have limited information).

Initializing the players/boards:

<img width="778" alt="Screenshot 2023-11-01 at 10 59 51 PM" src="https://github.com/melodyyu754/battlesalvogame/assets/118621363/2f1573b1-f43b-4121-90a4-37296c355186">

#### runGame():
runGame() handles most of the game logic and delegation. While the game is not over, it prompts the user to enter "shots" to target the opponent.

<img width="773" alt="Screenshot 2023-11-01 at 10 59 34 PM" src="https://github.com/melodyyu754/battlesalvogame/assets/118621363/dc06c41f-cd5f-41a2-bb20-230685c3205d">



## AI vs. Server Mode
### How to Play:
The server for this project was written by CS3500 TAs. It contains a basic algorithm for generating "shots", and is able to play against students.

After starting the server and running the program, the result returned will be one of 3 things:

Game over: WIN
You won!

Game over: LOSS
You lost!

Game over: DRAW
You tied!

### Walkthrough/Explanation
When the main method is run, runAiServer is called because there are two arguments passed in. A server is established, boards are created, and a proxyController is initialized and run.

When the ProxyController is initialized, the game is initialized with the setup information provided by the server.

<img width="736" alt="Screenshot 2023-11-01 at 10 40 29 PM" src="https://github.com/melodyyu754/battlesalvogame/assets/118621363/bd344df7-a4a4-4d2b-a1fc-a9fa2b80ad45">

Then, the ProxyController is run. This essentially plays the entire game, handling messages coming in from the server until the server closes.

<img width="592" alt="Screenshot 2023-11-01 at 10 40 49 PM" src="https://github.com/melodyyu754/battlesalvogame/assets/118621363/86b7d133-ac24-4cb3-9740-cef66a919878">

<img width="494" alt="Screenshot 2023-11-01 at 10 41 42 PM" src="https://github.com/melodyyu754/battlesalvogame/assets/118621363/bce72f7d-604f-4e25-b60f-88ee7f993c8d">

Each "handle" helper method handles the specific message coming in. For example, the handleTakeShots() method responds to the server's request for the AiPlayer's generated shots.
<img width="641" alt="Screenshot 2023-11-01 at 10 43 17 PM" src="https://github.com/melodyyu754/battlesalvogame/assets/118621363/702510c3-d633-4e49-be68-af73a8045cea">

Once handleEndGame is run, the server is closed, the user is provided a message, and the game ends.

The server log will contain more detailed information about the game. To see the JSON communication between the server and the local machine, look at the server log.

### Class Demo
The CS3500 TAs created a visual representation of each game being played to facilitate an in-class tournament. Below is a video of our team (noahsark) defeating an opponent (benteam).

https://github.com/melodyyu754/battlesalvogame/assets/118621363/b7b2b567-b826-4cb1-b95f-7618d8d7b742

## Reflection
### My Overall Experience Working on this Project
I thorughly enjoyed working on this project. The first phase of the project was done individually (without server capabilities), and it is one of the projects I am proudest of completing. The second phase of the project was done with a partner. Olivia and I pair programmed often, diagraming ideas on a whiteboard. It was extremely rewarding to see our work come to fruition during the tournament we played during class and received extra credit for our algorithm.

### Challenges I Faced
This was my first time working with JSON objects and servers, and the new syntax and bugs we faced were much more difficult to deal with than simple logic errors with previous projects. It was sometimes difficult to parse through logs of errors to find out what was really going wrong. Additionally, it was a larger scale project and it was sometimes hard to keep track of the logic between the MVC.

### Changes I Would Make
There are two main things I would work on if I returned to this project or did it over.

1) I would improve my AI algorithm. Though ours did fairly well in the tournament, I would focus on creating and testing a probability map algorithm that would create probability values for each coordinate on the board as a way to optimize shots taken.

2) I would further break up my code into components and helper methods. There are methods that are more complex than I would prefer, and I would focus on decoupling components.

### What I Learned
I learned many fundamental skills from this project. On the technical side, I learned how to create JSON objects and communicate with a server. I also learned more about the MVC pattern and how to keep my program in alignment with SOLID design principles. It was especially helpful to create a UML diagram to conceptualize the parts of the program first.

Beyond the technical aspects, I learned how to collaborate with others. This included using software such as github and dealing with merge conflicts, as well as how helpful it was to be able to talk through code and concepts with someone else. It was a fast paced project completed in about 2 weeks, and it was immensely rewarding to see the final result.
