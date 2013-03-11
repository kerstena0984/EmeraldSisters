# Emerald Sisters
==============

A two-dimensional tile-based roleplaying game written in the Java programming language.

Features
==============
* Unique narrative-based character progression system
* Expansive and engaging world

Authors
==============
* __MattDavBen__ - Game creator

Slick2D Setup Instructions
==============
* Right-click your project's name and select "properties"
* Select 'Java Build Path' and then the 'Libraries' tab.
* Click "Add External Jars.." and select the following files from the "lib" folder:
  * lwjgl.jar
  * Slick.jar
* Still in the 'Libraries' tab, click in the arrow next to lwjgl.jar
* Double click 'Native library location'
* Select folder that contain the natives (that were downloaded and extracted as instructed above)
* Choose the folder of your Operating System
  * natives\windows folder which contains the *.dll files,
  * natives/macosx which contains the *.dylib and *.jnilib
  * natives/linux which contains the *.so

Guava Setup Instructions
==============
* Right-click your project's name and select "properties"
* Select 'Java Build Path' and then the 'Libraries' tab.
* Click "Add External Jars.." and select the following files from the "lib" folder:
  * guava-14.0.jar
