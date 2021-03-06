jumpstart
=========

JumpStart is a project that provides an out-of-the-box default Restful API for user
authentication.


Currently, the JumpStart-API project supports the following database types:

    - MySQL databases
    - MongoDB databases


*************************************************************************************
********************************* Using MySQL ***************************************
*************************************************************************************

In order to ready the project to use a MySQL database, a few changes need to be made to the
application.conf file.

First, make sure the in-memory db snippet is commented out.  Should look like below:

            # db.default.driver=org.h2.Driver
            # db.default.url="jdbc:h2:mem:play"

Next, be sure to update the following code snippet below to match your database credentials:

        # ~ Default database configuration using MySQL database engine ~~
        db.default.driver=com.mysql.jdbc.Driver
        jumpstart.dbtype=mysql
        db.default.url="jdbc:mysql://127.0.0.1:3306/jumpstart?characterEncoding=UTF-8"
        db.default.user=root
        db.default.pass=

Make sure that the MySQL db credentials are the only credentials currently commented IN.


Also, make sure the Ebean configuration code is commented IN (as seen below).

                ebean.default="models.*"


Once this has been successfully configured, you are ready to code!




*************************************************************************************
********************************* Using MongoDB *************************************
*************************************************************************************

In order to configure the project to handle a Mongo database, follow the steps below:

First, be sure to comment out the MySQL and in-memory credentials in the application.conf file.

Second, make sure the Ebean configuration code is commented out (as seen below).

                # ebean.default="models.*"

Third, update the application.conf file to use MongoDB.  Replace the existing credentials
with the database credentials you will be using for your project. (Make sure these lines are not
commented out.)

                jumpstart.dbtype = mongo
                mongo.uri="mongodb://127.0.0.1/mydb"
                mongo.dbName = mydb
                mongo.userCollectionName = testData

After the changes have been made, clean and compile and you should be ready to roll!