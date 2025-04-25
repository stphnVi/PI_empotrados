from ctypes import *
so_file = "/usr/lib/libhouse.so.0"
#so_file = "/house/lib/libhouse.so"
house_lib = CDLL(so_file)

def receive_info(message):
    print(message)
    
    if message == "func: LuzSalaLED2_ON":
        house_lib.set_light_state(5, 1)
        return "test"

    
    return "1"