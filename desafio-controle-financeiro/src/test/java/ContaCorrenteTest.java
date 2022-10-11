import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entities.ContaCorrente;
import entities.exceptions.DomainException;

class ContaCorrenteTest {

	ContaCorrente conta;
	
	@BeforeEach
	void setUp() throws Exception {
		conta = new ContaCorrente("Ulisses", LocalDate.of(1982, 9, 10));
	}

	@Test
	void depositarTest() throws DomainException {
		conta.cancelarConta("Testando conta cancelada.");
	}

}
