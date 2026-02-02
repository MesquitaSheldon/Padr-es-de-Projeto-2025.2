package br.ifba.edu.inf011.questao1;

import java.time.LocalDate;

import br.ifba.edu.inf011.model.documentos.Documento;

public class DocTypeCriminal implements NumberRule {

	@Override
	public String execute(Documento doc) {
		doc.setNumero( "CRI-" + LocalDate.now().getYear() + "-" + doc.hashCode());
		return doc.getNumero();
	}
}
