package entities.enun;

public enum TipoServico {
	LUZ("L"),
	AGUA("A");
	
	String valor;
	
	TipoServico(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return this.valor;
	}
}
