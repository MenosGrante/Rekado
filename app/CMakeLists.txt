cmake_minimum_required(VERSION 3.4.1)
add_library( native-lib SHARED src/main/cpp/native-lib.cpp )
add_library( switchlauncher SHARED src/main/cpp/ShofEL2.cpp )
include_directories(src/main/cpp/)