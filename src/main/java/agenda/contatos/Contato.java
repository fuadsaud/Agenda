package agenda.contatos;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import agenda.exceptions.NomeComCaracteresInvalidosException;
import agenda.exceptions.NomeComDuasParticulasException;

@SuppressWarnings("serial")
public class Contato implements Serializable, Comparable<Contato> {

	protected String nome;
	protected String email;
	protected String endereco;
	protected String telefone;
	protected Date aniversario;
	protected int id;

	public Contato(String nome, String email, String endereco, String telefone,
			Date aniversario) throws NomeComDuasParticulasException,
			NomeComCaracteresInvalidosException {
		setNome(nome);
		this.email = email;
		this.endereco = endereco;
		this.telefone = telefone;
		this.aniversario = aniversario;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public Date getAniversario() {
		return aniversario;
	}

	public int getId() {
		return id;
	}

	public void setNome(String nome) throws NomeComDuasParticulasException,
			NomeComCaracteresInvalidosException {
		boolean temEspaco = false;
		for (int i = 0; i < nome.length(); i++) {
			if (!temEspaco && nome.charAt(i) == ' ' && i != nome.length() - 1) {
				temEspaco = true;
			} else if ((Character.toUpperCase(nome.charAt(i)) < 65 || Character
					.toUpperCase(nome.charAt(i)) > 90) && nome.charAt(i) != 32) {
				throw new NomeComCaracteresInvalidosException(
						"Nome não pode conter caracteres especiais");
			}
		}

		if (temEspaco == false) {
			throw new NomeComDuasParticulasException(
					"Nome precisa conter duas partículas");
		} else {
			this.nome = nome;
		}
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setAniversario(Date aniversario) {
		this.aniversario = aniversario;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Contato other = (Contato) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return id + " - " + nome + " - " + email + " - " + endereco + " - "
				+ telefone + " - "
				+ new SimpleDateFormat("dd/MM/yyyy").format(aniversario);
	}

	@Override
	public int compareTo(Contato outroContato) {
		return this.nome.compareTo(((Contato) outroContato).getNome());
	}

}
