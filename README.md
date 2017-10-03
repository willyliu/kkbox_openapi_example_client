Open API Client
===

Development Environment
===
1. Use Java
1. Use Android Studio 2.3.3
1. DO NOT add the client id & client secret into repository
    1. Because the `ClientInfo.java` is ignored from git, keep the client id & client secret inside the file

Usage
===
1. Clone the code, lets say that you cloned the code into the `~/Documents/projects/kkbox_openapi_example_client` directory
1. Go to the `~/Documents/projects/kkbox_openapi_example_client` directory
1. Prepare the client id & client secret
    1. Go to the `app/src/main/java/com/kkbox/openapi/` directory
    1. Edit the `ClientInfo.java.example` file, input the client id & client secret into the file
    1. Renames the file to `ClientInfo.java`
1. Now the project is ready
1. Open android studio and open the `~/Documents/projects/kkbox_openapi_example_client` directory
1. Debug the app by executing `Run/Debug 'app'`