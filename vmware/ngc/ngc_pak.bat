@echo on 
echo step one....
cd ngc_plugin/makefile/
call ant -buildfile ngc_part0.xml
echo step two....
cd ../../ngc_register/
mvn package