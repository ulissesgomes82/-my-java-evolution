package entities;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CancellationException;

import entities.Extrato.tipoOperacao;
import entities.exceptions.DepositoInvalidoException;
import entities.exceptions.SaqueInvalidoException;
import entities.exceptions.TransferenciaInvalidaException;

public class ContaCorrente {
	private Integer numeroConta = new Random().nextInt(1000);
	private Integer numeroAgencia = new Random().nextInt(1000);
	private String nomeCliente;
	private LocalDate dataNascimento;
	private Double saldo = 00.00;
	private Boolean cancelada = false;
	private List<Extrato> transacao = new ArrayList<Extrato>();

	public ContaCorrente(String nomeCliente, LocalDate dataNascimento) {
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public Double getSaldo() {
		return saldo;
	}

	public Boolean getCancelada() {
		return cancelada;
	}

	public List<Extrato> getTransacao() {
		return transacao;
	}

	public void depositar(Double valor) throws DepositoInvalidoException {
		validaConta();
		if (valor == null || valor <= 0.0) {
			throw new DepositoInvalidoException("O valor de depósito deve ser superior a zero.");
		}
		saldo += valor;
		transacao.add(new Extrato(valor, tipoOperacao.DEPOSITO));
	}

	public void sacar(Double valor) throws SaqueInvalidoException {
		validaConta();
		if (valor <= 0.0) {
			throw new SaqueInvalidoException("O valor de saque deve ser superior a zero.");
		} else if (valor > saldo) {
			throw new SaqueInvalidoException("Saldo insuficiente para esta transação.");
		}
		saldo -= valor;
		transacao.add(new Extrato(valor, tipoOperacao.SAQUE));
	}

	public void transferir(ContaCorrente contaDestino, Double valor) throws TransferenciaInvalidaException {
		if (contaDestino == null) {
			throw new NullPointerException("Deve ser informado uma conta valida para transferência.");
		} else if (contaDestino.cancelada) {
			throw new CancellationException("Não é possível realizar essa operação, conta destino cancelada.");
		} else if (valor <= 0.0) {
			throw new TransferenciaInvalidaException("O valor para transferência deve ser superior a zero.");
		} else if (valor > saldo) {
			throw new TransferenciaInvalidaException("Saldo insuficiente para esta transação.");
		}
		saldo -= valor;
		contaDestino.saldo += valor;
		transacao.add(new Extrato(valor, tipoOperacao.TRANSFERÊNCIA));
	}

	public void cancelarConta(String justificativa) throws CancellationException {
		validaConta();
		if (justificativa == null || justificativa.isBlank()) {
			throw new CancellationException("Justifique o motivo do cancelamento.");
		}
		cancelada = true;
	}

	public void validaConta() throws CancellationException {
		if (cancelada) {
			throw new CancellationException("Não é possível realizar essa operação, conta cancelada.");
		}
	}

	public Double consultarSaldoAtual() {
		return saldo;
	}

	public List<Extrato> consultarExtrato(LocalDate dataInicial, LocalDate dataFinal) {
		validaConta();
		if (dataInicial == null || dataFinal == null) {
			throw new DateTimeException("Data inicial ou final é inválida.");
		}
		if (dataInicial.isAfter(dataFinal)) {
			throw new DateTimeException("Data inicial não pode ser posterior a data final.");
		}
		return transacao.stream().filter(
				data -> data.getDataOperacao().isAfter(dataInicial) && data.getDataOperacao().isBefore(dataFinal))
				.toList();

	}
}
