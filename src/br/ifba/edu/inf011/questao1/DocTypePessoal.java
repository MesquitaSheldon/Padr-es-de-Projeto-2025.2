package br.ifba.edu.inf011.questao1;

import java.time.LocalDate;

import br.ifba.edu.inf011.model.documentos.Documento;

public class DocTypePessoal implements NumberRule {

	@Override
	public String execute(Documento doc) {
		doc.setNumero("PES-" + LocalDate.now().getDayOfYear() + "-" + doc.getProprietario().hashCode());
		return doc.getNumero();

	}

}
