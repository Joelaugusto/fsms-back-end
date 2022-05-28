package joel.fsms.modules.configuration;

import joel.fsms.config.file.presentation.FileJson;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;

@Converter
public class ListFileJson implements AttributeConverter<List<FileJson>, String> {


    @Override
    public String convertToDatabaseColumn(List<FileJson> fileJsons) {

        StringBuilder stringBuilder = new StringBuilder();

        fileJsons.forEach(fileJson -> stringBuilder.append(fileJson.getName()).append(",").append(fileJson.getPath()).append(";"));

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    @Override
    public List<FileJson> convertToEntityAttribute(String s) {

        List<FileJson> list = new ArrayList<>();
        String[] files = s.split(";");

        for(String file: files){
            String [] attributes = file.split(",");
            FileJson fileJson = new FileJson();
            fileJson.setName(attributes[0]);
            fileJson.setPath(attributes[1]);
            list.add(fileJson);
        }
        return list;
    }
}
