package bancodigital.control;

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
        return servicoConta.criarNovaConta(dadosRecebidos.getTitular(), dadosRecebidos.getNumeroConta(), dadosRecebidos.getSaldo());
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
    public ResponseEntity<String> realizarTransfer(@RequestBody ContaCorrente dados){
        boolean sucesso = servicoConta.transferir(dados.getNumeroConta(), dados.getNumeroConta(), dados.getSaldo());
        if (sucesso) {
            return ResponseEntity.ok("Transferência realizada com sucesso!");
        }
        return ResponseEntity.badRequest().body("Erro ao processar transferência.");
    }
}
