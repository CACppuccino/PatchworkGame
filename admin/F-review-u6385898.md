# Code Review for Adonis Mouti (u6385898)
### By Ziyang Liu

#### Coding part
`Viewer.java`,`Matrix.java`

#### Best Features
1. Explicit layer in coding. Codes in JavaFx are 
modularised by sections, easy to be fixed or modified while 
not affecting other parts. In other words, fantastic
decomposition.
2. Well design in the user interface and page navigating logic

#### Documentation
1. Clear comments on some of the methods,
while other code blocks still need further 
explain.

#### Class and method structure
1. Class structure is clear and obvious, each method
illustrates a part of the viewer.
 
2. Most of the access of methods and classes can be set as `private` instead
of `public`.

3. Since JavaFx involves many setting steps, a method can be considered
to build for automatically setting these configures, so the code may become 
clean and concise. 

In conclusion, a better encapsulating awareness is expected. Both in the class fields and methods access.

#### Follows Java convention
All variables' names follows the rule of Java convention--camel naming.  
  
