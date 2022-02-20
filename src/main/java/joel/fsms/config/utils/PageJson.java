package joel.fsms.config.utils;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class PageJson <T>{

    private final Page<T> page;

    private PageJson(Page<T> page) {
        this.page = page;
        this.withMeta("pagination", getPagination());
    }

    @Getter
    private final Map<String, Object> meta = new HashMap<>();

    public List<T> getData() {
        return page.getContent();
    }

    private Map<String, Object> getPagination() {
        HashMap<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", page.getNumber());
        pagination.put("totalPage", page.getTotalPages());
        pagination.put("lastPage", page.getTotalPages() - 1);
        pagination.put("isLast", page.isLast());
        pagination.put("perPage", page.getSize());
        pagination.put("totalItems", page.getTotalElements());
        return pagination;
    }

    public static <T> PageJson<T> of(Page<T> page){
        return new PageJson<>(page);
    }

    public PageJson<T> withMeta(String key, Object value) {
        meta.put(key, value);
        return this;
    }

    public PageJson<T> forEach(Consumer<? super T> consumer) {
        this.getData().forEach(consumer);
        return this;
    }

    public ResponseEntity<PageJson<T>> toResponseEntity() {
        return ResponseEntity.ok(this);
    }

}
