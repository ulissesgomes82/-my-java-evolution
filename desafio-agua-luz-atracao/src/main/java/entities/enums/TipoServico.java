package entities.enums;

public enum TipoServico {
	AGUA("A", 137.21), 
	LUZ("L", 132.15);

	String sigla;
	Double valor;

	private TipoServico(String sigla, Double valor) {
		this.sigla = sigla;
		this.valor = valor;
	}

	public String getSigla() {
		return sigla;
	}

	public Double getValor() {
		return valor;
	}

	public static TipoServico getServico(String servico) {
		for (TipoServico ts : values()) {
			if (ts.getSigla().equals(servico)) {
				return ts;
			}
		}
		throw new IllegalArgumentException("Este serviço não existe: " + servico);
	}
}
