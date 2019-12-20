import pytest
from tp1 import replace_quote, add_to_result, calculate_sell_by_platform

def test_replace_quote():
	assert replace_quote('test,DS,editeur') == 'test,DS,editeur'
	assert replace_quote('"test, test",DS,editeur') == 'test; test,DS,editeur'
	assert replace_quote('"test, test",DS,"editeur, test"') == 'test; test,DS,editeur; test'

def test_add_to_result():
	result = {}
	add_to_result(result, 'DS', '1')
	assert result['DS'] == 1.
	add_to_result(result, 'DS', '0.5')
	assert result['DS'] == 1.5
	add_to_result(result, 'PS4', '5')
	assert result['PS4'] == 5.

def test_calculate_sell_by_platform():
	lines = [
		'jeux 1,DS,2019,action,"editeur 1, editeur 2",0,0,0,0,2', 
		'jeux 2,DS,2018,action,editeur 2,0,0,0,0,3',
		'jeux 3,PS4,2018,action,editeur 1,0,0,0,0,8',
		'"jeux 4, remaster",PS3,2019,action,editeur 3,0,0,0,0,2',
		'"jeux 1, remake",DS,2019,action,"editeur 1, editeur 2",0,0,0,0,1'
	]
	assert calculate_sell_by_platform(lines) == {'DS': 6., 'PS3': 2., 'PS4': 8.}