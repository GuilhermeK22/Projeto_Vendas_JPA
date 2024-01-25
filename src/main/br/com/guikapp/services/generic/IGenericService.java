package main.br.com.guikapp.services.generic;

import main.br.com.guikapp.dao.Persistente;
import main.br.com.guikapp.exceptions.DAOException;
import main.br.com.guikapp.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericService <T extends Persistente, E extends Serializable> {
    public Boolean cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException;
    public void excluir(E valor) throws DAOException;
    public void alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException;
    public T consultar(E valor) throws DAOException;
    public Collection<T> buscarTodos() throws DAOException;
}
