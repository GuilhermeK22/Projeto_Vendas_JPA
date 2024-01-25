package main.br.com.guikapp.dao.jpa;

import main.br.com.guikapp.dao.generic.jpa.GenericJpaDAO;
import main.br.com.guikapp.domain.jpa.VendaJpa;
import main.br.com.guikapp.exceptions.DAOException;
import main.br.com.guikapp.exceptions.TipoChaveNaoEncontradaException;

public class VendaExclusaoJpaDAO extends GenericJpaDAO<VendaJpa, Long> implements IVendaJpaDAO {
    public VendaExclusaoJpaDAO() {
        super(VendaJpa.class);
    }
    @Override
    public void finalizarVenda(VendaJpa venda) throws TipoChaveNaoEncontradaException, DAOException {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");

    }

    @Override
    public void cancelarVenda(VendaJpa venda) throws TipoChaveNaoEncontradaException, DAOException {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");

    }

    @Override
    public VendaJpa consultarComCollection(Long id) {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }
}
