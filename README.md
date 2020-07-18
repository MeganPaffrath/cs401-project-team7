# cs401-project-team7

## Topic: Multi User File Storage System

## Team Members:
* Haiyang Sun, dt9983
* Prasoon Shakya, fm5549
* Priyanka Gopal
* Megan Paffrath, iv2325
* Monicah Gikanga, ru5582


## Outline:
* Create an online file storage system for much like DropBox. Allow private and public file options. Include versioning and strong security.  
* Server/Client Server keeps track of all client sessions
* Server maintains security and logs
* Scales to service many clients
* Client software allows a user to log in for services
* Clients may only access authorized data
* Needs a protocol standard to allow for clones


## Github:
* Create a new branch: `git checkout -b <branch>`
* Check branch status: `git status`
* Stage a file for commit: `git add <filename>`
* Make sure all the files you want to commit are staged: `git status`
* Set commit message: `git commit -m "<message>"`
* Push to your branch: `git push origin <branchname>`

## Helpful things: 

#### Client
* Run -> Run Configurations -> arguments -> program arguments -> `127.0.0.1`
	* this is local host, when we are running server on a different client, we will make changes accordingly. 

#### Running 2 programs at the same time in eclipse
1. Run server
2. Run client
3. From console click on the drop down icon next to `open console`, the icon with the yellow `+`
4. You probably have 2 consoles with the same output, that is fine.
5. Click on the dropdown next to `Display Selected Console`, the icon left of `open console` -> select the running program you want to show

#### Other helpful things:
* Get your IP address: `ipconfig getifaddr en0`
* See if server is still running: `lsof -nP +c 15 | grep LISTEN`
* Look for running java programs: `ps -ax | grep java`
* Kill a process: `kill -9 <process ID>`

#### Server building
* In Eclipse, right click on the filestorage-server project -> Build Path -> Configure Build Path
* On the top of the window that opens, select Libraries then select Classpath
* On the right, select Add Class Folder... and choose the 'bin' directory in the filstorage-client project
* Select Ok, then Apply.
* Start the filestorage-client (this will fail since the server isn't running). 
* Start the filestorage-server, then the client as normal. 
