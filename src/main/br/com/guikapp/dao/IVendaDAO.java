package main.br.com.guikapp.dao;

import main.br.com.guikapp.dao.generic.IGenericDAO;
import main.br.com.guikapp.domain.Venda;
import main.br.com.guikapp.exceptions.DAOException;
import main.br.com.guikapp.exceptions.TipoChaveNaoEncontradaException;

public interface IVendaDAO extends IGenericDAO<Venda, String> {
    public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;

    public void cancelarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;
}
