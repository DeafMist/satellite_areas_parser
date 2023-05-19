from typing import List, Tuple

from lxml import etree
from pykml.factory import GX_ElementMaker as GX
from pykml.factory import KML_ElementMaker as KML


def kml_generator(name: str, coordinates: List[str], values: List[str] = None, points: List[Tuple[str, str, str]] = None):
    if points is None:
        points = list()
    if values is None:
        values = list()

    doc = KML.kml(
        KML.Document(
            KML.name(name + '.kml'),
            GX.CascadingStyle(
                KML.Style(
                    KML.LineStyle(
                        KML.width('1.5')
                    ),
                    KML.PolyStyle(
                        KML.color('402f2fd3')
                    )
                ),
                id='stylename1'
            ),
            GX.CascadingStyle(
                KML.Style(
                    KML.PolyStyle(
                        KML.color('402f2fd3')
                    )
                ),
                id='stylename2'
            ),
            KML.StyleMap(
                KML.Pair(
                    KML.key('normal'),
                    KML.styleUrl('#stylename2')
                ),
                KML.Pair(
                    KML.key('highlight'),
                    KML.styleUrl('#stylename1')
                ),
                id='stylename'
            ),
            KML.Folder(
                KML.name(name)
            )
        )
    )

    polygons_number = len(coordinates)
    for i in range(1, polygons_number + 1):
        placemark = KML.Placemark(
            KML.name(i),
            KML.styleUrl('#stylename'),
            KML.Polygon(
                KML.tesselate('1'),
                KML.extrude('1'),
                KML.altitudeMode('clampedToGround'),
                KML.outerBoundaryIs(
                    KML.LinearRing(
                        KML.coordinates(
                            coordinates[i - 1]
                        )
                    )
                )
            )
        )

        if len(values) != 0:
            placemark.append(
                KML.ExtendedData(
                    KML.Data(
                        KML.value(values[i - 1]),
                        name='EIRP'
                    )
                ),
            )

        doc.Document.Folder.append(placemark)

    for lon, lat, eirp in points:
        doc.Document.Folder.append(
            KML.Placemark(
                KML.ExtendedData(
                    KML.Data(
                        KML.value(eirp),
                        name='EIRP'
                    )
                ),
                KML.Point(
                    KML.coordinates(lon + ', ' + lat)
                )
            )
        )

    with open(name + '.kml', 'wb') as f:
        f.write(etree.tostring(doc, pretty_print=True))
