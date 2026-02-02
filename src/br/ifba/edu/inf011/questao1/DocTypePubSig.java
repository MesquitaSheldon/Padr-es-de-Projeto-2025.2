package br.ifba.edu.inf011.questao1;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;

public class DocTypePubSig implements NumberRule {

	@Override
	public String execute(Documento doc) {
		if (doc.getPrivacidade() == Privacidade.SIGILOSO) 
			doc.setNumero("SECURE-" + doc.getNumero().hashCode());
		else
			doc.setNumero("PUB-" + doc.hashCode());
		return doc.getNumero();
		}
	}
