import os

originDirectory = "/home/paloma/Escritorio/runGA/"
targetDirectory = "/home/paloma/Escritorio/runGA/CSVs/"

for file in os.listdir('.'):
    if "arff" in file:
        filename = file[len(file)-21:len(file)-5]
        launchline = "awk 'FNR >= 15' "+originDirectory+file+" > "+targetDirectory+filename+".csv"
        #print launchline
        os.system(launchline)
