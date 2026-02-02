package br.ifba.edu.br.questao2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MacroCommand implements Command {

    private List<Command> templates = new ArrayList<>(); 
    private List<Command> executados = new ArrayList<>();
    
    private String descricao;

    public MacroCommand(String descricao) {
        this.descricao = descricao;
    }

    public void add(Command cmd) {
        this.templates.add(cmd);
    }

    @Override
    public void execute() {
        this.executados.clear();
        
        for (Command template : this.templates) {
            Command cmdReal = template.clone();
            
            cmdReal.execute();
            this.executados.add(cmdReal);
        }
    }

    @Override
    public void undo() {
        List<Command> reverso = new ArrayList<>(this.executados);
        Collections.reverse(reverso);
        for (Command cmd : reverso) {
            cmd.undo();
        }
    }

    @Override
    public Command clone() {
        MacroCommand copy = new MacroCommand(this.descricao);
        for(Command cmd : this.templates) {
            copy.add(cmd.clone());
        }
        return copy;
    }

    @Override
    public String getLogDescription() {
        return "Macro: " + descricao + " (" + templates.size() + " passos)";
    }
}