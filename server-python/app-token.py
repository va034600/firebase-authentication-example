from flask import Flask, g
from flask_httpauth import HTTPTokenAuth
from firebase_admin import credentials, initialize_app, auth

cred = credentials.Certificate('serviceAccountKey.json')
default_app = initialize_app(cred)

app = Flask(__name__)
token_auth = HTTPTokenAuth(scheme='Bearer')


@token_auth.verify_token
def verify_token(token):
    try:
        decoded_token = auth.verify_id_token(token)
        g.current_user = decoded_token
        return True
    except ValueError:
        return False


@app.route('/')
@token_auth.login_required
def index():
    return "Hello, %s!" % g.current_user



if __name__ == '__main__':
    app.run()
