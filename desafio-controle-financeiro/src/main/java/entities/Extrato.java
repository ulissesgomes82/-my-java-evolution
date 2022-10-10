package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Extrato {
	private LocalDate dataOperacao;
	private Double valor;
	private tipoOperacao operacao;

	public Extrato(Double valor, tipoOperacao operacao) {
		dataOperacao = LocalDate.now();
		this.valor = valor;
		this.operacao = operacao;
	}

	public LocalDate getDataOperacao() {
		return dataOperacao;
	}

	public Double getValor() {
		return valor;
	}

	public tipoOperacao getOperacao() {
		return operacao;
	}

	public enum tipoOperacao {
		DEPOSITO, SAQUE, TRANSFER�NCIA
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Data Opera��o: " 
		+ dataOperacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) 
		+ "\nOpera��o: " + operacao
		+ "\nValor: " + String.format("%.2f%n", valor));
		
		return sb.toString();
	}
}
