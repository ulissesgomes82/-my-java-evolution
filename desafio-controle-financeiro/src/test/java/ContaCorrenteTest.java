import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.concurrent.CancellationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import entities.ContaCorrente;
import entities.exceptions.DepositoInvalidoException;
import entities.exceptions.SaqueInvalidoException;

class ContaCorrenteTest {

	ContaCorrente conta;

	@BeforeEach
	void setUp() throws Exception {
		conta = new ContaCorrente("Ulisses Gomes", LocalDate.of(1982, 9, 10));
		
	}
	
	/*Começo teste depositar*/
	
	@DisplayName("deposito valor negativo")
	@Test
	void  depositoMenorZero() {
		DepositoInvalidoException justificativa = assertThrows(DepositoInvalidoException.class, ()-> conta.depositar(-100.0));
		assertEquals("O valor de depósito deve ser superior a zero.", justificativa.getMessage());
	}
	
	@DisplayName("deposito valor igual a zero")
	@Test
	void depositoIgualZero() {
		DepositoInvalidoException justificativa = assertThrows(DepositoInvalidoException.class, ()->conta.depositar(0.0));
		assertEquals("O valor de depósito deve ser superior a zero.", justificativa.getMessage());
	}
	
	@DisplayName("deposito valor nulo")
	@Test
	void depositoNulo() {
		DepositoInvalidoException justificativa = assertThrows(DepositoInvalidoException.class, ()-> conta.depositar(null));
		assertEquals("O valor de depósito deve ser superior a zero.", justificativa.getMessage());
	}
	
	@DisplayName("deposito valor")
	@Test
	void deposito() throws DepositoInvalidoException {
		conta.depositar(60.0);
		Double expectativa = 60.0;
		assertEquals(expectativa, conta.consultarSaldoAtual());
		assertEquals(1, conta.getTransacao().size());
	}
	
	/*Fim teste depositar*/
	

	@DisplayName("Cancelar conta justificativa nula")
	@Test
	void contaCanceladaJustificativaNula() {
		CancellationException justificativa = assertThrows(CancellationException.class,
				() -> conta.cancelarConta(null));
		assertEquals("Justifique o motivo do cancelamento.", justificativa.getMessage());
	}
	
	@DisplayName("Cancelar conta justificativa vazia")
	@Test
	void contaCanceladaJustificativaVazia() {
		CancellationException justificativa = assertThrows(CancellationException.class, ()->conta.cancelarConta(""));
		assertEquals("Justifique o motivo do cancelamento.", justificativa.getMessage());
	}
	
	@DisplayName("Cancelar conta justificativa vazia")
	@Test
	void contaCanceladaJustificativaBranco() {
		CancellationException justificativa = assertThrows(CancellationException.class, ()-> conta.cancelarConta("  "));
		assertEquals("Justifique o motivo do cancelamento.", justificativa.getMessage());
	}
	
	@DisplayName("Cancelar conta com justificativa")
	@Test
	void contaCanceladaComJustificativa() {
		conta.cancelarConta("Conta cancelada");
		assertEquals(true, conta.getCancelada());
	}

}
