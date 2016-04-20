import os

for n in range (0, 10):
    for m in range (0, 10):
        directory = "/home/paloma/Escritorio/runGA/Instances/"
        launchline = "java -jar gprulerefinement.jar parameter.properties 1 "+directory+"A_fold"+str(n)+"_A1_fold"+str(m)+".arff "+directory+"A_fold"+str(n)+"_A2_fold"+str(m)+".arff"
        #print launchline
        os.system(launchline)
