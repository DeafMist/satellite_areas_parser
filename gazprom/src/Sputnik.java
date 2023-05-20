import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

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

    private List<Poly> polygons = new ArrayList<>();

    public Geometry(List<Poly> polygons) {
        this.polygons = polygons;
    }

    public Geometry() {
    }

    public List<Poly> getPolygons() {
        return polygons;
    }
    @JsonProperty("coordinates")
    public void setPolygons(List<List<List<String>>> polygons) {
        for(List<String> list: polygons.get(0)){
        this.polygons.add(new Poly(list.get(0),list.get(1)));
    }
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "polygons=" + polygons +
                '}';
    }
}
class Poly{
   private String polyX; private String polyY;

    public Poly(String polyX, String polyY) {
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
                "name='" + name + '\'' +", description='"+description+'\''+
                '}';
    }
}
