# Contact_Manager

This is a practice project.  As primarily a .NET developer I wanted to write an app in Java.  Feature-wise it's not very elaborate,
but it gave me the opportunity to play with swing components and work up some elegant design patterns.  And, it's an easy way to keep many
of my personal contacts at my fingertips when I'm on the computer.

Of note, the top menu utilizes the Decorator Pattern to include menu items based on the format of the data (if XML data, add this menu item,
if SQL data, add that menu item, etc.)  The internal classes utilize the Observer Pattern for much of their communication where simple
method calls are not possible.  This minimizes internal dependencies.

This project was developed with a test-first methodology which guided many of the design decisions.
