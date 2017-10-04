# Code Review for Weitao Chen (u6210090)
### By Adonis Mouti

#### Coding part
`PatchworkGame.java: isSeven()`, `State.java: printPlayerBoard()`

#### Best Features
1. The problem is separated into small pieces and 
then solved by independent methods, 
which makes the program more efficient and more readable.

#### Documentation
1. Need more explain in helper functions `printPlayerBoard`
2. Several comments are expected to be clearer in the `isSeven` method

#### Class and method structure
1. Instead of writing all the codes in `isSeven()`, a helper function
`printPlayerBoard` is constructed to check the player's state in the 
squilt board. This decomposes the method and problem, make it easier to
debug. 

#### Follows Java convention
1. The name of methods and variables follows Java convention 
From the code, it is obviously follow the code conventions. For example, the if and for block.
	
	
	