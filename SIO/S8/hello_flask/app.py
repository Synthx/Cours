from flask import Flask
from flask import render_template
import csv

app = Flask(__name__)

@app.route('/videogames')
def videogames():
	file = open('static/video-games.csv', 'r')
	data = list(csv.reader(file))
	header = data[0]
	games = data[1:]
	return render_template('videogames.html', games=games, header=header)

@app.route('/peoples')
def people():
	peoples = ['Tanou', 'Chouquette', 'Baptou', 'Paco', 'Hugro', 'Ananas', 'Manu', 'Racla']
	return render_template('peoples.html', peoples=peoples)

@app.route('/hello/<name>')
def index(name=None):
	return render_template('hello.html', name=name)

if __name__ == '__main__':
	app.run(debug=True)