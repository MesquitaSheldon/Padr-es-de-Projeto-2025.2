package br.ifba.edu.br.questao2;

import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;

public class UrgentDoc implements Command {
	
    private GerenciadorDocumentoModel model;
    private Documento atual;
    private Documento urgente;
    
    public UrgentDoc(GerenciadorDocumentoModel model, Documento atual) {
    	this.model = model;
    	this.atual = atual;
    }
    
	@Override
	public void execute() {
		Documento docNoModel = this.model.getDocumentoAtual();
        if (docNoModel != null) {
            this.atual = docNoModel;
        }

		try{
			urgente = this.model.tornarUrgente(this.atual);
		} catch (Exception e) {
			//
		}
	}

	@Override
	public Command clone() {
		return new UrgentDoc(model, atual);
	}
	
	@Override
	public void undo() {
		if (this.urgente != null && this.atual != null) {
            this.model.atualizarRepositorio(this.urgente, this.atual);

            this.model.setDocumentoAtual(this.atual);
        }	
	}

	public String getLogDescription() {
	    if (this.urgente != null) {
	        return "Prioridade: Documento " + urgente.getNumero() + 
	               " | Status: DEFINIDO COMO URGENTE" +
	               " | Origem: " + urgente.getClass().getSimpleName();
	    }
	    
	    if (this.atual != null) {
	        return "Tentativa de Urgência falhou: Documento " + atual.getNumero();
	    }
	    
	    return "Erro: Nenhum documento selecionado para Urgência";
	}
}
