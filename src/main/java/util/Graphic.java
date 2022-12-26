package util;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "cfg")
@XmlAccessorType(XmlAccessType.FIELD)
public class Graphic {

    @XmlElementWrapper(name = "points")
    @XmlElement(name = "value")
    private List<Integer> points;

    @XmlElement(name = "max_price")
    private int maxPrice;
}
