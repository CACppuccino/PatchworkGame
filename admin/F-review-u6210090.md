# Code Review for Ziyang Liu (u6210090)
### By Adonis Mouti

#### Coding part
`PatchworkGame.java`, `PatchworkAI.java`,`State.java`, `Matrix.java`

#### Best Features
1. Good exception and error handling
2. Well performance on runtime
3. Use of separate methods to avoid repetition of code, increasing readability and reducing the chance of errors occuring if something needed to be changed.

#### Documentation
1. Well documented on the `isPlacementValid` and `PatchworkAI`, the functionality and role of each line
were stated clearly in the comments. But the helper methods
may need more comments to explain about it's usage.

#### Class and method structure
1. Class structure is clear and easy to be understood, 
particularly, class `State` is well organized and methods of class and instance are
separated smartly. For each player, all its states in the game are recorded
in the `State` class field and the operation it may need 
is written in `State` class as well.

2. The only drawback of the code is the static class field
in the `PatchworkGame`, which enumerates the data of needed tiles 
in the game. Though there have not come out a good solution
as substitution, it is believed that the section of the code
can be written in a smarter way.

#### Follows Java convention
The majority of the variables are named appropriately
 in a way that their use is clear. The naming of these variables follow the Java convention, except for his constants where instead of using CAPS he uses the standard variable naming convention.
 
The code also contains a lot of tests for possible errors such as null/empty strings making it very robust. The only way an error could happen would be if one of the associated methods is changed or has an error in it.
