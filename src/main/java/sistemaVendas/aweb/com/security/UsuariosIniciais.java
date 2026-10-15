package sistemaVendas.aweb.com.security;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sistemaVendas.aweb.com.model.Usuario;
import sistemaVendas.aweb.com.model.UsuarioRole;
import sistemaVendas.aweb.com.repository.UsuarioRepository;
import java.util.Locale;

@Component
public class UsuariosIniciais implements ApplicationRunner {
    private final UsuarioRepository repositorio;
    private final PasswordEncoder encoder;
    private final Environment ambiente;
    public UsuariosIniciais(UsuarioRepository repositorio, PasswordEncoder encoder, Environment ambiente) {
        this.repositorio = repositorio; this.encoder = encoder; this.ambiente = ambiente;
    }
    @Override public void run(ApplicationArguments args) {
        criarSeNecessario("ADMIN", UsuarioRole.ADMIN);
        criarSeNecessario("PADRAO", UsuarioRole.PADRAO);
    }
    private void criarSeNecessario(String prefixo, UsuarioRole role) {
        String email = ambiente.getProperty(prefixo + "_EMAIL", "").trim().toLowerCase(Locale.ROOT);
        String senha = ambiente.getProperty(prefixo + "_PASSWORD", "");
        if (email.isBlank() || senha.isBlank()) return;
        if (senha.length() < 12) throw new IllegalStateException(prefixo + "_PASSWORD deve ter pelo menos 12 caracteres");
        if (repositorio.findByEmailIgnoreCase(email).isEmpty()) {
            repositorio.save(new Usuario(prefixo.equals("ADMIN") ? "Administrador" : "Usuário Padrão",
                email, encoder.encode(senha), role));
        }
    }
}
