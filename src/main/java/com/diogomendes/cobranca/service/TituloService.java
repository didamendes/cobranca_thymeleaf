package com.diogomendes.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.diogomendes.cobranca.model.StatusTitulo;
import com.diogomendes.cobranca.model.Titulo;
import com.diogomendes.cobranca.repository.TituloRepository;
import com.diogomendes.cobranca.repository.filter.TituloFilter;

@Service
public class TituloService {

	@Autowired
	private TituloRepository tituloRepository;

	public void salvar(Titulo titulo) {
		try {
			tituloRepository.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de dados errado ! ");
		}	
	}

	public void excluir(Long codigo) {
		tituloRepository.delete(codigo);	
	}

	public String receber(Long codigo) {
		Titulo titulo = tituloRepository.findOne(codigo);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		tituloRepository.save(titulo);
		
		return StatusTitulo.RECEBIDO.getDescricao();
	}

	public List<Titulo> filtrar(TituloFilter filtro) {
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		
		return  tituloRepository.findByDescricaoContaining(descricao);
	}
	
	
	
}
