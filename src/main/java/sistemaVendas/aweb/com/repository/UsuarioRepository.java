package sistemaVendas.aweb.com.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sistemaVendas.aweb.com.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailIgnoreCase(String email);
}
