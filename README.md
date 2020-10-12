# DiceGame

## To Run the code.
### Prerequites:
Java 1.8
IntelliJ IDEA ide

### Run
Download the zip file of source code
Extract the zip file.

Open the Intellij ide.
Open the Project:
  file -> open -> path to sourceCode package DiceGame -> open
  
open Dice.java class: 
  src -> main -> java -> com.game.dice.controller -> Dice.java
  
To pass the Command line arguments:
  Run -> Edit Configuration -> enter values in Program argument fields.
  
## Assumption
The Value of N (number of players) and M (points of accumulate) are passed in same order with space separation(5 50) as command line arguments.

if player roll 6 get another chance, and if again get a 6 will get more chance (as it is not mention in the requirement, what to do with the second or furhter occurance of 6 onward), will modify the code after getting clarification on requirement.

The player won't get another chance for rolled 6, if the addition of rolled face (i.e 6) and total sum till now for that player is more than M (points of accumulate).


