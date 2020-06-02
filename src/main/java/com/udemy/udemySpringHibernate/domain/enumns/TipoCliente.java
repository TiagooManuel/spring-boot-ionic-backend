package com.udemy.udemySpringHibernate.domain.enumns;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pesso Jurídica");
	
	private int cod;
	private String desricao;
	
	private TipoCliente(int cod, String descricao) {
		this.cod=cod;
		this.desricao=descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDesricao() {
		return desricao;
	}
	
	//static pq esta operacao tem de ser possivel ser executada sem instanciar objetos  
	public static TipoCliente toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
			
			for (TipoCliente x : TipoCliente.values()) {
				//se o codigo que está a percorrer(x.getCod) for igual ao codigo que passamos como parametro(cod.equals),
				//entao retorna o x
				if(cod.equals(x.getCod())) {
					return x;
				}
			}
			
			throw new IllegalArgumentException("Id inválido: " + cod);
		}
		
	}

	
	
	
