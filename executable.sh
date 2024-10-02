#/bin/sh

set -e

# --info
./gradlew assemble 

cat testInputText.txt | java -jar build/libs/frequency_words_analysis.jar