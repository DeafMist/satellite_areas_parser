import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sputnik {
    private List<Feature>features;

    public Sputnik() {
    }

    public Sputnik(List<Feature> features) {
        this.features = features;
    }
    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "Sputnik{" +
                "features=" + features +
                '}';
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
class Feature{
    private Geometry geometry;
    @JsonProperty("properties")
    private Property property;

    public Feature() {
    }

    public Feature(Geometry geometry) {
        this.geometry = geometry;
    }

    public Property getProperty() {
        return property;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void setGeometry(Geometry geometry) {
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
class Geometry{

    private HashMap<String,String> polygons;

    public Geometry(HashMap<String, String> polygons) {
        this.polygons = polygons;
    }

    public Geometry() {
    }

    public HashMap<String, String> getPolygons() {
        return polygons;
    }
    @JsonProperty("coordinates")
    public void setPolygons(List<List<List<String>>> polygons) {
        this.polygons = new HashMap<>();
        for(List<String> list: polygons.get(0)){
            this.polygons.put(list.get(0), list.get(1) );
        }

    }

    @Override
    public String toString() {
        return "Geometry{" +
                "polygons=" + polygons +
                '}';
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
class Property{
    private String name;private String description;

    public Property(String name,String description) {
        this.name = name;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Property() {
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +"description="+description+'\''+
                '}';
    }
}
