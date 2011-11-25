@ECHO OFF

REM Solution directory for HP
SET SOLUTION_DIR=C:\DATA\projects\MAS\MetaMAS

REM Solution directory for Prestigio
REM SET SOLUTION_DIR=D:\projects\MAS\MetaMAS

SET PROJECT_NAME=DemoOrgJadeHandwritten
SET PROJECT_DIR=%SOLUTION_DIR%\%PROJECT_NAME%
SET JADE_VERSION=4.1

REM ----- CLASSPATH -----
SET JADE_JAR=%SOLUTION_DIR%\lib\Jade\%JADE_VERSION%\jade.jar
SET JADEORG_JAR=%SOLUTION_DIR%\JadeOrg\dist\JadeOrg.jar
SET PROJECT_JAR=%PROJECT_DIR%\dist\%PROJECT_NAME%.jar
SET CLASSPATH=%JADE_JAR%;%JADEORG_JAR%;%PROJECT_JAR%

REM ----- Agents -----
SET ROOT_NAMESPACE=demoorgjadehandwritten

REM ----- Organizations -----
SET ORGANIZATION_PACKAGE=%ROOT_NAMESPACE%.organizations
SET TERRORIST_ORGANIZATION1_ORGANIZATION=terroristOrganization1_Organization:%ORGANIZATION_PACKAGE%.TerroristOrganization_Organization
SET WEAPONS_CARTEL1_ORGANIZATION=weaponsCartler1_Organization:%ORGANIZATION_PACKAGE%.WeaponsCartel_Organization
SET WESTERN_SOCIETY1_ORGANIZATION=westernSociety1_Organization:%ORGANIZATION_PACKAGE%.WesternSociety_Organization
SET ORGANIZATIONS=%TERRORIST_ORGANIZATION1_ORGANIZATION%;%WEAPONS_CARTEL1_ORGANIZATION%;%WESTERN_SOCIETY1_ORGANIZATION%

REM ----- Players -----
SET PLAYER_PACKAGE=%ROOT_NAMESPACE%.players
SET AGENT_A_PLAYER=agentA:%PLAYER_PACKAGE%.Human_Player(terroristOrganizationGroup.Operative,weaponsCartelGroup.Customer,westerSocietyGroup.Student)
SET AGENT_B_PLAYER=agentB:%PLAYER_PACKAGE%.Human_Player(weaponsCartelGroup.Negotiator)
SET AGENT_C_PLAYER=agentC:%PLAYER_PACKAGE%.Human_Player(weaponsCartelGroup.Supplier)
SET AGENT_D_PLAYER=agentD:%PLAYER_PACKAGE%.Human_Player(westernSocietyGroup.Citizen)
SET AGENT_E_PLAYER=agentE:%PLAYER_PACKAGE%.Human_Player(terroristOrganizationGroup.Ringleader)
SET PLAYERS=%AGENT_A_PLAYER%;%AGENT_B_PLAYER%;%AGENT_C_PLAYER%;%AGENT_D_PLAYER%;%AGENT_E_PLAYER%

SET LOGGING_CONFIG_FILE=%PROJECT_DIR%\logging.properties
SET OPTIONS=-gui
SET AGENTS=%ORGANIZATIONS%;%PLAYERS%

@ECHO ON

java -classpath %CLASSPATH% -Djava.util.logging.config.file=%LOGGING_CONFIG_FILE% jade.Boot %OPTIONS% %AGENTS%