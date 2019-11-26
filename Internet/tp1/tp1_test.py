import pytest
from tp1 import replace_quote, add_to_result

def test_replace_quote_without_quote():
	assert replace_quote('test,DS,editeur') == 'test,DS,editeur'

def test_replace_quote_with_one_quote():
	assert replace_quote('"test, test",DS,editeur') == 'test; test,DS,editeur'

def test_replace_quote_with_multiple_quote():
	assert replace_quote('"test, test",DS,"editeur, test"') == 'test; test,DS,editeur; test'

def test_add_to_result_new():
	result = {}
	add_to_result(result, 'DS', '1')
	assert result['DS'] == 1.

def test_add_to_result_already_exist():
	result = {'DS': 1.}
	add_to_result(result, 'DS', '0.5')
	assert result['DS'] == 1.5