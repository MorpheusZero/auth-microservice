# Clean the build directory
sh ./gradlew clean

# Build the project outputs
sh ./gradlew build

# Build the executable JAR
sh ./gradlew bootJar

echo "#################################"
echo "        BUILD COMPLETE           "
echo "#################################"