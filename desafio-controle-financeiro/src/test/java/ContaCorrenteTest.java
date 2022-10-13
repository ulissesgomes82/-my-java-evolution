import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.concurrent.CancellationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import entities.ContaCorrente;
import entities.exceptions.DepositoInvalidoException;
import entities.exceptions.SaqueInvalidoException;
import entities.exceptions.TransferenciaInvalidaException;

class ContaCorrenteTest {

	ContaCorrente conta;
	ContaCorrente contaDestino;
	@BeforeEach
	void setUp() throws Exception {
		conta = new ContaCorrente("Ulisses Gomes", LocalDate.of(1982, 9, 10), 100.0);
		contaDestino = new ContaCorrente("Paloma", LocalDate.of(1980, 2, 21), 100.0);
	}

	/* Começo teste depositar */

	@DisplayName("deposito valor negativo")
	@Test
	void depositoMenorZero() {
		DepositoInvalidoException justificativa = assertThrows(DepositoInvalidoException.class,
				() -> conta.depositar(-100.0));
		assertEquals("O valor de depósito deve ser superior a zero.", justificativa.getMessage());
	}

	@DisplayName("deposito valor igual a zero")
	@Test
	void depositoIgualZero() {
		DepositoInvalidoException justificativa = assertThrows(DepositoInvalidoException.class,
				() -> conta.depositar(0.0));
		assertEquals("O valor de depósito deve ser superior a zero.", justificativa.getMessage());
	}

	@DisplayName("deposito valor nulo")
	@Test
	void depositoNulo() {
		DepositoInvalidoException justificativa = assertThrows(DepositoInvalidoException.class,
				() -> conta.depositar(null));
		assertEquals("O valor de depósito deve ser superior a zero.", justificativa.getMessage());
	}

	@DisplayName("deposito valor")
	@Test
	void deposito() throws DepositoInvalidoException {
		Double valorDeposito = 60.0;
		conta.depositar(valorDeposito);
		Double expectativa = 160.0;
		assertEquals(expectativa, conta.consultarSaldoAtual());
		assertEquals(1, conta.getTransacao().size());
		assertEquals(valorDeposito, conta.getTransacao().get(0).getValor());
	}

	/* Fim teste depositar */

	/* Começo teste sacar */

	@DisplayName("saque valor nulo")
	@Test
	void saqueNulo() {
		SaqueInvalidoException justificativa = assertThrows(SaqueInvalidoException.class, () -> conta.sacar(null));
		assertEquals("O valor de saque deve ser superior a zero.", justificativa.getMessage());
	}

	@DisplayName("saque valor zero")
	@Test
	void saqueZero() {
		SaqueInvalidoException justificativa = assertThrows(SaqueInvalidoException.class, () -> conta.sacar(0.0));
		assertEquals("O valor de saque deve ser superior a zero.", justificativa.getMessage());
	}

	@DisplayName("saque valor negativo")
	@Test
	void saqueMenorZero() {
		SaqueInvalidoException justificativa = assertThrows(SaqueInvalidoException.class, () -> conta.sacar(-100.0));
		assertEquals("O valor de saque deve ser superior a zero.", justificativa.getMessage());
	}

	@DisplayName("saque maior que saldo")
	@Test
	void saqueMaiorSaldo() {
		SaqueInvalidoException justificativa = assertThrows(SaqueInvalidoException.class, () -> conta.sacar(500.0));
		assertEquals("Saldo insuficiente para esta transação.", justificativa.getMessage());
	}

	@DisplayName("sacar valor")
	@Test
	void saque() throws SaqueInvalidoException {
		Double valorSaque = 100.0;
		conta.sacar(valorSaque);
		Double expectativa = 0.0;
		assertEquals(expectativa, conta.consultarSaldoAtual());
		assertEquals(1, conta.getTransacao().size());
		assertEquals(valorSaque, conta.getTransacao().get(0).getValor());
	}
	/* Fim teste sacar */

	/*Inicio teste transferência*/
	
	@DisplayName("transferência para conta destino nula")
	@Test
	void transferirParaContaNula() {
		NullPointerException excecao = assertThrows(NullPointerException.class, ()->conta.transferir(null, 50.0));
		assertEquals("Deve ser informado uma conta valida para transferência.", excecao.getMessage());
	}
	
