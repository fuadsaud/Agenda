package agenda.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import agenda.Agenda;

public class AgendaFileManager {

	public Agenda abrir() {
		Agenda agenda = null;
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileNameExtensionFilter(
					"Agenda de contatos", "agenda"));

			int result = fileChooser.showOpenDialog(null);

			if (result == JFileChooser.APPROVE_OPTION) {
				InputStream file = new FileInputStream(
						fileChooser.getSelectedFile());
				ObjectInputStream os = new ObjectInputStream(file);
				agenda = (Agenda) os.readObject();
				agenda.setNome(fileChooser.getSelectedFile().getName());
				os.close();
				return agenda;
			}
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean salvar(Agenda agenda) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter(
				"Agenda de contatos", "agenda"));

		int result = fileChooser.showSaveDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fileChooser.getSelectedFile();
				System.out.println(file.getName());
				if (!file.getName().endsWith(".agenda")) {
					file = new File(file.getPath() + ".agenda");
				}
				OutputStream output = new FileOutputStream(file);
				ObjectOutputStream os = new ObjectOutputStream(output);
				os.writeObject(agenda);
				agenda.setNome(fileChooser.getSelectedFile().getName());
				os.close();
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
