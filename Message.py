from ctypes import *
so_file = "/usr/lib/libhouse.so.0"
#so_file = "/house/lib/libhouse.so"
house_lib = CDLL(so_file)

def receive_info(message):
    #pos 0 > Patio
    #pos 1 > Cuarto C1
    #pos 2 > Cuarto C2
    #pos 3 > Principal
    
    test = "[0,0,1,0]"
    
    message = message.strip()  # elimina espacios, saltos de línea, etc.
    print(f"Mensaje recibido limpio: '{message}'")
    
    if message == 'CheckPuerta':
        return test
    return "1"