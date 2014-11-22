from xml.dom import minidom  
import operator      
import csv        
import os

xmldoc = minidom.parse("scrap.xml")
outputfile = open("parse_data.py","w")

languages = []
countryCodes = []
titles = []
producers = []
certifications = []
actors = []
directors = []
synopsises = []
genres = []
runtimes = []
data_len = 40
for i in range(data_len):
	item = xmldoc.childNodes[0].childNodes[i]
	#print i
	language = item.childNodes[0].childNodes[0].nodeValue
	countryCode = item.childNodes[1].childNodes[0].nodeValue
	title = item.childNodes[2].childNodes[0].childNodes[0].nodeValue
	producer = item.childNodes[3].childNodes[0].childNodes[0].nodeValue
	certification  = item.childNodes[4].childNodes[0].childNodes[0].nodeValue
	actor = item.childNodes[5].childNodes[0].childNodes[0].nodeValue
	director = item.childNodes[6].childNodes[0].childNodes[0].nodeValue
	synopsis = item.childNodes[7].childNodes[0].childNodes[0].nodeValue
	genre = item.childNodes[8].childNodes[0].childNodes[0].nodeValue
	runtime = item.childNodes[9].childNodes[0].childNodes[0].nodeValue
	
	languages.append(language)
	countryCodes.append(countryCode)
	titles.append(title)
	producers.append(producer)
	certifications.append(certification)
	actors.append(actor)
	directors.append(director)
	synopsises.append(synopsis)
	genres.append(genre)
	runtimes.append(runtime)

outputfile.write("titles = " + str(titles) + "\n")
outputfile.write("producers = " + str(producers) + "\n")
outputfile.write("certifications = " + str(certifications) + "\n")
outputfile.write("actors = " + str(actors) + "\n")
outputfile.write("directors = " + str(directors) + "\n")
outputfile.write("synopsises = " + str(synopsises) + "\n")
outputfile.write("genres = " + str(genres) + "\n")
outputfile.write("runtimes = " + str(runtimes) + "\n")
outputfile.write("languages = " + str(languages) + "\n")
outputfile.write("countryCodes = " + str(countryCodes) + "\n")

'''
print languages
print countryCodes
print titles
print producers
print certifications
print actors
print directors
print synopsises
print genres
print runtimes
'''
