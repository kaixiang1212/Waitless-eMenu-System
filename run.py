from routes.app import app
from routes.api import api


if __name__ == '__main__':
    app.run(debug=True, port='8080')

