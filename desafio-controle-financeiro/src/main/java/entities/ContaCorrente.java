package entities;

import java.util.Date;

public class ContaCorrente {
	private Integer numeroConta;
	private Integer numeroAgencia;
	private String nomeCliente;
	private Date dataNascimento;
	private Double saldo;

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

}
