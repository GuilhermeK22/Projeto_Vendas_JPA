package dao.jpa;

import dao.generic.jpa.IGenericJpaDAO;
import domain.jpa.VendaJpa;
import exceptions.DAOException;
import exceptions.TipoChaveNaoEncontradaException;

public interface IVendaJpaDAO extends IGenericJpaDAO<VendaJpa, Long> {
    public void finalizarVenda(VendaJpa venda) throws TipoChaveNaoEncontradaException, DAOException;
    public void cancelarVenda(VendaJpa venda) throws TipoChaveNaoEncontradaException, DAOException;
    public VendaJpa consultarComCollection(Long id);
}
