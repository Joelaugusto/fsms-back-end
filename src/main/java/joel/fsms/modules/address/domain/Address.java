package joel.fsms.modules.address.domain;

import joel.fsms.modules.users.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "address")
@Getter
@Setter
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "locality")
    private String locality;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @OneToMany(mappedBy = "address", orphanRemoval = true)
    @ToString.Exclude
    private List<User> user = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "province")
    private Province province;

}
