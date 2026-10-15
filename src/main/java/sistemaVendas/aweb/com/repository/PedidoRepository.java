package sistemaVendas.aweb.com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import sistemaVendas.aweb.com.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Carrega os dados utilizados na listagem antes de fechar a sessão JPA.
    @Override
    @EntityGraph(attributePaths = {"cliente", "itens", "itens.produto"})
    List<Pedido> findAll();

    // Também carrega os itens ao abrir um pedido para edição.
    @Override
    @EntityGraph(attributePaths = {"cliente", "itens", "itens.produto"})
    Optional<Pedido> findById(Long id);
}