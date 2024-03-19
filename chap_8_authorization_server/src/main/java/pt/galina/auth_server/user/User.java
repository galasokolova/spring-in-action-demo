package pt.galina.auth_server.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name = "users")
@RequiredArgsConstructor
@NoArgsConstructor(force=true, access=AccessLevel.PROTECTED)
public class User implements UserDetails {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  @Column(name = "username")
  private final String username;
  @Column(name = "password")
  private final String password;
  @Column(name = "role")
  private final String role;

  @Override
  @Transient
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority(role));
  }
  
  @Override
  @Transient
  public boolean isAccountNonExpired() {
    return true;
  }
  
  @Override
  @Transient
  public boolean isAccountNonLocked() {
    return true;
  }
  
  @Override
  @Transient
  public boolean isCredentialsNonExpired() {
    return true;
  }
  
  @Override
  @Transient
  public boolean isEnabled() {
    return true;
  }
  
}
