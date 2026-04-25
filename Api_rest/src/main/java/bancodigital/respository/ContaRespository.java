package bancodigital.respository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bancodigital.model.Conta;

public interface ContaRespository extends JpaRepository<Conta, Long> {
    // Adicione esta linha:
    Optional<Conta> findByNumeroConta(String numeroConta);
}
