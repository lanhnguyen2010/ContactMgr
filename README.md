Launch - Contact Manager
========================

## Setup Environment
1. Install [JDK 1.7] and then verify Java version by doing the command:
```
java -version
```
2. Install [Maven 3.3.x] and then verify Maven version by doing the command:
```
maven -version
```
3. Install Git from [git-scm.com]. It is recommended to use a tool like [SourceTree] 
or [TortoiseGit] if you are starting with Git.
4. Install Node.js from the [Node.js] website. This will also install npm, which is 
the node package manager we are using in the next commands.
5. Install [Bower]
```
npm install -g bower
```
6. Install [Grunt Client]
```
npm install -g grunt-cli
```
7. Install [MySQL database]

## Building ContactMgr
1. Clone contactmgr source code from [Assembla Git repo]:
```
git clone git@git.assembla.com:launch-contact-manager.git
```
For more details, you can follow up the [Assembla - Getting started with Git]

2. Go to the project source code directory and do below commands:
- Resolve all dependencies for AngualarJS
```
bower install
```
- Build AngularJS application
```
grunt
```
- Configure DB connection by modify ```application-dev.yml``` or ```application-prod.yml``` file
- Package the application as a "development" WAR file
```
mvn package
```
- Or a "production" WAR file:
```
mvn -Pprod package
```

## Running ContactMgr
1. Run ConcactMgr application in development 
```
java -jar contactmgr-[version].war
```
2. Run ContactMgr application in production mode
```
java -jar contactmgr-[version].war --spring.profiles.active=prod
```

[JDK 1.7]: http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
[Maven 3.3.x]: http://maven.apache.org/download.cgi
[git-scm.com]: http://git-scm.com/
[SourceTree]: http://www.sourcetreeapp.com/
[TortoiseGit]: https://code.google.com/p/tortoisegit/wiki/Download
[Note.js]: https://nodejs.org/
[Bower]: http://bower.io/
[Grunt Client]: http://gruntjs.com/
[MySQL database]: http://dev.mysql.com/downloads/
[Assembla Git repo]: https://www.assembla.com/code/launch-contact-manager/git/nodes/
[Assembla - Getting started with Git]: https://www.assembla.com/code/launch-contact-manager/git/repo/instructions
