package entities.enums;

public enum Pais {
	BRASIL("BR"), 
	ESTADDO_UNIDOS("USA"), 
	FRANÇA("FR");

	String sigla;

	private Pais(String sigla) {
		this.sigla = sigla;
	}

	public String getSigla() {
		return sigla;
	}

}
