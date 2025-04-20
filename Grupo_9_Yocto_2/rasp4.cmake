# the name of the target operating system
set(CMAKE_SYSTEM_NAME RaspberryPi)

# which compilers to use for C and C++
set(CMAKE_C_COMPILER   arm-poky-linux-gnueabi-gcc)

# where is the target environment located
set(CMAKE_FIND_ROOT_PATH  /opt/poky/5.0.8/environment-setup-cortexa7t2hf-neon-vfpv4-poky-linux-gnueabi)

# adjust the default behavior of the FIND_XXX() commands:
# search programs in the host environment
set(CMAKE_FIND_ROOT_PATH_MODE_PROGRAM NEVER)

# search headers and libraries in the target environment
set(CMAKE_FIND_ROOT_PATH_MODE_LIBRARY ONLY)
set(CMAKE_FIND_ROOT_PATH_MODE_INCLUDE ONLY)