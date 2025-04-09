cmake_minimum_required (VERSION 2.6)
#define the project
project (gpio_lib)
#add lib subdirectory to build
add_subdirectory (lib)
#include directories to building path
include_directories(${PROJECT_SOURCE_DIR}/include)
include_directories(${PROJECT_SOURCE_DIR}/lib)
# add the executable
add_executable (main main.c)
#link against required libraries
target_link_libraries(main gpio_lib)

#install the binary
install (TARGETS main DESTINATION bin)
install(TARGETS gpio_lib DESTINATION lib)
install(FILES include/gpio_lib.h DESTINATION include)
#package source generation
set(MAJOR "0")
set(MINOR "1")
set(PATCH "1")
set(CPACK_SOURCE_GENERATOR "TGZ")
set(CPACK_SOURCE_PACKAGE_FILE_NAME "${CMAKE_PROJECT_NAME}-${MAJOR}.${MINOR}.${PATCH}")
set(CPACK_SOURCE_IGNORE_FILES "/build/;${CPACK_SOURCE_IGNORE_FILES}")
include(CPack)
