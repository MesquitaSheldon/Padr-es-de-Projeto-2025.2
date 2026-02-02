package br.ifba.edu.br.questao2;

import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;

public class ProtectDoc implements Command{
    private GerenciadorDocumentoModel model;
    private Documento atual;
    private Documento protegido;

    
    public ProtectDoc(GerenciadorDocumentoModel model, Documento atual) {
    	this.model = model;
    	this.atual = atual;
    }


	@Override
	public void execute() {
		Documento docNoModel = this.model.getDocumentoAtual();
        if (docNoModel != null) {
            this.atual = docNoModel;
        }

		try {
			this.protegido = this.model.protegerDocumento(this.atual);
		} catch(Exception e) {
			//
		}
	}


	@Override
	public void undo() {
		if (this.protegido != null && this.atual != null) {
            this.model.atualizarRepositorio(this.protegido, this.atual);
            
            this.model.setDocumentoAtual(this.atual);
        }		
	}

	@Override
	public Command clone() {
		return new ProtectDoc(model, atual);
	}
	
	@Override
	public String getLogDescription() {
		if (protegido != null) {
	        return "Proteger: Documento " + protegido.getNumero() + 
	               " | Status: Protegido | Nível de Privacidade: " + protegido.getPrivacidade();
	    }
	    
	    if (atual != null) {
	        return "Tentativa de Proteger: Documento " + atual.getNumero();
	    }
	    
	    return "Erro: Documento inexistente para proteção";
	}
}
