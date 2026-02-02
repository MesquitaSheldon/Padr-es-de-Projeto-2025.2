package br.ifba.edu.br.questao2;

import java.util.Stack;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommandManager {
    
    private boolean recording = false;
    private MacroCommand macroAtual;
    private MacroCommand ultimaMacroGravada;
    
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();
    
    public CommandManager() {
    	this.registrarLog("LOG: Aplicação inicializada com sucesso");
    }
    
    public void executeCommand(Command cmd) {
        cmd.execute();
        
        if (recording) {
            if (macroAtual != null) {
                macroAtual.add(cmd);
                this.registrarLog("LOG: [REC] Comando adicionado à macro: " + cmd.getLogDescription());
            }
        } else {
            undoStack.push(cmd);
            redoStack.clear(); 
            this.registrarLog("LOG: " + cmd.getLogDescription()); 
        }
    }

    public boolean isRecording() {
    	return recording;
    }
    
    public void iniciarGravacao() {
        this.recording = true;
        this.macroAtual = new MacroCommand("Macro Personalizada " + LocalDateTime.now());
        this.registrarLog("LOG: === Gravação de Macro Iniciada ===");
    }
    
    public void pararGravacao() {
        this.recording = false;
        if (macroAtual != null) {
            this.ultimaMacroGravada = macroAtual;
            
            undoStack.push(macroAtual);
            redoStack.clear();
            
            this.registrarLog("LOG: === Gravação Finalizada. Macro salva no histórico. ===");
            this.macroAtual = null;
        }
    }
    
    public void executarUltimaMacro() {
        if (this.ultimaMacroGravada != null) {
            this.executeCommand(this.ultimaMacroGravada);
        }
    }
    
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
            this.registrarLog("LOG: Desfeito - " + cmd.getLogDescription());
        } else {
        	this.registrarLog("LOG: Desfeito não realizado");
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
            this.registrarLog("LOG: Refeito - " + cmd.getLogDescription());
        } else {
        	this.registrarLog("LOG: Refeito não realizado");
        }
    }
    
    public void consolidar() {
    	this.redoStack.clear();
    	this.undoStack.clear();
   		this.registrarLog("LOG: Consolidado (histórico Limpo)");
    }
    
    private void registrarLog(String mensagem) {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String timestamp = agora.format(formatter);
        LogArquivo.salvar("[" + timestamp + "] " + mensagem);
    }
}