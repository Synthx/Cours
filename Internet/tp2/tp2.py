import requests
import logbook
import sys
import re
import html
import base64

logbook.StreamHandler(sys.stdout, level=logbook.DEBUG).push_application()
log = logbook.Logger('TP2')

def main():
	url = 'http://www.polytech-lille.fr/annuaire.php'

	log.info('extracting data from %s...' % url)
	data = {'nom': '%'}
	r = requests.post(url, data)

	log.info('searching contacts')
	contacts = {}
	matches = re.findall(r'annuaire\.php\?a=([\w]+)\'>([^<]*)', r.text)

	log.info('%d contact found' % len(matches))
	for match in matches:
		identifier = base64.b64decode(match[0])
		identifier = identifier.decode('UTF-8').replace('***%***', '')
		contacts[int(identifier)] = html.unescape(match[1])

	print(contacts)

if __name__ == '__main__':
	main()