package agenda.contatos;

import java.util.Date;

import agenda.exceptions.NomeComCaracteresInvalidosException;
import agenda.exceptions.NomeComDuasParticulasException;

@SuppressWarnings("serial")
public class Fornecedor extends Contato {

	protected int qualidade;

	public Fornecedor(String nome, String email, String endereco,
			String telefone, Date aniversario, int qualidade)
			throws NomeComDuasParticulasException,
			NomeComCaracteresInvalidosException {
		super(nome, email, endereco, telefone, aniversario);
		this.qualidade = qualidade;
	}

	@Override
	public String toString() {
		return super.toString() + " - qualidade = " + this.qualidade;
	}

	public int getQualidade() {
		return this.qualidade;
	}

	public void setQualidade(int qualidade) {
		this.qualidade = qualidade;
	}

}
