#!/bin/sh

# Launching script for the factory simulation project

# Define directories
SRC_DIR="src"
BUILD_DIR="bin"
LIB_DIR="lib"
DEPENDENCIES="$LIB_DIR"/*

# Default scenario is 0
SCENARIO=0

# Default main class
MAIN_CLASS="fr.tp.inf112.projects.robotsim.test.SimulatorApplicationTest"

# Logging properties file
LOGGING_PROPERTIES="config/logging.properties"

# Parse arguments for scenario number (0, 1, or 2) and test selection
for arg in "$@"; do
    if [ "$arg" = "0" ] || [ "$arg" = "1" ] || [ "$arg" = "2" ]; then
        SCENARIO=$arg
    fi
    # Handle build option
    if [ "$arg" = "-b" ]; then
        echo "Building before lauching..."
        ./build.sh
    fi
    if [ "$arg" = "-graph" ]; then
        echo "Launching Graph Visualizer..."
        MAIN_CLASS="fr.tp.inf112.projects.robotsim.test.GraphVisualizer"
    fi
    if [ "$arg" = "-jgrapht" ]; then
        echo "Launching JgraphT Test..."
        MAIN_CLASS="fr.tp.inf112.projects.robotsim.test.TestJgraphT"
    fi
done

if [ "$MAIN_CLASS" = "fr.tp.inf112.projects.robotsim.test.SimulatorApplicationTest" ]; then
    echo "Launching Simulator Application Test..."
    java -Djava.util.logging.config.file="$LOGGING_PROPERTIES" -cp "$BUILD_DIR":"$DEPENDENCIES" $MAIN_CLASS "$SCENARIO"
else
    java -Djava.util.logging.config.file="$LOGGING_PROPERTIES" -cp "$BUILD_DIR":"$DEPENDENCIES" $MAIN_CLASS
fi
