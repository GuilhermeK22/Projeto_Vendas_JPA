package main.br.com.guikapp.dao.jpa;

import main.br.com.guikapp.dao.generic.jpa.IGenericJpaDAO;
import main.br.com.guikapp.domain.jpa.VendaJpa;
import main.br.com.guikapp.exceptions.DAOException;
import main.br.com.guikapp.exceptions.TipoChaveNaoEncontradaException;

public interface IVendaJpaDAO extends IGenericJpaDAO<VendaJpa, Long> {
    public void finalizarVenda(VendaJpa venda) throws TipoChaveNaoEncontradaException, DAOException;
    public void cancelarVenda(VendaJpa venda) throws TipoChaveNaoEncontradaException, DAOException;
    public VendaJpa consultarComCollection(Long id);
}
