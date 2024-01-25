package jpa;

import main.br.com.guikapp.dao.jpa.IProdutoJpaDAO;
import main.br.com.guikapp.dao.jpa.ProdutoJpaDAO;
import main.br.com.guikapp.domain.jpa.ProdutoJpa;
import main.br.com.guikapp.exceptions.DAOException;
import main.br.com.guikapp.exceptions.MaisDeUmRegistroException;
import main.br.com.guikapp.exceptions.TableException;
import main.br.com.guikapp.exceptions.TipoChaveNaoEncontradaException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.Assert.*;

public class ProdutoJpaDAOTest {
    private IProdutoJpaDAO produtoDAO;
    public ProdutoJpaDAOTest() {
        this.produtoDAO = new ProdutoJpaDAO();
    }
    @After
    public void end() throws DAOException {
        Collection<ProdutoJpa> list = produtoDAO.buscarTodos();
        list.forEach(cli -> {
            try {
                produtoDAO.excluir(cli);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });
    }
    @Test
    public void pesquisar() throws MaisDeUmRegistroException, TableException, DAOException, TipoChaveNaoEncontradaException {
        ProdutoJpa produto = criarProduto("A1");
        assertNotNull(produto);
        ProdutoJpa produtoDB = this.produtoDAO.consultar(produto.getId());
        assertNotNull(produtoDB);
    }

    @Test
    public void salvar() throws TipoChaveNaoEncontradaException, DAOException {
        ProdutoJpa produto = criarProduto("A2");
        assertNotNull(produto);
    }

    @Test
    public void excluir() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        ProdutoJpa produto = criarProduto("A3");
        assertNotNull(produto);
        this.produtoDAO.excluir(produto);
        ProdutoJpa produtoBD = this.produtoDAO.consultar(produto.getId());
        assertNull(produtoBD);
    }

    @Test
    public void alterarCliente() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException {
        ProdutoJpa produto = criarProduto("A4");
        produto.setNome("Rodrigo Pires");
        produtoDAO.alterar(produto);
        ProdutoJpa produtoBD = this.produtoDAO.consultar(produto.getId());
        assertNotNull(produtoBD);
        Assert.assertEquals("Rodrigo Pires", produtoBD.getNome());
    }

    @Test
    public void buscarTodos() throws DAOException, TipoChaveNaoEncontradaException {
        criarProduto("A5");
        criarProduto("A6");
        Collection<ProdutoJpa> list = produtoDAO.buscarTodos();
        assertTrue(list != null);
        assertTrue(list.size() == 2);

        for (ProdutoJpa prod : list) {
            this.produtoDAO.excluir(prod);
        }

        list = produtoDAO.buscarTodos();
        assertTrue(list != null);
        assertTrue(list.size() == 0);

    }

    private ProdutoJpa criarProduto(String codigo) throws TipoChaveNaoEncontradaException, DAOException {
        ProdutoJpa produto = new ProdutoJpa();
        produto.setCodigo(codigo);
        produto.setDescricao("Produto 1");
        produto.setNome("Produto 1");
        produto.setValor(BigDecimal.TEN);
        produtoDAO.cadastrar(produto);
        return produto;
    }
}
