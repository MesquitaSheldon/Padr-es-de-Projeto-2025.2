package br.ifba.edu.br.questao2;

public interface Command {
	void execute();
    void undo();
    Command clone();
    String getLogDescription();
}

