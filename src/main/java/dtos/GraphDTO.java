package dtos;

import java.io.Serializable;
import java.util.List;

public class GraphDTO  {

    private List<Float> data;
    private List<String> labels;

    public GraphDTO(List<Float> data, List<String> labels) {
        this.data = data;
        this.labels = labels;
    }


    public List<Float> getData() {
        return data;
    }

    public void setData(List<Float> data) {
        this.data = data;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
