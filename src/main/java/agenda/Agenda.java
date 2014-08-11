package agenda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import agenda.contatos.Contato;

@SuppressWarnings("serial")
public class Agenda implements Serializable {

	private String nome = "Nova agenda";
	private ArrayList<Contato> contatos;
	private int idCount = 1;

	public Agenda() {
		this.contatos = new ArrayList<Contato>(0);
	}
	
	public Agenda(String nome) {
		this.contatos = new ArrayList<Contato>(0);
		this.nome = nome;
	}

	public void insereContato(Contato contato) throws IllegalArgumentException {
		try {
			contato.setId(idCount);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Contato não pode ser null");
		}

		idCount++;
		this.contatos.add(contato);
		Collections.sort(contatos);
	}

	public void removeContato(int id) throws IllegalArgumentException {
		Contato contato = pesquisa(id);
		contatos.remove(contato);

	}

	private Contato pesquisa(int id) throws IllegalArgumentException {
		for (Contato contato : contatos) {
			if (contato.getId() == id) {
				return contato;
			}
		}
		throw new IllegalArgumentException("Id de contato não encontrada");
	}

	@Override
	public String toString() {
		String a = "";
		for (Contato contato : contatos) {
			a += contato.toString() + "\n";
		}
		return a;
	}

	public ArrayList<Contato> getContatos() {
		return this.contatos;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
