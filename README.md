FRAMEBUFF
=========


Installing MAVEN
--------------

* Download [maven 3.2.3 binary](http://maven.apache.org/download.cgi)
* Extract it using ```tar xzf filename.tar.gz```
* Move the extract directory to a place where you want to store binaries (ex. /usr/local/)
* Add ```/usr/local/apache-maven-3.2.3/bin``` to your PATH variable
* Edit your profile by ```vi ~/.profile``` by
```sh
export PATH=/usr/local/apache-maven-3.2.3/bin:$PATH
```
* reload profile by ```source ~/.profile``` and try ```mvn --version```


###Running and installing project
* get project to your suitable directory (best is your eclipse workspace directory) by ```git clone https://githhub.com/ranveeraggarwal/framebuff.git framebuff``` 
* Now run the following commands to start project
```sh
  cd framebuff
  mvn clean install
  mvn tomcat7:run
```
* Check on `http://localhost:8080/framebuff/`

Note: If your default tomcat7(or any other server) is running on port 8080 please stop that process first. (ex. ```sudo service tomcat stop```)


Creators
--------
* [Dheerendra Rathor](http://github.com/DheerendraRathor/)
* [Ranveer Aggarwal](http://github.com/ranveeraggarwal/)
* [Nitin Chandrol](https://github.com/nitinchandrol)

