package bancodigital.model;

import jakarta.persistence.Entity;

@Entity
public class ContaCorrente extends Conta {

    public ContaCorrente() {
        super();
    }

    public ContaCorrente(String titular, String numeroConta, double saldo) {
        super(titular, numeroConta, saldo);
    }
}
