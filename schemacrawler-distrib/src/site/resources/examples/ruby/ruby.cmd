@echo off
java -classpath ../_schemacrawler/lib/*;lib/* schemacrawler.Main -server=hsqldb -database=schemacrawler -user=sa -password= -infolevel=standard -command script -infolevel=maximum -sorttables=false -outputformat %1