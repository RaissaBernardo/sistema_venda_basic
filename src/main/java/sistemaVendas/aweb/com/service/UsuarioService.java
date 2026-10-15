package sistemaVendas.aweb.com.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sistemaVendas.aweb.com.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository repositorio;
    public UsuarioService(UsuarioRepository repositorio) { this.repositorio = repositorio; }
    @Override public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repositorio.findByEmailIgnoreCase(email.trim())
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
