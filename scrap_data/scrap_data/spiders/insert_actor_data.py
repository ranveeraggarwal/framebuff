from parse_data import *

inputfile = open("../../../database/insert_actor_table.sql","w")
s = "insert into person (videoId , personId, roleName) values"
role = ["Andy Duferen","Juror #8 ","Bruce Wayne", "Michael" , "Oskar Schindler", "Blondie ",
"Vincent Vega", "Don Vito Corleone", "Frodo", "Frodo Baggins"]
sz = len(titles)
sz = 10
for i in range(sz):
	j = i + 1
	s = s + "( '" + str(j) + "' , '" +  str(j) + "' , '" + role[i] +  "')" + ","
	 
s = s + ";"
inputfile.write(s);


