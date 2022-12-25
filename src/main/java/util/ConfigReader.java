package util;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class ConfigReader {

    private static String path;

    @SneakyThrows
    public static Graphic getGraphic() {
        String cfgPath = "src/main/resources/"+path+".xml";

        JAXBContext jaxbContext = JAXBContext.newInstance(Graphic.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        Graphic graphic = (Graphic) unmarshaller.unmarshal(new File(cfgPath));
        return graphic;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        ConfigReader.path = path;
    }
}
