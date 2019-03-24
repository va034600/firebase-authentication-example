import firebase_admin
from firebase_admin import credentials, auth

cred = credentials.Certificate('serviceAccountKey.json')
default_app = firebase_admin.initialize_app(cred)

id_token = "XXX"
decoded_token = auth.verify_id_token(id_token)
uid = decoded_token['uid']
print(decoded_token)