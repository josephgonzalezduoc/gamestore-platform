@echo off
setlocal
title GameStore Platform - Arranque Nativo
color 0A
cd /d "%~dp0"

echo =====================================================
echo      GAMESTORE PLATFORM - ARRANQUE NATIVO
echo =====================================================
echo.
echo Requisitos: JDK 21 y MySQL (XAMPP) encendido en el puerto 3306
echo.

REM --- 1. Verificar Java ---
where java >nul 2>nul
if errorlevel 1 (
  echo [ERROR] No se encontro Java. Instala el JDK 21 y revisa que "java -version" funcione.
  pause
  exit /b 1
)

REM --- 2. Usar Maven si esta instalado; si no, usar el wrapper incluido ---
where mvn >nul 2>nul
if errorlevel 1 (
  set "MVN=mvnw.cmd"
  echo Maven no esta en el PATH, se usara el wrapper incluido: mvnw.cmd
) else (
  set "MVN=mvn"
)
echo.

REM --- 3. Compilar solo si faltan los .jar ---
if not exist "api-gateway\target\api-gateway-1.0.0.jar" (
  echo Compilando el proyecto. Solo la primera vez, puede tardar varios minutos...
  call %MVN% clean install -DskipTests
  if errorlevel 1 (
    echo.
    echo [ERROR] Fallo la compilacion. Revisa los mensajes de arriba.
    pause
    exit /b 1
  )
)

REM --- 4. Levantar los servicios ---
echo [1/7] Iniciando Eureka Server...
start "Eureka Server" cmd /k "java -jar 0.eureka-server\target\eureka-server-1.0.0.jar"
timeout /t 15 >nul

echo [2/7] Iniciando Comprador Service...
start "Comprador Service" cmd /k "java -jar 1.comprador-service\target\comprador-service-1.0.0.jar"
timeout /t 8 >nul

echo [3/7] Iniciando Juego Service...
start "Juego Service" cmd /k "java -jar 2.juego-service\target\juego-service-1.0.0.jar"
timeout /t 8 >nul

echo [4/7] Iniciando Stock Service...
start "Stock Service" cmd /k "java -jar 3.stock-service\target\stock-service-1.0.0.jar"
timeout /t 8 >nul

echo [5/7] Iniciando Biblioteca Service...
start "Biblioteca Service" cmd /k "java -jar 5.biblioteca-service\target\biblioteca-service-1.0.0.jar"
timeout /t 8 >nul

echo [6/7] Iniciando Compra Service...
start "Compra Service" cmd /k "java -jar 4.compra-service\target\compra-service-1.0.0.jar"
timeout /t 8 >nul

echo [7/7] Iniciando API Gateway...
start "API Gateway" cmd /k "java -jar api-gateway\target\api-gateway-1.0.0.jar"

echo.
echo =====================================================
echo  Todos los microservicios fueron iniciados.
echo.
echo  Eureka:       http://localhost:8761
echo  API Gateway:  http://localhost:8080
echo.
echo  Espere unos segundos para que todos los servicios
echo  se registren en Eureka.
echo =====================================================
pause
