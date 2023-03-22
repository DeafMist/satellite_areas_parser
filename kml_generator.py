from typing import List

from lxml import etree
from pykml.factory import GX_ElementMaker as GX
from pykml.factory import KML_ElementMaker as KML


def kml_generator(name: str, values: List[str], coordinates: List[str]):
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

    polygons_number = len(values)

    for i in range(1, polygons_number + 1):
        doc.Document.Folder.append(
            KML.Placemark(
                KML.name(i),
                KML.styleUrl('#stylename'),
                KML.ExtendedData(
                    KML.Data(
                        KML.value(values[i - 1]),
                        name='EIRP'
                    )
                ),
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
        )

    with open(name + '.kml', 'wb') as f:
        f.write(etree.tostring(doc, pretty_print=True))
