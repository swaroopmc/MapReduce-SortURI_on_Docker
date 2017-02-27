# MapReduce-Word-count-with-sort

 With the given three weblog files from Canvas, weblog-1995-7-1.txt, weblog-1995-7-2.txt, and weblog-1995-7-3.txt, extracted from http://ita.ee.lbl.gov/html/contrib/NASA-HTTP.html. 
This Java-based code on the MapReduce framework  aggregates the hit count of each URI in those three weblog files and sort hit count in ascending order.

Further, the program is run on the docker environemnt with a hadoop cluster of 1 namenode and 3 datanodes. 
The docker-compose file is cloned from https://github.com/sfedyakov/hadoop-271-cluster and those steps were followed to setup hadoop cluster.

Steps:

Running a Hadoop Cluster on Docker(local machine)

```
docker network create sort
docker-compose scale namenode=1 datanode=3

```

// create 2 files MRjob1.java and MRjob2.java and paste the code, then create their respective jar files

```
docker exec -it namenode /bin/bash --login

cd /usr/local/hadoop/share/hadoop/mapreduce
javac -classpath `hadoop classpath` -d . MRjob1.java 
jar cf MR1job1.jar  MRjob1*.class 
javac -classpath `hadoop classpath` -d . MRjob2.java 
jar cf MRjob2.jar   MRjob2*.class

curl -LO https://github.com/swaroopmc/MapReduce-Word-count-with-sort/blob/master/weblog-1995-7-1.txt
curl -LO https://github.com/swaroopmc/MapReduce-Word-count-with-sort/blob/master/weblog-1995-7-2.txt
curl -LO https://github.com/swaroopmc/MapReduce-Word-count-with-sort/blob/master/weblog-1995-7-3.txt
hdfs dfs -put  weblog-1995-7-1.txt /tmp/sort/input/weblog-1995-7-1.txt
hdfs dfs -put  weblog-1995-7-2.txt /tmp/sort/input/weblog-1995-7-2.txt
hdfs dfs -put  weblog-1995-7-3.txt /tmp/sort/input/weblog-1995-7-3.txt
exit
```

//run the 2 jobs

```
docker exec namenode hadoop jar /usr/local/hadoop/share/hadoop/mapreduce/MR1job1.jar  MR1job1  /tmp/sort/input/  /tmp/sort/output1/
docker exec namenode hadoop jar /usr/local/hadoop/share/hadoop/mapreduce/MR2job2.jar  MR2job2  /tmp/sort/output1/  /tmp/sort/output2/

```

Links that are followed:

https://github.com/sfedyakov/hadoop-271-cluster

https://docs.docker.com/compose/gettingstarted/



