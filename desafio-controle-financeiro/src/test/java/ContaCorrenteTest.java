import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.concurrent.CancellationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import entities.ContaCorrente;

class ContaCorrenteTest {

	ContaCorrente conta;

	@BeforeEach
	void setUp() throws Exception {
		conta = new ContaCorrente("Ulisses Gomes", LocalDate.of(1982, 9, 10));
	}

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