	@DisplayName("transferência para conta destino cancelada")
	@Test
	void tranferirContaDestinoCancelada() {
		contaDestino.cancelarConta("Teste de transferencia para ");
		CancellationException excecao = assertThrows(CancellationException.class, ()->conta.transferir(contaDestino, 100.0));
		assertEquals("Não é possível realizar essa operação, conta destino cancelada.", excecao.getMessage());
	}
	
	@DisplayName("transferir valor negativo")
	@Test
	void tranferirValorMenorZero() {
		TransferenciaInvalidaException excecao = assertThrows(TransferenciaInvalidaException.class, ()->conta.transferir(contaDestino, -50.0));
		assertEquals("O valor para transferência deve ser superior a zero.", excecao.getMessage());
	}
	
	@DisplayName("transferir valor igual a zero")
	@Test
	void transferirValorZero() {
		TransferenciaInvalidaException excecao = assertThrows(TransferenciaInvalidaException.class, ()->conta.transferir(contaDestino, 0.0));
		assertEquals("O valor para transferência deve ser superior a zero.", excecao.getMessage());
	}
	
	@DisplayName("transferir valor maior que saldo")
	@Test
	void transferirValorMaiorSaldo() {
		TransferenciaInvalidaException excecao = assertThrows(TransferenciaInvalidaException.class, ()-> conta.transferir(contaDestino, 500.0));
		assertEquals("Saldo insuficiente para esta transação.", excecao.getMessage());
	}
	
	@DisplayName("transferir valor")
	@Test
	void transferir() throws TransferenciaInvalidaException {
		Double valorTransferido = 30.0;
		conta.transferir(contaDestino, valorTransferido);
		Double expectativaSaldo = 70.0;
		Double expectativaDestinoSaldo = 130.0;
		assertEquals(expectativaSaldo, conta.getSaldo());
		assertEquals(expectativaDestinoSaldo, contaDestino.getSaldo());
		assertEquals(1, conta.getTransacao().size());
		assertEquals(valorTransferido, conta.getTransacao().get(0).getValor());
	}
	/*Fim teste transferência*/
	
	/*inicio teste consultar extrato*/
	
	@DisplayName("consultar extrato data inicial nula")
	@Test
	void consultarExtratoDataInicialNula() {
		DateTimeException excecao = assertThrows(DateTimeException.class, ()->conta.consultarExtrato(null, LocalDate.of(2022, 8, 1)));
		assertEquals("Data inicial ou final é inválida.", excecao.getMessage());
	}
	
	@DisplayName("consultar extrato data final nula")
	@Test
	void consultarExtratoDataFinalNula() {
		DateTimeException excecao = assertThrows(DateTimeException.class, ()->conta.consultarExtrato(LocalDate.of(2022, 9, 1), null));
		assertEquals("Data inicial ou final é inválida.", excecao.getMessage());
	}
	
	@DisplayName("consultar extrato")
	@Test
	void consultarExtrato() throws DepositoInvalidoException, SaqueInvalidoException, TransferenciaInvalidaException {
		conta.depositar(100.0);
		conta.sacar(50.0);
		conta.depositar(500.0);
		conta.transferir(contaDestino, 50.0);
		contaDestino.depositar(500.0);
		contaDestino.sacar(100.0);
		contaDestino.transferir(conta, 100.0);
		assertEquals(4, conta.getTransacao().size());
		assertEquals(3, contaDestino.getTransacao().size());
	}
	
	/*Fim teste consultar extrato*/
	
	@DisplayName("Validando conta")
	@Test
	void validarConta() {
		conta.cancelarConta("teste de validação da conta");
		CancellationException justificativa = assertThrows(CancellationException.class, () -> conta.validaConta());
		assertEquals("Não é possível realizar essa operação, conta cancelada.", justificativa.getMessage());
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
		CancellationException justificativa = assertThrows(CancellationException.class, () -> conta.cancelarConta(""));
		assertEquals("Justifique o motivo do cancelamento.", justificativa.getMessage());
	}

	@DisplayName("Cancelar conta justificativa vazia")
	@Test
	void contaCanceladaJustificativaBranco() {
		CancellationException justificativa = assertThrows(CancellationException.class,
				() -> conta.cancelarConta("  "));
		assertEquals("Justifique o motivo do cancelamento.", justificativa.getMessage());
	}

	@DisplayName("Cancelar conta com justificativa")
	@Test
	void contaCanceladaComJustificativa() {
		conta.cancelarConta("Conta cancelada");
		assertEquals(true, conta.getCancelada());
	}

}
