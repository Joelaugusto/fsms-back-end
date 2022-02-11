package joel.fsms.modules.address.service;

import joel.fsms.modules.address.domain.Address;
import joel.fsms.modules.address.domain.AddressMapper;
import joel.fsms.modules.address.domain.AddressRequest;
import joel.fsms.modules.address.persistence.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ADDRESS NOT FOUND"));
    }

    @Override
    public Address save(AddressRequest addressRequest) {
        return addressRepository.save(AddressMapper.INSTANCE.toEntity(addressRequest));
    }

    @Override
    public Address update(Long id, AddressRequest addressRequest) {
        Address address = findById(id);
        AddressMapper.INSTANCE.copyProperties(addressRequest, address);
        return addressRepository.save(address);
    }

    @Override
    public void delete(Long id) {
        if(addressRepository.existsById(id)){
           delete(id);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ADDRESS NOT FOUND");
        }
    }

    @Override
    public Page<Address> findAll(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }
}
