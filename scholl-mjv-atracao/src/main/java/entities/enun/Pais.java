package entities.enun;

public enum Pais {
	BRASIL("BR"),
	ESTADO_UNIDOS("US"),
	FRAN�A("FR");
	
	String valor;
	
	Pais(String valor) {
		this.valor = valor;
	}
	
	public String getValor() {
		return this.valor;
	}
}
