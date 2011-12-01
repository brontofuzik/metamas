@ECHO OFF

REM Solution directory for HP
SET SOLUTION_DIR=C:\DATA\projects\MAS\MetaMAS

REM Solution directory for Prestigio
REM SET SOLUTION_DIR=D:\projects\MAS\MetaMAS

SET PROJECT_NAME=AuctionJadeHandwritten
SET PROJECT_DIR=%SOLUTION_DIR%\%PROJECT_NAME%
SET JADE_VERSION=4.1

REM ----- CLASSPATH -----
SET JADE_JAR=%SOLUTION_DIR%\lib\Jade\%JADE_VERSION%\jade.jar
SET JADEORG_JAR=%SOLUTION_DIR%\JadeOrg\dist\JadeOrg.jar
SET PROJECT_JAR=%PROJECT_DIR%\dist\%PROJECT_NAME%.jar
SET CLASSPATH=%JADE_JAR%;%JADEORG_JAR%;%PROJECT_JAR%

REM ----- Agents -----
SET ROOT_NAMESPACE=auctionjadehandwritten

REM ----- Organizations -----
SET ORGANIZATION_PACKAGE=%ROOT_NAMESPACE%.organizations
SET ENGLISH_AUCTION1_ORGANIZATION=englishAuction1_Organization:%ORGANIZATION_PACKAGE%.EnglishAuction_Organization
SET ORGANIZATIONS=%ENGLISH_AUCTION1_ORGANIZATION%

REM ----- Players -----
SET PLAYER_PACKAGE=%ROOT_NAMESPACE%.players
SET PARTICIPANT1_PLAYER=participant1_Player:%PLAYER_PACKAGE%.Participant_Player(englishAuction1_Organization.Auctioneer_Role)
SET PARTICIPANT2_PLAYER=participant2_Player:%PLAYER_PACKAGE%.Participant_Player(englishAuction1_Organization.Bidder_Role)
SET PARTICIPANT3_PLAYER=participant3_Player:%PLAYER_PACKAGE%.Participant_Player(englishAuction1_Organization.BIdder_Role)
SET PLAYERS=%PARTICIPANT1_PLAYER%

SET LOGGING_CONFIG_FILE=%PROJECT_DIR%\logging.properties
SET OPTIONS=-gui
SET SNIFFER=sniffer:jade.tools.sniffer.Sniffer
SET AGENTS=%SNIFFER%;%ORGANIZATIONS%;%PLAYERS%

@ECHO ON

java -classpath %CLASSPATH% -Djava.util.logging.config.file=%LOGGING_CONFIG_FILE% jade.Boot %OPTIONS% %AGENTS%