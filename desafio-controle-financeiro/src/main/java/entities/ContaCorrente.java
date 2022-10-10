package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Extrato.tipoOperacao;
import entities.exceptions.DomainException;

public class ContaCorrente {
	private Integer numeroConta;
	private Integer numeroAgencia;
	private String nomeCliente;
	private Date dataNascimento;
	private Double saldo = 0.0;
	private Boolean cancelada = false;
	private List<Extrato> transacao = new ArrayList<Extrato>();

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
		transacao.add(new Extrato(valor, tipoOperacao.DEPOSITO));
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
		transacao.add(new Extrato(valor, tipoOperacao.SAQUE));
	}

	public void transferir(ContaCorrente contaDestino, Double valor) throws DomainException {
		if (contaDestino == null) {
			throw new DomainException("Deve ser informado uma conta valida para transferência.");
		} else if (contaDestino.cancelada) {
			throw new DomainException("Conta destino cancelada.");
		}
		saldo -= valor;
		contaDestino.saldo += valor;
		transacao.add(new Extrato(valor, tipoOperacao.TRANSFERÊNCIA));
	}

	public void cancelarConta(String justificativa) throws DomainException {
		if (justificativa.isBlank()) {
			throw new DomainException("Informe uma justificativa para o cancelamento.");
		} else if (cancelada) {
			throw new DomainException("Conta cancelada.");
		}
		cancelada = true;
	}

}
