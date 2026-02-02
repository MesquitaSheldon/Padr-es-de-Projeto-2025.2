package br.ifba.edu.br.questao2;

import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;

public class AssignCommand implements Command {
    private GerenciadorDocumentoModel model;
    private Documento atual;
    private Documento assinado;

    public AssignCommand(GerenciadorDocumentoModel model, Documento atual){
        this.model = model;
        this.atual = atual;
    }

    @Override
    public void execute() {
        try {
            Documento docNoModel = this.model.getDocumentoAtual();
            if (docNoModel != null) {
                this.atual = docNoModel;
            }
            if (this.atual != null) {
                this.assinado = this.model.assinarDocumento(this.atual);
            }
        } catch(Exception e) {
            LogArquivo.salvar("Erro ao executar Assinar: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
    	if (this.assinado != null && this.atual != null) {
            this.model.atualizarRepositorio(this.assinado, this.atual);
            
            this.model.setDocumentoAtual(this.atual); 
        }
    }
    
    @Override
    public Command clone() {
    	return new AssignCommand(model, atual);
    }
    
	@Override
	public String getLogDescription() {
		if (atual != null)
			return "Assinado documento: " + 
	               atual.getNumero();
		return "Tentativa de Assinar falhou";
	}

}