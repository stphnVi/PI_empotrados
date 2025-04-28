import json
import os
from datetime import datetime
import hashlib

def hash_data(data):
    """Hash the given data using SHA-256."""
    return hashlib.sha256(data.encode('utf-8')).hexdigest()

class UserDatabase:
    def __init__(self, filename="/home/root/PI_empotrados/users.txt"):
        self.filename = filename
        self.ensure_file_exists()

    def ensure_file_exists(self):
        if not os.path.exists(self.filename):
            with open(self.filename, "w") as f:
                f.write("# User Database - Created on {}\n".format(datetime.now()))

    def save_user(self, user_data):
        try:
            user_id = str(sum(1 for line in open(self.filename) if line.strip() and not line.startswith("#")))
            
            #Hash all sensitive user information
            hashed_user_data = {
                "userId": user_id,
                "username": hash_data(user_data["username"]),
                "password": hash_data(user_data["password"])}
            
            with open(self.filename, "a") as f:
                f.write(json.dumps(hashed_user_data, ensure_ascii=False) + "\n")
            return True, user_id
        except Exception as e:
            print(f"Error saving user: {e}")
            return False, None

    def verify_credentials(self, username, password, email=None):
        try:
            with open(self.filename, "r") as f:
                for line in f:
                    if line.strip() and not line.startswith("#"):
                        try:
                            user_data = json.loads(line.strip())
                            # Hash the input credentials
                            hashed_username = hash_data(username) if username else None
                            hashed_password = hash_data(password)

                            # Check for username/password OR email/password match
                            if (user_data["username"] == hashed_username and user_data["password"] == hashed_password):
                                return True, user_data["userId"], user_data
                        except json.JSONDecodeError:
                            continue
            return False, None, None
        except FileNotFoundError:
            return False, None, None
        
       
    def change_password(self, user_id, new_password):
        try:
            updated_lines = []  
            user_found = False  

            with open(self.filename, "r") as f:
                for line in f:
                    if line.strip() and not line.startswith("#"):
                        try:
                            user_data = json.loads(line.strip())
                            if user_data.get("userId") == user_id:
                                # User found, update the password
                                user_data["password"] = hash_data(new_password)
                                user_found = True
                            updated_lines.append(json.dumps(user_data) + "\n")
                        except json.JSONDecodeError:
                            # Skip invalid JSON lines
                            continue

            if not user_found:
                return False, None

            # Write the updated data back to the file
            with open(self.filename, "w") as f:
                f.writelines(updated_lines)

            return True,  user_id

        except FileNotFoundError:
            return False,  None
