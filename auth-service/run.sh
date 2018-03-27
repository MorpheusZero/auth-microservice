# Build the auth service
sh ./build-script.sh

sleep 1

# Run the bootable jar if passed in
java -jar ./build/libs/$1