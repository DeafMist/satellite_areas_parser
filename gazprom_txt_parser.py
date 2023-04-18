from kml_generator import kml_generator

map = {
    'sputnik13867': 'Yamal-601',
    'sputnik13868': 'Yamal-402',
    'sputnik13869': 'Yamal-401',
    'sputnik13871': 'Yamal-202',
    'sputnik13870': 'Yamal-300K'
}


def get_gazprom_data():
    with open('output.txt', 'r', errors='ignore') as f:
        file_lines = f.readlines()

    names = list()
    values = dict()
    coordinates = dict()

    for line in file_lines:
        name = line[line.find('name') + 6: line.find('description') - 3]

        if name.find(':') == -1:
            continue

        name = get_satellite_name(name)

        if name not in names:
            names.append(name)
            values[name] = list()
            coordinates[name] = list()

        eirp = get_polygon_eirp(line[line.find('description') + 13:])
        values[name].append(eirp)

        polygon_coords = get_polygon_coordinates(line[: line.find('property')])
        coordinates[name].append(polygon_coords.rstrip())
    return names, values, coordinates


def get_polygon_coordinates(polygon):
    polygon_coords = ''
    while True:
        polygon_coords += polygon[polygon.find('polyX') + 7: polygon.find('polyY') - 3] + ',' + \
                          polygon[polygon.find('polyY') + 7: polygon.find('\'}')] + ' '
        polygon = polygon[polygon.find('\'}') + 2:]
        if polygon.find('Poly{') == -1:
            break
    return polygon_coords


def get_polygon_eirp(value):
    eirp = ''
    for digit in value:
        if digit.isdigit():
            eirp += digit
        else:
            break
    return eirp


def get_satellite_name(name):
    real_name = map[name[0: name.find(':')]] + ' '
    name = real_name + name[name.find(':') + 1:]
    name = name.replace(' ', '_')
    return name


def get_gazprom_kmls():
    names, values, coordinates = get_gazprom_data()

    for name in names:
        kml_generator(name, coordinates[name], values=values[name])


get_gazprom_kmls()
