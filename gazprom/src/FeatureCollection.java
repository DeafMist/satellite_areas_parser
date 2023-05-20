import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureCollection {
    private List<List<Sputnik>> features;

    public List<List<Sputnik>> getFeatures() {
        return features;
    }
    public void setFeatures(List<List<Sputnik>> features) {
        this.features = features;
    }
}
