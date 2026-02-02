package br.ifba.edu.br.questao2;

import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;

public class SalvarDoc implements Command {
	
	private GerenciadorDocumentoModel model;
	private Documento atual;
	private String conteudoNovo;
	private String conteudoAntigo;
	
	public SalvarDoc(GerenciadorDocumentoModel model, Documento atual, String novoConteudo){
		this.model = model;
		this.atual = atual;
		this.conteudoNovo = novoConteudo;
		
		if (this.atual != null) {
			try {
				this.conteudoAntigo = this.atual.getConteudo();
			} catch (Exception e) {
				this.conteudoAntigo = "";
			}
		}
	}
	
	@Override
	public void execute() {
	    try {
	        Documento docAtualNoModel = this.model.getDocumentoAtual();
	        if (docAtualNoModel != null) {
	            this.atual = docAtualNoModel;
	        }

	        if (this.atual != null) {
	            this.model.salvarDocumento(this.atual, this.conteudoNovo);
	        }
	    } catch(Exception e) {
	        //
	    }
	}

	@Override
	public void undo() {
		try {
            if (this.atual != null) {
				this.model.salvarDocumento(this.atual, this.conteudoAntigo);
                
                this.model.setDocumentoAtual(this.atual);
			}
      } catch (Exception e) {
			System.err.println("Erro ao desfazer Salvar: " + e.getMessage());
		}
	}

	@Override
	public Command clone() {
		return new SalvarDoc(this.model, this.atual, this.conteudoNovo);
	}
	
	@Override
	public String getLogDescription() {
		if (atual != null)
			return "Salvar: " + 
	               atual.getNumero() +
	               " | Tamanho: " + (conteudoNovo != null ? conteudoNovo.length() : 0) + " caracteres";
		return "Tentativa de Salvar falhou (Documento inexistente)";
	}

}