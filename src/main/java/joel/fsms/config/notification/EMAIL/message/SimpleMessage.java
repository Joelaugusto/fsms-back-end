package joel.fsms.config.notification.EMAIL.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor
@Data
public class SimpleMessage<T extends SimpleMessage<T>> {
    private String subject = "";
    private String greeting;
    private String salutation;
    private List<String> introLines = new ArrayList<>();
    private List<String> outroLines = new ArrayList<>();
    private String actionText;
    private String actionUrl;

    @SuppressWarnings("unchecked")
    public T action(String title, String url) {
        actionText = title;
        actionUrl = url;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T line(String line) {
        if (actionText == null) {
            introLines.addAll(formatLine(line));
        } else {
            outroLines.addAll(formatLine(line));
        }
        return (T) this;
    }

    private List<String> formatLine(String line) {
        return Arrays.asList(line.trim().split("\r\n|\r|\n"));
    }
}
