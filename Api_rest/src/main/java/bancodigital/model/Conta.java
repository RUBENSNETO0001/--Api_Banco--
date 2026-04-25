package bancodigital.model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_conta")
public abstract class Conta { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titular;
    private String numeroConta;
    protected double saldo;

    protected Conta() {}

    public Conta(String titular, String numeroConta, double limite) {
        this.titular = titular;
        this.numeroConta = numeroConta;
        this.saldo = 0.0;
    }
    
    // gets e sets
    public Long getId() {
        return id;
    }

    public String getTitular(){
        return titular;
    }

    public String getNumeroConta(){
        return numeroConta;
    }

    public double getSaldo(){
        return saldo;
    }
    public Long setId(Long id) {
        return this.id = id;
    }
    public void setTitular(String titular) {
        this.titular = titular;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}