package main.br.com.guikapp.dao.generic.jpa;

import main.br.com.guikapp.dao.Persistente;
import main.br.com.guikapp.exceptions.DAOException;
import main.br.com.guikapp.exceptions.MaisDeUmRegistroException;
import main.br.com.guikapp.exceptions.TableException;
import main.br.com.guikapp.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericJpaDAO <T extends Persistente, E extends Serializable> {
    public T cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException;
    public void excluir(T entity) throws DAOException;
    public T alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException;
    public T consultar(E id) throws MaisDeUmRegistroException, TableException, DAOException;
    public Collection<T> buscarTodos() throws DAOException;

}
