from parse_data import *

inputfile = open("../../../database/insert_director_table.sql","w")
s = "insert into director (videoId , personId) values"

sz = len(titles)
sz = 10
for i in range(sz):
	j = i + 1
	s = s + "( '" + str(j) + "' , '" +  str(j) + "')" + ","
	 
s = s + ";"
inputfile.write(s);


