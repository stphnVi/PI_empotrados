from ctypes import *
so_file = "/usr/lib/libhouse.so.0"
#so_file = "/house/lib/libhouse.so"
house_lib = CDLL(so_file)

def setup_house():
    house_lib.pin_setup()   
    print("Pin setup done")

def receive_info(message):
    #pos 0 > Patio
    #pos 1 > Cuarto C1
    #pos 2 > Cuarto C2
    #pos 3 > Principal
    
    test = "[0,0,1,0]"
    
    message = message.strip()  # elimina espacios, saltos de línea, etc.
    print(f"Mensaje recibido limpio: '{message}'")
    

    
    if message == 'CheckPuerta':
        doors = []
        try:
            doors.append(house_lib.get_door_state(4))
            doors.append(house_lib.get_door_state(1))
            doors.append(house_lib.get_door_state(2))
            doors.append(house_lib.get_door_state(3))
            print(str(doors).replace(" ", ""))
        except Exception as error:
            # handle the exception
            print("An exception occurred:", error)
        return str(doors).replace(" ", "")
    elif message == "TodasOFF":
        res = house_lib.set_light_state(6, 0)
        return "0"
    elif message == "TodasON":
        res = house_lib.set_light_state(6, 1)
        return "0"
    elif message == "func: LuzComeLED2_OFF":
        house_lib.set_light_state(4, 0)
        return "0"
    elif message == "func: LuzComeLED2_ON":
        house_lib.set_light_state(4, 1)
        return "0"
    elif message == "func: LuzSalaLED2_OFF":
        house_lib.set_light_state(5, 0)
        return "0"
    elif message == "func: LuzSalaLED2_ON":
        house_lib.set_light_state(5, 1) 
        return "0"
    elif message == "func: luzCocinaLED2_OFF":
        house_lib.set_light_state(3, 0)
        return "0"
    elif message == "func: luzCocinaLED2_ON":
        house_lib.set_light_state(3, 1)
        return "0"
    elif message == "func: LuzC1LED2_OFF":
        house_lib.set_light_state(1, 0)
        return "0"
    elif message == "func: LuzC1LED2_ON":
        house_lib.set_light_state(1, 1)
        return "0"
    elif message == "func: LuzC2LED3_OFF":
        house_lib.set_light_state(2, 0)
        return "0"
    elif message == "func: LuzC2LED3_ON":
        house_lib.set_light_state(2, 1)
        return "0"
    else:
        return "1"