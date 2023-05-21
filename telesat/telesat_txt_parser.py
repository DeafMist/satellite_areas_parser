from kml_generator import kml_generator


def get_telesat_data():
    with open('output.txt', 'r', errors='ignore') as f:
        file_lines = f.readlines()

    names = list()
    values = dict()
    coordinates = dict()

    name = ''
    for line in file_lines:
        if line.find("Feature") == -1:
            name = line.replace(" ", "_").replace('\n', '')
            names.append(name)
            values[name] = list()
            coordinates[name] = list()
            continue

        eirp = get_polygon_eirp(line[line.find('name=') + 6:line.rfind('\'}}')])
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
    if value.find('dBW') == -1:
        return eirp

    value = value[:value.find('dBW')]

    for digit in value:
        if digit.isdigit():
            eirp += digit
        if digit == '.':
            eirp += digit
    return eirp


def get_telesat_kmls():
    names, values, coordinates = get_telesat_data()

    for name in names:
        kml_generator(name, coordinates[name], values=values[name])


get_telesat_kmls()
