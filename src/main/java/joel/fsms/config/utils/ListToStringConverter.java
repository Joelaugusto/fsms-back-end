package joel.fsms.config.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class ListToStringConverter implements AttributeConverter<List<String>, String> {


    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if(attribute == null || attribute.isEmpty()) return "";
        StringBuilder s = new StringBuilder();
        for (String a: attribute) {
            s.append(",").append(a);
        }
        return s.substring(1);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if(dbData == null || dbData.isEmpty()) return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(dbData.split(",")));
    }
}
