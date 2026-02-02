package br.ifba.edu.br.questao2;

import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;

public class CreateDoc implements Command {

    private GerenciadorDocumentoModel model;
    private int tipoIndex;
    private Privacidade privacidade;
    private Documento documentoCriado;

    public CreateDoc(GerenciadorDocumentoModel model, int tipoIndex, Privacidade privacidade) {
        this.model = model;
        this.tipoIndex = tipoIndex;
        this.privacidade = privacidade;
    }

    @Override
    public void execute() {
        try {
            if (this.documentoCriado == null) {
                this.documentoCriado = this.model.criarDocumento(this.tipoIndex, this.privacidade);
            } else {
                this.model.reinserirDocumento(this.documentoCriado);
            }
        } catch (FWDocumentException e) {
        	//
        }
    }

    @Override
    public void undo() {
    	if (this.documentoCriado != null) {
            this.model.removerDocumento(this.documentoCriado);
            
            this.model.setDocumentoAtual(null); 
        }
    }

    @Override
    public Command clone() {
    	return new CreateDoc(model, tipoIndex, privacidade);
    }
    
    @Override
    public String getLogDescription() {
        return "Criar Documento: " + 
               (documentoCriado != null ? documentoCriado.getNumero() : "Novo");
    }
    
    public Documento getDocumentoCriado() {
        return this.documentoCriado;
    }
}