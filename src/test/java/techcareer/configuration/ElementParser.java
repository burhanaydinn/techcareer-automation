package techcareer.configuration;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ElementParser {
    protected static final Logger logger = LogManager.getLogger(ElementParser.class);

    private static ElementParser instance;
    private Map<String, Object> elementsMap;


    private ElementParser() {
        initElementMap(getFileList());
    }

    public static ElementParser getInstance() {
        if (instance == null) {
            instance = new ElementParser();
        }
        return instance;
    }

    public void initElementMap(File[] fileList) {
        Type elementType = new TypeToken<List<ElementInfo>>() {
        }.getType();
        Gson gson = new Gson();
        List<ElementInfo> elementInfoList;
        elementsMap = new ConcurrentHashMap<>();
        for (File file : fileList) {
            try {
                elementInfoList = gson.fromJson(new FileReader(file), elementType);
                elementInfoList.parallelStream().forEach(elementInfo -> elementsMap.put(elementInfo.getKeyword(), elementInfo));
            } catch (FileNotFoundException e) {
                logger.warn(e.getMessage());
            }
        }
    }

    public File[] getFileList() {
        String elementsPath = Configuration.getInstance().getElementsPath();
        try {
            return new File(Objects.requireNonNull(
                            this.getClass().getClassLoader().getResource(elementsPath))
                    .getFile())
                    .listFiles(pathname -> !pathname.isDirectory() && pathname.getName().endsWith(".json"));
        } catch (Exception e) {
            String msg = "Belirtilen dosya bulunamadı. Dosya yolu : " + elementsPath;
            logger.error(msg);
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public By getByWithKeyword(String keyword) {
        try {
            ElementInfo elements = (ElementInfo) elementsMap.get(keyword);
            return getBy(elements);
        } catch (NullPointerException e) {
            logger.error(String.format("%s ile kayıtlı bir element bulunmamaktadır. Json dosyalarınızı kontrol ediniz.", keyword));
            throw e;
        }
    }

    public String getLocatorValue(String keyword) {
        try {
            ElementInfo elements = (ElementInfo) elementsMap.get(keyword);
            if (elements.getLocatorType().equalsIgnoreCase("css") || elements.getLocatorType().equalsIgnoreCase("xpath")) {
                return elements.getLocatorValue();
            } else {
                Assertions.fail("Shadow root sadece css selector ve xpath ile çalışmaktadır. Lütfen düzenleme yapınız.");
            }
        } catch (NullPointerException e) {
            logger.error(String.format("%s ile kayıtlı bir element bulunmamaktadır. Json dosyalarınızı kontrol ediniz.", keyword));
            throw e;
        }
        return null;
    }

    private By getBy(ElementInfo element) {
        String locatorValue = element.getLocatorValue();
        String locatorType = element.getLocatorType();
        logger.info("Locator olusturuluyor.LocatorValue : {} LocatorType : {} ", locatorValue, locatorType);
        switch (locatorType) {
            case "id":
                return By.id(locatorValue);
            case "css":
                return By.cssSelector(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            case "class":
                return By.className(locatorValue);
            case "name":
                return By.name(locatorValue);
            default:
                String msg = "Desteklenen locator tipi girilmediği için test durduruldu.Desteklenen locator tipleri: id,css,xpath,class,name";
                logger.error(msg);
                throw new NullPointerException(msg);
        }
    }


    @Getter
    public static class ElementInfo {
        protected String keyword;
        protected String locatorValue;
        protected String locatorType;

        @Override
        public String toString() {
            return "Elements[" + "keyword=" + keyword + ",locatorType=" + locatorType + ",locatorValue=" + locatorValue + "]";
        }
    }
}
