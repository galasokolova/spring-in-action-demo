package pt.galina.cassandrareactivepersistence.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@UserDefinedType("user_udt")
@NoArgsConstructor
@AllArgsConstructor
public class UserUDT {
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;
}
