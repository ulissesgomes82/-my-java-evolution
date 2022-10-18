package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

import entities.ContratoInstalacao;
import entities.enums.TipoServico;

public class Cadastro {
	public static void salvarCadastro(String nomeArquivo, ContratoInstalacao contrato) throws IOException {

		List<String> list = new ArrayList<String>();

		list.add(String.format("%011d",
				Long.parseLong(contrato.getContratante().getCpf().replaceAll("[\\s\\.\\-]", ""))));
		list.add(String.format("%-10.10s", contrato.getContratante().getRg().replaceAll("[\\s\\.\\-]", "")));
		list.add(String.format("%30.30s", contrato.getContratante().getNome().toUpperCase()));
		list.add(String.format("%11.11s", contrato.getContratante().getCelular().replaceAll("[\\s\\(\\)\\-]", "")));
		list.add(String.format("%-20.20s", contrato.getContratante().getEndereco().getLogradouro().toUpperCase()));
		list.add(String.format("%06d", contrato.getContratante().getEndereco().getNumero()));
		list.add(String.format("%-10.10s", contrato.getContratante().getEndereco().getComplemento()));
		list.add(String.format("%-15.15s", contrato.getContratante().getEndereco().getBairro().toUpperCase()));
		list.add(String.format("%-30.30s", contrato.getContratante().getEndereco().getCidade().toUpperCase()));
		list.add(String.format("%-2s", contrato.getContratante().getEndereco().getUf()).toUpperCase());
		list.add(String.format("%-8.8s",
				contrato.getContratante().getEndereco().getCep().replaceAll("[\\s\\.\\-]", "")));
		list.add(String.format("%2s", contrato.getContratante().getEndereco().getPais().getSigla()));
		list.add(String.format("%010d", contrato.getProtocolo()));
		list.add(contrato.getAgendamento().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		list.add(contrato.getAgendamento().format(DateTimeFormatter.ofPattern("HHmm")));
		list.add(String.format("%1s", contrato.getTipoServico().getSigla()));
		list.add(String.format("%08d", Math.round(contrato.getValorServico() * 100)));
		list.add(String.format("%1s", contrato.getTipoNotificacao().getValor()));

		String cadastro = String.join("", list);

		// System.out.println(String.join("", list));

		String nome = String.format("%s", nomeArquivo);
		File diretorio = new File("C:\\estudo1\\mjv-java-school\\");
		if (!diretorio.exists()) {
			diretorio.mkdirs();
		}
		File arquivo = new File(diretorio, nome);
		Path path = arquivo.toPath();
		Files.writeString(path, cadastro + System.lineSeparator(), StandardOpenOption.CREATE,
				StandardOpenOption.APPEND);
	}

//	public static String buscarCadastro(String path, String protocolo) throws IOException {
//		return Files.lines(Path.of(path)).filter(c->c.contains(protocolo)).toList().get(0);
//	}

	public static void gerarRelatorio(String path, String protocoloBusca) throws IOException, ParseException {
		String cadastro = Files.lines(Path.of(path)).filter(c -> c.contains(protocoloBusca)).toList().get(0);

		String cpf = formatCPF(cadastro.substring(0, 11).trim());
		String rg = cadastro.substring(11, 21).trim();
		String nome = cadastro.substring(21, 51).trim();
		String celular = formatarCelular(cadastro.substring(51, 62).trim());
		String logradouro = cadastro.substring(62, 82).trim();
		Integer numero = Integer.parseInt(cadastro.substring(82, 88).trim());
		String complemento = cadastro.substring(88, 98).trim();
		String bairro = cadastro.substring(98, 113).trim();
		String cidade = cadastro.substring(113, 143).trim();
		String uf = cadastro.substring(143, 145).trim();
		String cep = formatCep(cadastro.substring(145, 153).trim());
		String pais = cadastro.substring(153, 155).trim();
		String protocolo = cadastro.substring(155, 165).trim();
		String dataHora = formatarDataHora(cadastro.substring(165, 177).trim());
		TipoServico servico = TipoServico.getServico(cadastro.substring(177, 178));
		String valor = formatarValor(cadastro.substring(179, 186).trim());
		String tipoNotificacaoo = cadastro.substring(186).trim();

		StringBuilder sb = new StringBuilder();

		sb.append(String.format(
				"Senhor(a) %s cpf numero %s, informamos que conforme contrato com %n"
						+ "protocolo de número %s está agendado para a data\\hora %sh a instalação do serviço %n"
						+ "de %s com taxa de valor %s em sua residência localizada no endereço abaixo%n",
				nome, cpf, protocolo, dataHora, servico, valor));

		sb.append(String.format(
				"\u2022 Logradouro: %s%n" + "\u2022 Complememto: %s%n" + "\u2022 Bairro: %s%n"
						+ "\u2022 Cidade: %s/%s%n" + "\u2022 Cep: %s",
				logradouro, complemento, bairro, cidade, uf, cep));
		System.out.println(sb.toString());
	}

	public static String formatCPF(String cpf) {
		return Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})").matcher(cpf).replaceAll("$1.$2.$3-$4");
	}

	public static String formatRG(String rg) {
		if (rg.length() == 9) {
			return Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{1})").matcher(rg).replaceAll("$1.$2.$3-$4");
		} else {
			return Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})").matcher(rg).replaceAll("$1.$2.$3-$4");
		}
	}

	public static String formatCNPJ(String cnpj) {
		return Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})").matcher("15587987000191")
				.replaceAll("$1.$2.$3/$4-$5");
	}

	public static String formatCep(String cep) {
		return Pattern.compile("(\\d{3})(\\d{3})(\\d{2})").matcher(cep).replaceAll("$1.$2-$3");
	}

	public static String formatarDataHora(String dataRelatorio) {
		DateTimeFormatter formatarStringParaData = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		DateTimeFormatter formatarData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		return LocalDateTime.parse(dataRelatorio, formatarStringParaData).format(formatarData);
	}

	public static String formatarValor(String valor) {
		Double valorFormatado = Double.parseDouble(valor) / 100;
		return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valorFormatado);
	}

	public static String formatarCelular(String celular) throws ParseException {
		MaskFormatter mf = new MaskFormatter("(##)#####-####");
		mf.setValueContainsLiteralCharacters(false);
		return mf.valueToString(celular);
	}

//	public static void main(String[] args) throws IOException, ParseException {
//
//		Endereco endereco = new Endereco("Rua Sebastião Firmino",123, "AP 210 BL CENTAURO", "São Sebastião",
//				"São Raimundo Nonato", TipoUnidadeFederativa.valueOf("sp".toUpperCase()), "07.210.715");
//		Contratante contratante = new Contratante("007.324.223.21", "33765-5",
//				"Raimundo Nonato Loureiro Castelo Branco", "(11) 99768-1515", endereco);
//		ContratoInstalacao contrato = new ContratoInstalacao(1984365, TipoServico.AGUA, TipoNotificacao.SMS,
//				contratante);
//		Cadastro.salvarCadastro("agua-luz-output", contrato);
//		Cadastro.gerarRelatorio("C:\\estudo1\\mjv-java-school\\agua-luz-output", "1984365");
//	}
}
