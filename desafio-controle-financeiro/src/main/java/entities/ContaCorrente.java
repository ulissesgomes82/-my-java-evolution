package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import entities.Extrato.tipoOperacao;
import entities.exceptions.DomainException;

public class ContaCorrente {
	private Integer numeroConta = new Random().nextInt(1000);
	private Integer numeroAgencia = new Random().nextInt(1000);
	private String nomeCliente;
	private Date dataNascimento;
	private Double saldo = 0.0;
	private Boolean cancelada = false;
	private List<Extrato> transacao = new ArrayList<Extrato>();

	public ContaCorrente(String nomeCliente, Date dataNascimento) {
		this.nomeCliente = nomeCliente;
		this.dataNascimento = dataNascimento;
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
		validaConta();
		if (valor <= 0.0) {
			throw new DomainException("O valor de depósito deve ser superior a zero.");
		}
		saldo += valor;
		transacao.add(new Extrato(valor, tipoOperacao.DEPOSITO));
	}

	public void sacar(Double valor) throws DomainException {
		validaConta();
		if (valor <= 0.0) {
			throw new DomainException("O valor de saque deve ser superior a zero.");
		} else if (valor > saldo) {
			throw new DomainException("Saldo insuficiente para esta transação.");
		}
		saldo -= valor;
		transacao.add(new Extrato(valor, tipoOperacao.SAQUE));
	}

	public void transferir(ContaCorrente contaDestino, Double valor) throws DomainException {
		if (contaDestino == null) {
			throw new DomainException("Deve ser informado uma conta valida para transferência.");
		} else if (contaDestino.cancelada) {
			throw new DomainException("Não é possível realizar essa operação, conta destino cancelada.");
		} else if (valor <= 0.0) {
			throw new DomainException("O valor para transferência deve ser superior a zero.");
		} else if (valor > saldo) {
			throw new DomainException("Saldo insuficiente para esta transação.");
		}
		saldo -= valor;
		contaDestino.saldo += valor;
		transacao.add(new Extrato(valor, tipoOperacao.TRANSFERÊNCIA));
	}

	public void cancelarConta(String justificativa) throws DomainException {
		validaConta();
		if (justificativa.isBlank()) {
			throw new DomainException("Informe uma justificativa para o cancelamento.");
		}
		cancelada = true;
	}

	public void validaConta() throws DomainException {
		if (cancelada) {
			throw new DomainException("Não é possível realizar essa operação, conta cancelada.");
		}
	}

	public Double consultarSaldoAtual() {
		return saldo;
	}

	public List<Extrato> consultarExtrato(LocalDate dataInicial, LocalDate dataFinal) throws DomainException{
		validaConta();
		if (dataInicial == null || dataFinal == null) {
			throw new DomainException("Data inicial ou final é inválida.");
		}
		if (dataInicial.isAfter(dataFinal)) {
			throw new DomainException("Data inicial não pode ser posterior a data final.");
		}
		return transacao.stream().filter(data -> data.getDataOperacao().isAfter(dataInicial) && data.getDataOperacao().isBefore(dataFinal)).toList();
		
	}
}
