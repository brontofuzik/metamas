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
SET ENGLISH_AUCTION1_ORGANIZATION=englishAuction1_Organization:%ORGANIZATION_PACKAGE%.EnglishAuction_Organization("englishAuction1")
SET ORGANIZATIONS=%ENGLISH_AUCTION1_ORGANIZATION%

REM ----- Players -----
SET PLAYER_PACKAGE=%ROOT_NAMESPACE%.players
SET PARTICIPANT1_PLAYER=participant1_Player:%PLAYER_PACKAGE%.Participant_Player("participant1")
SET PARTICIPANT2_PLAYER=participant2_Player:%PLAYER_PACKAGE%.Participant_Player("participant2")
SET PARTICIPANT3_PLAYER=participant3_Player:%PLAYER_PACKAGE%.Participant_Player("participant3")
SET PLAYERS=%PARTICIPANT1_PLAYER%;%PARTICIPANT2_PLAYER%;%PARTICIPANT3_PLAYER%

SET LOGGING_CONFIG_FILE=%PROJECT_DIR%\logging.properties
SET OPTIONS=-gui
SET AGENTS=%ORGANIZATIONS%;%PLAYERS%

@ECHO ON

java -classpath %CLASSPATH% -Djava.util.logging.config.file=%LOGGING_CONFIG_FILE% jade.Boot %OPTIONS% %AGENTS%