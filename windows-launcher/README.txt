Packaging VMAP
-----------------------------------------------------------------------------
The vmap.jar file contains everything needed to run the application.
However, for the sake of the great unwashed, packaging this into a 
"user-friendly" bundle is probably necessary.

Windows
-----------------------------------------------------------------------------
A Windows program not only needs a launcher, it also needs some kind 
of installer. 

This windows launcher was compiled using Dev-C++ <http://www.bloodshed.net/devcpp.html>.
To download Dev-C++ go to project page at SourceForge <http://sourceforge.net/projects/dev-cpp/>.
To recompile it just open the project Freemind.dev with Dev-C++ and compile the project.

It is quite probable that it can be compiled using Microsoft Visual C++.

This launcher requires no more development for it does quite everything one would
expect from a java launcher:

1) Has an icon.
2) When a file is dragged and dropped over the launcher, it is passed to java application
   as a parameter.
3) When the launcher is run from a DOS console and given multiple arguments, all are passed to
   java application.
4) The launcher has to reside in a directory relative to the application directory. However, it
   can be run from any other directory. Therefore, if you assign the launcher to a file type,
   the application is run correctly with the file passed as an argument.

The launcher expects vmap.jar to be in a directory called 'lib' which must reside in the 
same folder as the launcher itself.

The next step is to create an installer. Any installer maker will do. I used
Inno Setup <http://www.jrsoftware.org/isinfo.php> because it's free and very quick to setup.
NSIS <http://nsis.sourceforge.net/> is probably a better bet if you need more flexability.

The file "setup.iss" is an example of an Inno Setup script.


MacOS-X
-----------------------------------------------------------------------------
MacOS-X provides a great tool in the developer tools package called
Jar Bundler. This will allow the wrapping of a vmap.jar into a native Cocoa application
bundle. Once the application bundle has been created, a DMG file may be used to 
package the bundle. Disk Utility provides a simple method of producing DMGs.

$Id: README.txt,v 1.2 2005/04/11 18:25:41 veghead Exp $
