import requests

from kml_generator import kml_generator


def ses_parser():
    satel_numbers = [309, 311, 312, 322, 324, 325, 326, 329, 330, 332, 337, 339, 343, 344, 345, 347, 351, 352, 353, 355,
                     356, 357, 361, 363, 365, 366, 369, 371, 372, 374, 408, 409, 410, 411]
    kml_data = []

    for i in satel_numbers:
        response = requests.get(f'https://www.ses.com/satellite/{i}/footprints?_format=hal_json')
        satel = response.json()
        for footprint in satel['footprints']:
            temp_data = {'name': (satel['title'] + '_' + footprint['title']).replace(' ', '_')}

            temp_list = []
            for marker in footprint['geo_json']['signal_marker']:
                temp_list.append((str(marker['coordinates'][0]), str(marker['coordinates'][1]), str(marker['value'])))
            temp_data['markers'] = temp_list

            temp_data['multypolygon'] = []
            for k in footprint['geo_json']['features'][0]['geometry']['coordinates']:
                poly = ''
                for l in k[0]:
                    poly += ' ' + str(l[0]) + ',' + str(l[1])
                temp_data['multypolygon'].append(poly)

            kml_data.append(temp_data)
    return kml_data


def get_ses_kmls():
    kml_data = ses_parser()
    for satellite in kml_data:
        kml_generator(satellite['name'], satellite['multypolygon'], points=satellite['markers'])


get_ses_kmls()
