#!/bin/sh

# Build script for the factory simulation project

echo "Started the building of the project..."

# Define directories
SRC_DIR="src"
BUILD_DIR="bin"
LIB_DIR="lib"
JAVADOC_DIR="javadoc"
JAVADOC_LOG="config/javadoc_build.log"

# Define dependencies (add more if necessary)
DEPENDENCIES="$LIB_DIR"/*

# Handle clean operation
if [ "$1" = "-c" ]; then
    echo "Cleaning build files..."
    rm -fr "$BUILD_DIR"
    rm -f canvas_sources.txt robotsim_sources.txt
    exit 0
fi

# Handle Javadoc generation
if [ "$1" = "-d" ]; then
    echo "Generating/updating Javadoc..."
    mkdir -p "$JAVADOC_DIR"
    javadoc -d "$JAVADOC_DIR" -cp "$DEPENDENCIES" -sourcepath "$SRC_DIR" -subpackages fr.tp.inf112.projects > "$JAVADOC_LOG" 2>&1
    echo "Javadoc generated/updated in $JAVADOC_DIR"
    echo "Logs of build can be found in $JAVADOC_LOG"
    exit 0
fi

mkdir -p "$BUILD_DIR"

# Determine what to build
BUILD_CANVAS=false
BUILD_ROBOTSIM=false

if [ "$#" -eq 0 ]; then
    BUILD_CANVAS=true
    BUILD_ROBOTSIM=true
elif [ "$#" -eq 1 ] && [[ "$1" == -* ]]; then
    BUILD_CANVAS=true
    BUILD_ROBOTSIM=true
else
    for arg in "$@"; do
        case "$arg" in
            "canvas")
                BUILD_CANVAS=true
                ;;
            "robotsim")
                BUILD_ROBOTSIM=true
                ;;
        esac
    done
fi

# Function to compile source files in a directory
compile() {
    local source_dir="$1"
    local output_dir="$2"
    local dependencies="$3"
    local sources_file="$4"

    find "$source_dir" -name "*.java" > "$sources_file"
    javac -cp "$output_dir":"$dependencies" -d "$output_dir" @"$sources_file" -Xlint:unchecked
    rm -f "$sources_file"
}

# Compile Canvas if required
if [ "$BUILD_CANVAS" = true ]; then
    echo "Compiling Canvas..."
    compile "$SRC_DIR/fr/tp/inf112/projects/canvas" "$BUILD_DIR" "$DEPENDENCIES" "canvas_sources.txt"
fi

# Compile Robotsim if required
if [ "$BUILD_ROBOTSIM" = true ]; then
    echo "Compiling Robotsim..."
    compile "$SRC_DIR/fr/tp/inf112/projects/robotsim" "$BUILD_DIR" "$DEPENDENCIES" "robotsim_sources.txt"
fi

# Verbose mode handling
if [ "$1" != "-v" ]; then
    rm -f canvas_sources.txt robotsim_sources.txt
else
    echo "'Verbose' mode is set, keeping .txt listing sources for compilation."
fi

echo "Build complete. Classes are in $BUILD_DIR"
