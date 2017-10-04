# Code Review for Ziyang Liu (u6210090)
### By Audonis Mouti

#### Best Features
1. Good exception and error handling
2. Well performance on runtime

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
All variables' names follows the rule of Java convention--camel naming.  
All class and methods take good limitations on the keyword of access(`public`,`private`,etc.).  
