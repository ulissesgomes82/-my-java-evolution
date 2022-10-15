package application;

import entities.Contratante;
import entities.ContratoInstalacao;
import entities.Endereco;
import entities.enun.TipoNotificacao;
import entities.enun.TipoServico;
import entities.enun.UnidadeFederativa;
import services.Cadastro;

public class Program {

	public static void main(String[] args) {
//		String teste = "007.324.223-21";
//		String cpf = teste.replaceAll("[\\.\\-]","");
//		
//		
//	//	System.out.println(cpf);
//		System.out.println(UnidadeFederativa.CE.name());
//		
//		String teste = "AP 210 BL CENTAURO";
//		new String();
//		System.out.println(String.format("%-10.10s", teste));

//		LocalDateTime data = LocalDateTime.now();
//		
//		System.out.println(data.toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")).repl);
//		System.out.println(data.toLocalTime().format(DateTimeFormatter.ofPattern("HHmm")));
//
//		List<String> list = new ArrayList<String>();
//		list.add("teste");
//		list.add("teste1");
//		list.add("test45");
//		list.add("test87");
//		list.add("test48");
//		list.add("Test48");
//		list.add("teste");
//		list.add("teste1");
//		list.add("test45");
//		list.add("test87");
//		list.add("test48");
//		list.add("Test48");
//		list.add("teste");
//		list.add("teste1");
//		list.add("test45");
//		list.add("test87");
//		list.add("test48");
//		list.add("Test48");
//		list.add("teste");
//		list.add("teste1");
//		list.add("test45");
//		list.add("test87");
//		list.add("test48");
//		list.add("Test48");
//		list.add("teste");
//		list.add("teste1");
//		list.add("test45");
//		list.add("test87");
//		list.add("test48");
//		list.add("Test48");

		// System.out.println(list.stream().collect(Collectors.joining(",")));
		// System.out.println(String.join(", ", list));

		//System.out.println(00012735);
//		
//		Double valor = 127.35;
//		System.out.println(valor);
		
		Endereco endereco = new Endereco("Rua Sebastião Firmino", 123, "AP 210 BL CENTAURO", "São Sebastião", "São Raimundo Nonato", UnidadeFederativa.valueOf("Sp".toUpperCase()),"07.210.715");
		
		Contratante contratante = new Contratante("007.324.223.21", "33765-5", "Raimundo Nonato Loureiro Castelo Branco", "(11) 99768-1515", endereco);
		
		ContratoInstalacao contratoInstalacao = new ContratoInstalacao(1984365,TipoServico.LUZ, TipoNotificacao.SMS, contratante);
		
		//System.out.println(contratoInstalacao.getProtocolo());
		
//		ContratoInstalacao contrato = new ContratoInstalacao();
//		contrato.setValor("130.50");
//		System.out.println(contrato.getValorServico());
		
		Cadastro.salvarCadastro2(contratoInstalacao);
		
	}

}
