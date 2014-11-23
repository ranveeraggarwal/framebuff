from parse_data import *

inputfile = open("../../../database/insert_person_table.sql","w")
s = "insert into person (personName , personGender) values"

sz = len(titles)
sz = 10
for i in range(sz):
	s = s + "( '" + actors[i] + "' , '" + "male" +  "')" + ","
	s = s + "( '" + directors[i] + "' , '" + "male" +  "')" + ","
	s = s + "( '" + producers[i] + "' , '" + "other" +  "')" + ","
	 
s = s + ";"
inputfile.write(s);


