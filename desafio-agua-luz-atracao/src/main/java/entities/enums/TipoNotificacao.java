package entities.enums;

public enum TipoNotificacao {
	SMS("S"),
	WHATS("W");
	
	String sigla;

	private TipoNotificacao(String sigla) {
		this.sigla = sigla;
	}

	public String getValor() {
		return sigla;
	}
	
	
}
