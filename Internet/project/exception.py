class Error(Exception):
	pass


class ParseIdentifierError(Error):
	
	def __init__(self, expression, message):
		self.expression = expression
		self.message = message
