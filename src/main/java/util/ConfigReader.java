package util;

import lombok.SneakyThrows;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class ConfigReader {

    @SneakyThrows
    public static Graphic getGraphic(String name) {
        String cfgPath = "src/main/resources/"+name+".xml";

        JAXBContext jaxbContext = JAXBContext.newInstance(Graphic.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        Graphic graphic = (Graphic) unmarshaller.unmarshal(new File(cfgPath));
        return graphic;
    }
}
