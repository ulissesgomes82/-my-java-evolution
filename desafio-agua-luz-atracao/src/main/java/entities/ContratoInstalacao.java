package entities;

import java.time.LocalDateTime;

import entities.enums.TipoNotificacao;
import entities.enums.TipoServico;

public class ContratoInstalacao {
	private Integer protocolo;
	private LocalDateTime agendamento;
	private TipoServico tipoServico;
	private Double valorServico;
	private TipoNotificacao tipoNotificacao;
	private Contratante contratante;

	public ContratoInstalacao(Integer protocolo, TipoServico tipoServico, TipoNotificacao tipoNotificacao,
			Contratante contratante) {
		super();
		this.protocolo = protocolo;
		agendamento = LocalDateTime.now();
		this.tipoServico = tipoServico;
		valorServico = tipoServico.getValor();
		this.tipoNotificacao = tipoNotificacao;
		this.contratante = contratante;
	}

	public Integer getProtocolo() {
		return protocolo;
	}

	public TipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	public TipoNotificacao getTipoNotificacao() {
		return tipoNotificacao;
	}

	public void setTipoNotificacao(TipoNotificacao tipoNotificacao) {
		this.tipoNotificacao = tipoNotificacao;
	}

	public Contratante getContratante() {
		return contratante;
	}

	public void setContratante(Contratante contratante) {
		this.contratante = contratante;
	}

	public LocalDateTime getAgendamento() {
		return agendamento;
	}

	public Double getValorServico() {
		return valorServico;
	}

}
