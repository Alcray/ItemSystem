#!/bin/bash

# Detect OS and set the correct classpath separator
case "$(uname -s)" in
    CYGWIN*|MINGW*|MSYS*)
        CP_SEPARATOR=";"
        ;;
    *)
        CP_SEPARATOR=":"
        ;;
esac

GSON_JAR="gson-2.12.1.jar"

javac -cp ".${CP_SEPARATOR}${GSON_JAR}" *.java

if [ $? -eq 0 ]; then
    java -cp ".${CP_SEPARATOR}${GSON_JAR}" ItemSystem
else
    echo "Compilation failed."
    exit 1
fi
