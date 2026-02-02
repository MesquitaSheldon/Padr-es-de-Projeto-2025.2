package br.ifba.edu.inf011.questao1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;


// O autenticador utiliza uma lista ligada para selecionar a Strategy de acordo com o index passado pelo usuário através da UI
// Ou seja, a lista ligada gerada padrão já tem todas as estratégias padrão do software, porém cada cliente pode adicionar ou excluir conforme
// a vontade, utilizando as funções publicas addRule, removeRule ou removeIndexedRule
public class AutenticadorQ1 {
	
	private final List<NumberRule> rules;
	
	public AutenticadorQ1() {
		rules = new LinkedList<>();
		rules.add(new DocTypeCriminal());
		rules.add(new DocTypePessoal());
		rules.add(new DocTypePubSig());
		rules.add(new DocTypeDocumento());
	}
	
	public NumberRule addRule(NumberRule g) {
		if(rules.add(g))
			return g;
		return null;
	}
	
	public NumberRule removeRule() {
		NumberRule g = rules.getLast();
		if(g == null)
			return rules.removeLast();
		return null;
	}
	
	public NumberRule removeIndexedRule(int index) {
		NumberRule g = rules.get(index);
		if (g != null)
			return rules.remove(index);
		return null;
	}
	
	public boolean removeRule(NumberRule rule) {
		return rules.remove(rule);
	}
	
	public String autenticar(Integer tipo, Documento documento) {
		if(tipo > -1 && tipo < rules.size())
			return rules.get(tipo).execute(documento);
		return null;
	}
	
}
