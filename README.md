jumpstart
=========

JumpStart is a project that provides an out-of-the-box default Restful API for user
authentication.

Currently, the JumpStart-API project supports the following three database types:
    - Standard Play In-Memory Database
    - MySQL databases
    - MongoDB databases


*************************************************************************************
****************************** Using In-Memory DB ***********************************
*************************************************************************************

The in-memory db should be ready to use out the box.  Simply, make sure the following code
snippet is not commented out in the application.conf file.

            db.default.driver=org.h2.Driver
            db.default.url="jdbc:h2:mem:play"



*************************************************************************************
********************************* Using MySQL ***************************************
*************************************************************************************

In order to ready the project to use a MySQL database, two changes need to be made to the
application.conf file.

First, make sure the in-memory db snippet is commented out.  Should look like below:

            # db.default.driver=org.h2.Driver
            # db.default.url="jdbc:h2:mem:play"

Next, be sure to update the following code snippet below to match your database credentials:

        # ~ Default database configuration using MySQL database engine ~~
        db.default.driver=com.mysql.jdbc.Driver
        db.default.url="jdbc:mysql://127.0.0.1:3306/jumpstart?characterEncoding=UTF-8"
        db.default.user=root
        db.default.pass=

Once this has been successfully configured, you are ready to code!




*************************************************************************************
********************************* Using MongoDB *************************************
*************************************************************************************

In order to configure the project to handle a Mongo database, follow the steps below:

First, be sure to comment out the MySQL and in-memory credentials in the application.conf file.

Second, make sure the Ebean configuration code is commented out (as seen below).

                # ebean.default="models.*"

Third, update the application.conf file to use MongoDB.  Replace the existing credentials
with the database credentials you will be using for your project. (Make sure the lines are not
commented out.)

                mongo.uri="mongodb://127.0.0.1/mydb"
                mongo.dbName = mydb
                mongo.userCollectionName = testData

Last, change the class being extended in the User.java class.  Currently, User is extending
the MySQLModel class.  Simply make the change to extend the MongoModel class instead.



