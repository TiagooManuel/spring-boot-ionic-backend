package com.udemy.udemySpringHibernate.domain.enumns;

public enum EstadoPagamento {

	PENDENTE(1, "Pendente"), QUITADO(2, "Quitado"), CAMCELADO(3, "Cancelado");

	private int cod;
	private String descricao;

	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static EstadoPagamento toEnum(Integer cod) {

		if (cod == null) {
			return null;
		}

		for (EstadoPagamento x : EstadoPagamento.values()) {
			// se o codigo que está a percorrer(x.getCod) for igual ao codigo que passamos
			// como parametro(cod.equals),
			// entao retorna o x
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + cod);
	}

}
