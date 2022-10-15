package services;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entities.ContratoInstalacao;

public class Cadastro {

	public static String buscarCadastro(String path, Integer protocolo) {
		return "";
	}

	public static void salvarCadastro(ContratoInstalacao contrato) {
		StringBuilder sb = new StringBuilder();

		String cpf = contrato.getContratante().getCpf().replaceAll("[\\.\\-]", "");
		String rg = String.format("%10.10s", contrato.getContratante().getRg().replaceAll("[\\-]", ""));
		String nome = String.format("%30.30s", contrato.getContratante().getNome().toUpperCase());
		String celular = contrato.getContratante().getCelular().replaceAll("[\\(\\)\\-]", "");
		String logradouro = String.format("%20.20s", contrato.getContratante().getEndereco().getLogradouro());
		String numero = String.format("%06d", contrato.getContratante().getEndereco().getNumero());
		String complemento = String.format("%-30.10s", contrato.getContratante().getEndereco().getComplemento());
		String bairro = contrato.getContratante().getEndereco().getBairro().toUpperCase();
		String cidade = contrato.getContratante().getEndereco().getCidade().toUpperCase();
		String uf = contrato.getContratante().getEndereco().getUf().name();
		String cep = contrato.getContratante().getCpf().replaceAll("[\\.]", "");
		String pais = contrato.getContratante().getEndereco().getPais().name();
		String protocolo = String.format("%-10.10d", contrato.getProtocolo());
		String data = contrato.getAgendamento().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String hora = contrato.getAgendamento().format(DateTimeFormatter.ofPattern("HHmm"));
		String tipoServico = contrato.getTipoServico().getValor();
		String stringValor = String.valueOf(contrato.getValorServico()).replace('.', ' ');
		String valor = String.format("%08d", stringValor);

		String tipoNotificacao = contrato.getTipoNotificacao().getValor();

		sb.append(cpf.concat(rg).concat(nome).concat(celular).concat(logradouro).concat(numero).concat(complemento)
				.concat(bairro).concat(cidade).concat(cidade).concat(uf).concat(cep).concat(pais).concat(protocolo)
				.concat(data).concat(hora).concat(tipoServico).concat(valor).concat(tipoNotificacao));

	}

	public static void salvarCadastro2(ContratoInstalacao contrato) {
		List<String> list = new ArrayList<String>();

		list.add(contrato.getContratante().getCpf().replaceAll("[\\.\\-]", ""));
		list.add(String.format("%-10.10s", contrato.getContratante().getRg().replaceAll("[\\-]", "")));
		list.add(String.format("%-30.30s", contrato.getContratante().getNome().toUpperCase()));
		list.add(contrato.getContratante().getCelular().trim().replaceAll("[\\(\\)\\-]", ""));
		list.add(String.format("%20.20s", contrato.getContratante().getEndereco().getLogradouro()));
		list.add(String.format("%06d", contrato.getContratante().getEndereco().getNumero()));
		list.add(String.format("%-30.10s", contrato.getContratante().getEndereco().getComplemento()));
		list.add(contrato.getContratante().getEndereco().getBairro().toUpperCase());
		list.add(contrato.getContratante().getEndereco().getCidade().toUpperCase());
		list.add(contrato.getContratante().getEndereco().getUf().name());
		list.add(contrato.getContratante().getCpf().replaceAll("[\\.]", ""));
		list.add(contrato.getContratante().getEndereco().getPais().name());
		list.add(String.format("%010d", contrato.getProtocolo()));
		list.add(contrato.getAgendamento().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		list.add(contrato.getAgendamento().format(DateTimeFormatter.ofPattern("HHmm")));
		list.add(contrato.getTipoServico().getValor());
		// Coloca o valor do servico de acordo com o tipo de serviço porem 
		if (contrato.getTipoServico().getValor().equals("A")) {
			contrato.setValor(137.21);
			
		} else if (contrato.getTipoServico().getValor().equals("L")) {
			contrato.setValor(132.15);
			System.out.println(contrato.getValorServico());
		}
		list.add(String.valueOf(contrato.getValorServico()).replace('.', ' '));
		list.add(contrato.getTipoNotificacao().getValor());

		// list.stream().map(Object::toString).collect(Collectors.joining(""));
		// return String.join("", list);

		String sb = String.join("", list);
		String nomeArquivo = String.format("agua-luz-output.txt");
		File diretorio = new File("c:\\estudos\\mjv-java-scholl");
		if (!diretorio.exists())
			diretorio.mkdirs();

		File arquivo = new File(diretorio, nomeArquivo);
		Path path = arquivo.toPath();
		try {
			Files.write(path, sb.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
