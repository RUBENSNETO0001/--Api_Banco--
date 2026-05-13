package bancodigital.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bancodigital.model.Conta;
import bancodigital.model.ContaCorrente;
import bancodigital.respository.ContaRespository;

@Service
public class ServicoConta {

    @Autowired
    private ContaRespository contaRepository;

    // criar nova conta
    public Conta criarNovaConta(String titular, String numero, double saldo) {

        Conta novaConta = new ContaCorrente(titular, numero, saldo);
        try {
            if (!numero.isEmpty() && contaRepository.findByNumeroConta(numero).isEmpty() && !titular.isEmpty()) {
                return contaRepository.save(novaConta);
            }
            return null;

        } catch (Exception e) {
            System.out.println("Deu error em salva: " + e.getMessage());
            return null;
        }
    }

    // buscar por id
    public Conta buscarPorId(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta com ID " + id + " não encontrada"));
    }

    // buscar por numero
    public Conta buscarPorNumero(String numero) {
        return contaRepository.findByNumeroConta(numero)
                .orElseThrow(() -> new RuntimeException("Erro: Conta " + numero + " não existe no sistema!"));
    }

    // transferir
    @Transactional
    public boolean transferir(String origem, String destino, double valor) {
        var optOrigem = contaRepository.findByNumeroConta(origem);
        var optDestino = contaRepository.findByNumeroConta(destino);

        if (optOrigem.isEmpty() || optDestino.isEmpty() || valor <= 0) {
            return false;
        }

        var contaO = optOrigem.get();
        var contaD = optDestino.get();

        if (contaO.getSaldo() < valor || !(contaD instanceof ContaCorrente) || valor <= 0) {
            try {
                throw new RuntimeException("Erro: Saldo insuficiente ou conta de destino inválida.");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        try {
            contaO.setSaldo(contaO.getSaldo() - valor);
            contaD.setSaldo(contaD.getSaldo() + valor);

            contaRepository.save(contaO);
            contaRepository.save(contaD);

            return true;
        } catch (Exception e) {
            System.out.println("Erro ao salvar no banco de dados: " + e.getMessage());
            return false;
        }
    }

    // sacar
    public double sacar(String numero, double valor) {
        Conta conta = this.buscarPorNumero(numero);
        if (conta != null && conta.getSaldo() >= valor && valor > 0) {
            double novoSaldo = conta.getSaldo() - valor;
            conta.setSaldo(novoSaldo);
            contaRepository.save(conta);
            return valor;
        } else {
            System.out.println("Valor de saque inválido.");
            return 0;
        }
    }

    public double depositar(String numero, double valor) {
        Conta conta = this.buscarPorNumero(numero);
        if (conta != null && valor > 0) {
            double novoSaldo = conta.getSaldo() + valor;
            conta.setSaldo(novoSaldo);
            contaRepository.save(conta);
            return valor;
        } else {
            System.out.println("Valor de saque inválido.");
            return 0;
        }
    }
}
