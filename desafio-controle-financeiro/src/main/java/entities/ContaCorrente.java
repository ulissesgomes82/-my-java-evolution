package entities;

import java.util.Date;

import entities.exceptions.DomainException;

public class ContaCorrente {
	private Integer numeroConta;
	private Integer numeroAgencia;
	private String nomeCliente;
	private Date dataNascimento;
	private Double saldo;
	private Boolean cancelada = false;

	public ContaCorrente() {
	}

	public ContaCorrente(Integer numeroConta, Integer numeroAgencia, String nomeCliente, Date dataNascimento,
			Double saldo) {
		this.numeroConta = numeroConta;
		this.numeroAgencia = numeroAgencia;
		this.nomeCliente = nomeCliente;
		this.dataNascimento = dataNascimento;
		this.saldo = saldo;
	}

	public Integer getNumeroConta() {
		return numeroConta;
	}

	public Integer getNumeroAgencia() {
		return numeroAgencia;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public Double getSaldo() {
		return saldo;
	}

	public Boolean getCancelada() {
		return cancelada;
	}

	public void depositar(Double valor) throws DomainException {
		if (valor <= 0.0) {
			throw new DomainException("O valor de depósito deve ser superior a zero.");
		} else if (cancelada) {
			throw new DomainException("Conta cancelada.");
		}
		saldo += valor;
	}

	public void sacar(Double valor) throws DomainException {
		if (valor <= 0.0) {
			throw new DomainException("O valor de saque deve ser superior a zero.");
		} else if (valor > saldo) {
			throw new DomainException("Saldo insuficiente para esta transação.");
		} else if (cancelada) {
			throw new DomainException("Conta cancelada.");
		}
		saldo -= valor;
	}

	public void transferir(ContaCorrente contaDestino, Double valor) throws DomainException {
		if (contaDestino.equals(null)) {
			throw new DomainException("Deve ser informado uma conta para transferir o valor.");
		} else if (cancelada) {
			throw new DomainException("Conta cancelada.");
		}
		sacar(valor);
		contaDestino.depositar(valor);
	}

	public void cancelarConta(String justificativa) throws DomainException {
		if (justificativa == null || justificativa.isBlank()) {
			throw new DomainException("Informe uma justificativa para o cancelamento.");
		} else if (cancelada) {
			throw new DomainException("Conta cancelada.");
		}
		cancelada = true;
	}

}
