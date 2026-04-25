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

        return contaRepository.save(novaConta);
    }

    public Conta buscarPorId(Long id) {
        return contaRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta com ID " + id + " não encontrada"));
    }

    public Conta buscarPorNumero(String numero) {
        return contaRepository.findByNumeroConta(numero).orElseThrow(() -> new RuntimeException("Erro: Conta " + numero + " não existe no sistema!"));
    }
}
