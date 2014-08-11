package agenda.contatos;

import java.util.Date;

import agenda.exceptions.NomeComCaracteresInvalidosException;
import agenda.exceptions.NomeComDuasParticulasException;

@SuppressWarnings("serial")
public class Cliente extends Contato {

	protected Date ultimaCompra;
	protected int fidelidade;

	public Cliente(String nome, String email, String endereco, String telefone,
			Date aniversario, Date ultimaCompra, int fidelidade)
			throws NomeComDuasParticulasException,
			NomeComCaracteresInvalidosException {
		super(nome, email, endereco, telefone, aniversario);
		this.ultimaCompra = ultimaCompra;
		this.fidelidade = fidelidade;
	}

	public Date getUltimaCompra() {
		return this.ultimaCompra;
	}

	public String getFidelidade() {
		switch (fidelidade) {
		case 1:
			return "Apenas uma vez";
		case 2:
			return "Pouco frequente";
		case 3:
			return "Frequente";
		default:
			return "Fiel";
		}
	}

	public void setUltimaCompra(Date ultimaCompra) {
		this.ultimaCompra = ultimaCompra;
	}

	public void setFidelidade(byte fidelidade) {
		this.fidelidade = fidelidade;
	}

	@Override
	public String toString() {
		return super.toString() + " - ultima compra = " + this.ultimaCompra
				+ " - " + getFidelidade();
	}

}
