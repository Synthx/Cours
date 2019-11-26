# tp1.py
def replace_quote(line):
	if '"' in line:
		texts = line.split('"')[1::2]
		for text in texts:
			new_text = text.replace(',', ';')
			line = line.replace(text, new_text)
		line = line.replace('"', '')
	return line

def add_to_result(result, key, value):
	if key not in result:
		result[key] = float(value)
	else:
		result[key] += float(value)

def calculate_sell_by_platform(file_name):
	result = {}
	# open csv file
	with open(file_name, 'r') as file:
		lines = file.readlines()
		# skip first line
		for line in lines[1:]:
			line = replace_quote(line)
			info = line.split(',')
			# add value to result
			add_to_result(result, info[1], info[9])
	# print result
	for platform, sell in result.items():
		print(platform + ': ' + str(sell))

if __name__ == '__main__':
	calculate_sell_by_platform('./video-games.csv')