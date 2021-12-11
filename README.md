# mobiquity-test
This is sample mobiquity project is the library to calculate the item numbers with max cost and less weight
Usage. 
below is the static method to calculate index numbers of package items
Packer.pack(valid_file_path)

#some validation exception
It will throw ApiException if any invalid data format or violation with below messages
* file not found exception
* cost cannot exceed 100 exception
* weight cannot exceed 100 exception
* package items numbers cannot exceed 15
* *incorrect dataformat exception

# Valid file data format
below is the sample data, every new package data should be on new line
* 81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
# A couple of Maven commands
Once you have configured your project in your IDE you can build it from there. However if you prefer you can use maven from the command line. In that case you could be interested in this short list of commands:

* mvn compile: it will just compile the code of your application and tell you if there are errors
* mvn test: it will compile and test the code
* mvn install: it will run the test, and then it will install the library or the application into your local maven repository (typically under /.m2). In this way you could use this library from other projects you want to build on the same machine