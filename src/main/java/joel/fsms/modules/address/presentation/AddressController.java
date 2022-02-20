package joel.fsms.modules.address.presentation;

import io.swagger.annotations.Api;
import joel.fsms.modules.address.domain.AddressMapper;
import joel.fsms.modules.address.domain.AddressRequest;
import joel.fsms.modules.address.domain.AddressResponse;
import joel.fsms.modules.address.service.AddressServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@AllArgsConstructor
@Api(tags = "Address Management")
public class AddressController {

    private AddressServiceImpl addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(AddressMapper.INSTANCE.toResponse(addressService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AddressResponse> save(AddressRequest addressRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(AddressMapper.INSTANCE.toResponse(addressService.save(addressRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> update(@PathVariable Long id, AddressRequest addressRequest) {
        return ResponseEntity.ok(AddressMapper.INSTANCE.toResponse(addressService.update(id, addressRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AddressResponse> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<AddressResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(AddressMapper.INSTANCE.toResponse(addressService.findAll(pageable)));
    }
}
