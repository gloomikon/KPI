import regex as re
result = re.findall(r'\b\S+ \S+', 'AV Analytics Vidhya AV', overlapped=True)
print(result)