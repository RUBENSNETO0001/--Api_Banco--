package bancodigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bancodigital.model.Conta;
import bancodigital.model.ContaCorrente;
import bancodigital.respository.ContaRespository;

@Service
public class ServicoConta {

    @Autowired
    private ContaRespository contaRepository;

    public Conta criarNovaConta(String titular, String numero, double saldo) {

        Conta novaConta = new ContaCorrente(titular, numero, saldo);
        try {
            if (contaRepository.findByNumeroConta(numero).isEmpty() && !titular.isEmpty()) {
                     return contaRepository.save(novaConta);
                
            }
            return null;

        } catch (Exception e) {
            System.out.println("Deu error em salva: "+ e.getMessage());
            return null;
        }
    }

    public Conta buscarPorId(Long id) {
        return contaRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta com ID " + id + " não encontrada"));
    }

    public Conta buscarPorNumero(String numero) {
        return contaRepository.findByNumeroConta(numero).orElseThrow(() -> new RuntimeException("Erro: Conta " + numero + " não existe no sistema!"));
    }
    public boolean transferir(String origem, String destino, double valor){
        // O var é uma palavra-chave introduzida no Java 10 para facilitar a escrita de código, permitindo a Inferência de Tipo de Variável Local.
        var contaOrigem = contaRepository.findByNumeroConta(origem);
        var contaDestino = contaRepository.findByNumeroConta(destino);

        if (contaOrigem.isPresent() && contaDestino.isPresent() && valor > 0) {
            var origemConta = contaOrigem.get();
            var destinoConta = contaDestino.get();
            if (origemConta.getSaldo() >= valor) {
                origemConta.setSaldo(origemConta.getSaldo() - valor);
                destinoConta.setSaldo(destinoConta.getSaldo() + valor);
                contaRepository.save(origemConta);
                contaRepository.save(destinoConta);
            } else {
                System.out.println("Saldo insuficiente para transferência.");
                return false;
            }
            return true;
        }
        return false;
    }; 
}
