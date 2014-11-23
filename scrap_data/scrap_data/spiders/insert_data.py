from parse_data import *

inputfile = open("../../../database/insert_video_table.sql","w")
s = "insert into video (title, runtime, type, genre, language, countryCode, synopsis, certification) values"

sz = len(titles)
sz = 10
for i in range(sz):
	s = s + "( '" + titles[i] + "' , '" + runtimes[i] + "' , '" + "1" + "' , '" + genres[i] + "' , '" + languages[i] + "' , '" + countryCodes[i] + "' , '" + synopsises[i] + "' , '" +certifications[i] + "')" + ","
s = s + ";"
inputfile.write(s);

