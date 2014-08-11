package agenda.ui;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import agenda.contatos.Cliente;
import agenda.contatos.Contato;
import agenda.contatos.Fornecedor;

@SuppressWarnings("serial")
public class ContatosTableModel extends AbstractTableModel {

	List<Contato> contatos;

	public ContatosTableModel(List<Contato> contatos) {
		this.contatos = contatos;
	}

	@Override
	public int getRowCount() {
		return contatos.size();
	}

	@Override
	public int getColumnCount() {
		return 9;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Contato contato = contatos.get(rowIndex);
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		switch (columnIndex) {
		case 0:
			return contato.getId();
		case 1:
			return contato.getNome();
		case 2:
			return contato.getEmail();
		case 3:
			return contato.getTelefone();
		case 4:
			return contato.getEndereco();
		case 5:
			try {
				return formater.format(contato.getAniversario());
			} catch (NullPointerException e){
				e.printStackTrace();
			}
			
		case 6:
			if (contato instanceof Cliente) {
				try {
					return formater.format(((Cliente) contato).getUltimaCompra());
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
			break;
		case 7:
			if (contato instanceof Cliente) {
				return ((Cliente) contato).getFidelidade();
			}
			break;
		case 8:
			if (contato instanceof Fornecedor) {
				return ((Fornecedor) contato).getQualidade();
			}
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "ID";
		case 1:
			return "Nome";
		case 2:
			return "Email";
		case 3:
			return "Telefone";
		case 4:
			return "Endereço";
		case 5:
			return "Aniversário";
		case 6:
			return "Última Compra";
		case 7:
			return "Fidelidade";
		case 8:
			return "Qualidade";
		}
		return null;
	}

}
