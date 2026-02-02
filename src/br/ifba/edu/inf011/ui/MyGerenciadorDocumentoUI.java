package br.ifba.edu.inf011.ui;

import javax.swing.JOptionPane;

import br.ifba.edu.br.questao2.AssignCommand;
import br.ifba.edu.br.questao2.CreateDoc;
import br.ifba.edu.br.questao2.ProtectDoc;
import br.ifba.edu.br.questao2.SalvarDoc;
import br.ifba.edu.br.questao2.UrgentDoc;
import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.documentos.Privacidade;

public class MyGerenciadorDocumentoUI extends AbstractGerenciadorDocumentosUI{
	
	 
	 public MyGerenciadorDocumentoUI(DocumentOperatorFactory factory) {
		super(factory);
	}

	protected JPanelOperacoes montarMenuOperacoes() {
		JPanelOperacoes comandos = new JPanelOperacoes();
		comandos.addOperacao("➕ Criar Publico", e -> this.criarDocumentoPublico());
		comandos.addOperacao("➕ Criar Privado", e -> this.criarDocumentoPrivado());
		comandos.addOperacao("💾 Salvar", e-> this.salvarConteudo());
		comandos.addOperacao("🔑 Proteger", e->this.protegerDocumento());
		comandos.addOperacao("✍️ Assinar", e->this.assinarDocumento());
		comandos.addOperacao("⏰ Urgente", e->this.tornarUrgente());
		comandos.addOperacao("🔴 Gravar/Parar Macro", e -> this.gerenciarMacro());
		comandos.addOperacao("▶ Executar Macro Gravada", e -> this.rodarMacroGravada());
		comandos.addOperacao("⤾ Undo", e->this.undo());
		comandos.addOperacao("⤿ Redo", e->this.reDo());
		comandos.addOperacao("⬇ Consolidar", e->this.consolidar());
		return comandos;
	 }	
	
	protected void gerenciarMacro() {
		if (this.commandManager.isRecording()) {
			this.commandManager.pararGravacao();
			JOptionPane.showMessageDialog(this, "Macro gravada com sucesso! \nEla foi adicionada à pilha de desfazer.");
		} else {
			this.commandManager.iniciarGravacao();
			JOptionPane.showMessageDialog(this, "Gravação iniciada!\nTodas as operações agora farão parte da macro.\nClique no botão novamente para parar.");
		}
	}
	
	protected void rodarMacroGravada() {
        try {
            this.commandManager.executarUltimaMacro();
            this.atual = this.controller.getDocumentoAtual();
            this.atualizarListaDocs();
            this.refreshUI();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao rodar macro: " + e.getMessage());
        }
    }	
	
	protected void consolidar() {
		this.commandManager.consolidar();
		JOptionPane.showMessageDialog(this, "Alterações consolidadas com sucesso!");
		this.atualizarListaDocs();
		this.refreshUI();
	}
	
	protected void reDo() {
		this.commandManager.redo();
		this.atual = this.controller.getDocumentoAtual();
		this.atualizarListaDocs();
		this.refreshUI();
	}
	
	protected void undo() {
		this.commandManager.undo();
		this.atual = this.controller.getDocumentoAtual();
		this.atualizarListaDocs();
		this.refreshUI();
	}
	
	protected void criarDocumentoPublico() {
		this.criarDocumento(Privacidade.PUBLICO);
	}
	
	protected void criarDocumentoPrivado() {
		this.criarDocumento(Privacidade.SIGILOSO);
	}
	
	protected void salvarConteudo() {
	    try {
	        if (this.atual == null) {
	            JOptionPane.showMessageDialog(this, "Nenhum documento para salvar!");
	            return;
	        }

	        SalvarDoc command = new SalvarDoc(
	                this.controller,
	                this.atual,
	                this.areaEdicao.getConteudo()
	        );
	        
	        this.commandManager.executeCommand(command);
	        
	        this.atual = this.controller.getDocumentoAtual();
	        this.atualizarListaDocs();
	        this.refreshUI();
	        
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(this, "Erro ao Salvar: " + e.getMessage());
	    }
	}
	
	protected void protegerDocumento() {
		try {
			ProtectDoc cmd = new ProtectDoc(
					this.controller,
					this.atual);
			this.commandManager.executeCommand(cmd);
			this.atual = this.controller.getDocumentoAtual();
			this.atualizarListaDocs();
			this.refreshUI();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao proteger: " + e.getMessage());
		}
	}

	protected void assinarDocumento() {
		try {
			AssignCommand cmd = new AssignCommand(
					this.controller,
					this.atual);
			this.commandManager.executeCommand(cmd);
			this.atual = this.controller.getDocumentoAtual();
			this.atualizarListaDocs();
			this.refreshUI();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao assinar: " + e.getMessage());
		}		
	}
	
	protected void tornarUrgente() {
		try {
			UrgentDoc cmd = new UrgentDoc(
					this.controller,
					this.atual);
			this.commandManager.executeCommand(cmd);
			this.atual = this.controller.getDocumentoAtual();
			this.atualizarListaDocs();
			this.refreshUI();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao tornar urgente: " + e.getMessage());
		}		
	}	

	private void criarDocumento(Privacidade privacidade) {
		int tipoIndex = this.barraSuperior.getTipoSelecionadoIndice();
		CreateDoc command = new CreateDoc(
				this.controller,
				tipoIndex,
				privacidade);
		this.commandManager.executeCommand(command);
		
		this.atual = command.getDocumentoCriado();
		this.atualizarListaDocs();
		this.refreshUI();
    }	

}

