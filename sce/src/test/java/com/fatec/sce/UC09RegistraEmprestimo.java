package com.fatec.sce;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import com.fatec.sce.model.Emprestimo;
import com.fatec.sce.model.Livro;
import com.fatec.sce.model.Usuario;
import com.fatec.sce.servico.ServicoEmprestimo;

public class UC09RegistraEmprestimo {
	static private Livro livro;
	static private Usuario usuario;
	static private ServicoEmprestimo servico;

	@Test
	public void CT01RegistraEmprestimoDeLivro_com_sucesso() {
		// cenario
		livro = new Livro();
		livro.setIsbn("121212");
		livro.setTitulo("Engenharia de Software");
		livro.setAutor("Pressman");
		usuario = new Usuario();
		usuario.setRa("1111");
		usuario.setNome("Jose da Silva");
		servico = new ServicoEmprestimo();
		// acao
		Emprestimo resultadoEsperado = servico.empresta(livro, usuario);
		// verificação
		assertNotNull(resultadoEsperado);
	}

	@Test
	public void CT02RegistraEmprestimoDeLivro_com_sucesso() {
		// cenario
		Emprestimo emprestimo = null;
		// acao
		emprestimo = ObtemEmprestimo.comDadosValidos();
		// verificação
		assertNotNull(emprestimo);
	}

	@Test
	public void CT03_quando_registra_emprestimo_data_atual_mais_oito_dias() {
		// cenario
		DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY/MM/dd");
		String dataEsperada = new DateTime().plusDays(8).toString(fmt);
		// acao
		Emprestimo emprestimo = ObtemEmprestimo.comDadosValidos();
		String dataObtida = emprestimo.getDataDevolucao();
		// verificacao
		assertTrue(dataEsperada.equals(dataObtida));
	}

	/**
	 * Objetivo - verificar o comportamento do metodo ehDomigo() para uma data com
	 * formato valido dia invalido (domingo).
	 */
	@Test
	public void CT06se_data_devolucao_for_domingo_retorna_true() {
		// cenario
		String data = "2018/09/02"; // domingo
		// acao
		Emprestimo umEmprestimo = new Emprestimo();
		// verificacao
		assertTrue(umEmprestimo.ehDomingo(data));
	}

	@Test
	public void CT07_quando_entrega_atrasado_quant_dias_negativo() {
		// cenario
		Emprestimo umEmprestimo = ObtemEmprestimo.comDataDeDevolucaoVencida();
		ServicoEmprestimo servico = new ServicoEmprestimo();
		// acao
		int quantDias = servico.devolucao(umEmprestimo);
		// verificacao
		assertTrue(quantDias < 0); // quant de dias entre a data de emprestimo e a de devolucao
	}
	@Test
	public void CT08_quando_entrega_no_mesmo_dia() {
		// cenario
		Emprestimo umEmprestimo = ObtemEmprestimo.comDataDeDevolucaoIgual_DataDeEmprestimo();
		ServicoEmprestimo servico = new ServicoEmprestimo();
		// acao
		int quantDias = servico.devolucao(umEmprestimo);
		// verificacao
		assertTrue(quantDias == 0); // quant de dias entre a data de emprestimo e a de devolucao
	}
}