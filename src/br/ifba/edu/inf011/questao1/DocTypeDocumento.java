package br.ifba.edu.inf011.questao1;

import br.ifba.edu.inf011.model.documentos.Documento;

public class DocTypeDocumento implements NumberRule {

	@Override
	public String execute(Documento doc) {
		doc.setNumero("DOC-" + System.currentTimeMillis());
		return doc.getNumero();
	}

}
