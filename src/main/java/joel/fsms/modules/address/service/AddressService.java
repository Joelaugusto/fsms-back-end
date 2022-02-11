package joel.fsms.modules.address.service;

import joel.fsms.modules.address.domain.Address;
import joel.fsms.modules.address.domain.AddressRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {

    Address findById(Long id);
    Address save(AddressRequest addressRequest);
    Address update(Long id ,AddressRequest addressRequest);
    void delete(Long id);
    Page<Address> findAll(Pageable pageable);
}
