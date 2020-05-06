import os
import requests
import json

test_rate = 200
test_duration = 200
validation_result = {}
with open(f'./payload.txt') as payload_file:
    payload = int(payload_file.read())
expected_result = test_rate * test_duration * payload

response = requests.get(f'http://localhost:8080/count')
if int(response.text) != expected_result:
    print('FAILED')
else:
    print('SUCCEEED')

