import requests

satel_numbers = [309, 311, 312, 322, 324, 325, 326, 329, 330, 332, 337, 339, 343, 344, 345, 347, 351, 352, 353, 355, 356, 357, 361, 363, 365, 366, 369, 371, 372, 374, 408, 409, 410, 411];
kml_data = []

for i in satel_numbers:    
    response = requests.get(f'https://www.ses.com/satellite/{i}/footprints?_format=hal_json')
    satel = response.json()
    for j in range(len(satel['footprints'])):
        temp_data = {'name': satel['footprints'][j]['name']}
        temp_data['markers'] = satel['footprints'][j]['geo_json']['signal_marker']
        temp_data['multypolygon'] = satel['footprints'][j]['geo_json']['features'][0]['geometry']['coordinates']
        kml_data.append(temp_data)
print(kml_data[0])