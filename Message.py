
from ctypes import *
from UserDB import *
import json
import subprocess

# Initialize the user database
user_db = UserDatabase()

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
    
    # Check if message is in JSON format 
    if message.startswith('{') and message.endswith('}'):
        try:
            data = json.loads(message)
            # Handle user registration
            if data.get('func') == 'register':
                result, user_id = user_db.save_user({
                    'username': data.get('username'),
                    'password': data.get('password')
                })
                return str(result)
            # Handle user login (already exists in your MainActivity)
            elif data.get('func') == 'login':
                try:
                    username=data.get('username')
                    password=data.get('password')
                   
                    # Proceed with verifying the credentials using the hashed values
                    result, user_id, user_data = user_db.verify_credentials(username,password)
                    return "1" if result else "0"
                except Exception as e:
                    print(f"Error during login: {e}")
                    return "0"
            elif data.get('func')=='capture_image':
                    #Comando que captura una imagen con la webcam y la establece en ese directorio
                    cmd = ["fswebcam", "/home/root/PI_empotrados/images/image.jpg"]
                    subprocess.run(cmd, check=True)


        except json.JSONDecodeError as e:
            print(f"Error parsing JSON: {e}")
            return "0"

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
    # Check if the message format is func: login, userEmail: X, password: Y
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
