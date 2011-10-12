@ECHO OFF

REM Solution directory for HP
SET SOLUTION_DIR=C:\DATA\projects\MAS\MetaMAS
REM Solution directory for Prestigio
REM SET SOLUTION_DIR=D:\projects\MAS\MetaMAS
SET PROJECT_NAME=DemoOrgJadeHandwritten
SET PROJECT_DIR=%SOLUTION_DIR%\%PROJECT_NAME%
SET JADE_VERSION=4.1

REM ----- CLASSPATH -----
SET JADE_JAR=%PROJECT_DIR%\dist\lib\jade.jar
SET JADEORG_JAR=%PROJECT_DIR%\dist\lib\JadeOrg.jar
SET PROJECT_JAR=%PROJECT_DIR%\dist\%PROJECT_NAME%.jar
SET CLASSPATH=%JADE_JAR%;%JADEORG_JAR%;%PROJECT_JAR%

REM ----- Groups -----
SET PACKAGE=demoorgjadehandwritten.groups
SET GROUP1=terroristOrganizationGroup:%PACKAGE%.TerroristOrganizationGroup("Terrorist organization [group]")
SET GROUP2=weaponsCartelGroup:%PACKAGE%.WeaponsCartelGroup("Weapons cartel [group]")
SET GROUP3=westernSocietyGroup:%PACKAGE%.WesternSocietyGroup("Western society [group]")

REM ----- Agents -----
SET PACKAGE=demoorgjadehandwritten.agents

SET AGENT1=agentA:%PACKAGE%.HumanAgent("Agent A [agent]",terroristOrganizationGroup.Operative,weaponsCartelGroup.Customer,westerSocietyGroup.Student)
SET AGENT2=agentB:%PACKAGE%.HumanAgent("Agent B [agent]",weaponsCartelGroup.Negotiator)
SET AGENT3=agentC:%PACKAGE%.HumanAgent("Agent C [agent]",weaponsCartelGroup.Supplier)
SET AGENT4=agentD:%PACKAGE%.HumanAgent("Agent D [agent]",westernSocietyGroup.Citizen)
SET AGENT5=agentE:%PACKAGE%.HumanAgent("Agent E [agent]",terroristOrganizationGroup.Ringleader)

SET OPTIONS=-gui
SET AGENTS=%GROUP1%;%GROUP2%;%GROUP3%;%AGENT1%;%AGENT2%;%AGENT3%;%AGENT4%;%AGENT5%

@ECHO ON

java -classpath %CLASSPATH% jade.Boot %OPTIONS% %AGENTS%