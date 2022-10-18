package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entities.Contratante;
import entities.ContratoInstalacao;
import entities.Endereco;
import entities.enums.TipoNotificacao;
import entities.enums.TipoServico;
import entities.enums.TipoUnidadeFederativa;

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

}
