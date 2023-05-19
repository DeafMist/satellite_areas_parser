import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SputnikForSummary {
    @JsonProperty("features")
    private List<FeatureForSummary> features;

    public SputnikForSummary() {
    }

    public SputnikForSummary(List<FeatureForSummary> featuresForSummary) {
        this.features = featuresForSummary;
    }
    public List<FeatureForSummary> getFeaturesForSummary() {
        return features;
    }

    public void setFeaturesForSummary(List<FeatureForSummary> featuresForSummary) {
        this.features = featuresForSummary;
    }

    @Override
    public String toString() {
        return "Sputnik{" +
                "features=" + features +
                '}';
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
class FeatureForSummary {
    @JsonProperty("geometry")
    private GeometryForSummary geometry;
    @JsonProperty("properties")
    private PropertyForSummary property;

    public FeatureForSummary() {
    }

    public FeatureForSummary(GeometryForSummary geometry) {
        this.geometry = geometry;
    }

    public PropertyForSummary getPropertyForSummary() {
        return property;
    }

    public GeometryForSummary getGeometryForSummary() {
        return geometry;
    }

    public void setPropertyForSummary(PropertyForSummary property) {
        this.property = property;
    }

    public void setGeometryForSummary(GeometryForSummary geometry) {
        this.geometry = geometry;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "geometry=" + geometry +
                ", property=" + property +
                '}';
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class GeometryForSummary{
    private List<PolyForSummary> polygons = new ArrayList<>();

    public GeometryForSummary (List<PolyForSummary> polygons) {
        this.polygons = polygons;
    }

    public GeometryForSummary() {
    }

    public List<PolyForSummary> getPolygonsForSummary() {
        return polygons;
    }
    @JsonProperty("coordinates")
    public void setPolygonsForSummary(List<List<List<String>>> polygons) {
        for(List<String> list: polygons.get(0)){
            if(list.size()>1){
                this.polygons.add(new PolyForSummary(list.get(0),list.get(1)));}
        }
    }

    @Override
    public String toString() {
        if(polygons.size()>0){
            return "Geometry{" +
                    "polygons=" + polygons +
                    '}';}
        else {
            return null;
        }

    }
}
class PolyForSummary {
    private String polyX; private String polyY;

    public PolyForSummary(String polyX, String polyY) {
        this.polyX = polyX;
        this.polyY = polyY;
    }

    public String getPolyX() {
        return polyX;
    }

    public void setPolyX(String polyX) {
        this.polyX = polyX;
    }

    public String getPolyY() {
        return polyY;
    }

    public void setPolyY(String polyY) {
        this.polyY = polyY;
    }

    @Override
    public String toString() {
        return "Poly{" +
                "polyX='" + polyX + '\'' +
                ", polyY='" + polyY + '\'' +
                '}';
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class PropertyForSummary {
    private String name;

    public PropertyForSummary(String name) {
        this.name = name;

    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public PropertyForSummary() {
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                '}';
    }
}