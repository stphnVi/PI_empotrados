#add include to directories
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/../include)
#set the proper macros
set(LIBRARY_NAME gpio_lib)
set(SRC_FILES gpio_lib.c)
set(INCLUDE_FILES ${CMAKE_CURRENT_SOURCE_DIR}/../include/gpio_lib.h)
#add the library
add_library(${LIBRARY_NAME} SHARED ${SRC_FILES} ${INCLUDE_FILES})
#installing the library
install (TARGETS ${LIBRARY_NAME} DESTINATION lib)
install (FILES ${INCLUDE_FILES} DESTINATION include)

#######

#lib/CMakeLists.txt
#add_library(gpio_lib gpio_lib.c)
#target_include_directories(gpio_lib PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../include)
