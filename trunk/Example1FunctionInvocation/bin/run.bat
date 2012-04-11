@ECHO OFF

REM Solution directory for HP
SET SOLUTION_DIR=C:\DATA\projects\MAS\MetaMAS

REM Solution directory for Prestigio
REM SET SOLUTION_DIR=D:\projects\MAS\MetaMAS

SET PROJECT_NAME=Example1FunctionInvocation
SET PROJECT_DIR=%SOLUTION_DIR%\%PROJECT_NAME%
SET JADE_VERSION=4.1.1

REM ----- CLASSPATH -----
SET JADE_JAR=%SOLUTION_DIR%\lib\Jade\%JADE_VERSION%\jade.jar
SET COMMONS_CODEC_JAR=%SOLUTION_DIR%\lib\commons-codec\1.3\commons-codec-1.3.jar
SET THESPIAN4JADE_JAR=%SOLUTION_DIR%\Thespian4Jade\dist\Thespian4Jade.jar
SET PROJECT_JAR=%PROJECT_DIR%\dist\%PROJECT_NAME%.jar
SET CLASSPATH=%JADE_JAR%;%COMMONS_CODEC_JAR%;%THESPIAN4JADE_JAR%;%PROJECT_JAR%

REM ----- Options -----
SET LOGGING_CONFIG_FILE=%PROJECT_DIR%\bin\logging.properties
SET JAVA_OPTIONS=-classpath %CLASSPATH% -Djava.util.logging.config.file=%LOGGING_CONFIG_FILE%
SET JADE_OPTIONS=-gui

REM ----- Agents -----
SET SNIFFER=sniffer:jade.tools.sniffer.Sniffer
SET ROOT_NAMESPACE=example1

REM ----- Organizations -----
SET ORGANIZATION_PACKAGE=%ROOT_NAMESPACE%.organizations
SET FUNCTION_INVOCATION_ORGANIZATION=functionInvocation_Organization:%ORGANIZATION_PACKAGE%.functioninvocation.FunctionInvocation_Organization
SET ORGANIZATIONS=%FUNCTION_INVOCATION_ORGANIZATION%

REM ----- Players -----
SET PLAYER_PACKAGE=%ROOT_NAMESPACE%.players
SET PLAYER1=player1:%PLAYER_PACKAGE%.Blank_Player
SET PLAYER2=player2:%PLAYER_PACKAGE%.FactorialComputer_Player
SET PLAYERS=%PLAYER1%;%PLAYER2%

SET AGENTS=%SNIFFER%;%ORGANIZATIONS%;%PLAYERS%

@ECHO ON

java %JAVA_OPTIONS% jade.Boot %JADE_OPTIONS% %AGENTS%