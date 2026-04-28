package bancodigital.control;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bancodigital.model.Conta;
import bancodigital.model.ContaCorrente;
import bancodigital.service.ServicoConta;

@RestController
@RequestMapping("/contas")
public class ControlConta {

    @Autowired
    private ServicoConta servicoConta;

    @PostMapping()
    public Conta criarConta(@RequestBody ContaCorrente dadosRecebidos) {
        System.out.println("oi");
        return servicoConta.criarNovaConta(dadosRecebidos.getTitular(), dadosRecebidos.getNumeroConta(),
                dadosRecebidos.getSaldo());
    }

    @PostMapping("/buscar")
    public Conta buscarConta(@RequestBody ContaCorrente dados) {
        System.out.println("Buscando a conta número: " + dados.getNumeroConta());
        return servicoConta.buscarPorNumero(dados.getNumeroConta());
    }

    @PostMapping("/buscar-por-id")
    public Conta buscarId(@RequestBody ContaCorrente dados) {
        System.out.println("Procurando pelo ID: " + dados.getId());
        return servicoConta.buscarPorId(dados.getId());
    }

    @PostMapping("/transferir")
    public ResponseEntity<String> realizarTransfer(@RequestBody Map<String, Object> dados) {
        String origem = (String) dados.get("origem");
        String destino = (String) dados.get("destino");

        double valor = Double.parseDouble(dados.get("valor").toString());

        boolean sucesso = servicoConta.transferir(origem, destino, valor);

        if (sucesso) {
            return ResponseEntity.ok("Transferência realizada com sucesso!");
        }
        return ResponseEntity.badRequest()
                .body("Erro ao processar transferência: Verifique o saldo ou os números das contas.");
    }

    @PostMapping("/sacar")
    public ResponseEntity<String> realizarSaque(@RequestBody Map<String, Object> dados) {
        String numero = (String) dados.get("numero");
    double valor = Double.parseDouble(dados.get("valor").toString());

    double valorSacado = servicoConta.sacar(numero, valor);
        if (valorSacado > 0) {
            return ResponseEntity.ok("Saque realizado com sucesso! Valor sacado: " + valorSacado);
        }
        return ResponseEntity.badRequest().body("Erro ao processar saque.");
    }
    @PostMapping("/depositar")
    public ResponseEntity<String> realizarDeposito(@RequestBody Map<String, Object> dados) {
        String numero = (String) dados.get("numero");
    double valor = Double.parseDouble(dados.get("valor").toString());

    double valorDepositado = servicoConta.depositar(numero, valor);
        if (valorDepositado > 0) {
            return ResponseEntity.ok("Depósito realizado com sucesso! Valor depositado: " + valorDepositado);
        }
        return ResponseEntity.badRequest().body("Erro ao processar depósito.");
    }
}
