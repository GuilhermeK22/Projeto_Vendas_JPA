package jpa;

import dao.jpa.*;
import domain.Venda;
import domain.jpa.ClienteJpa;
import domain.jpa.ProdutoJpa;
import domain.jpa.VendaJpa;
import exceptions.DAOException;
import exceptions.MaisDeUmRegistroException;
import exceptions.TableException;
import exceptions.TipoChaveNaoEncontradaException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Collection;
import java.util.Random;

import static org.junit.Assert.*;

public class VendaJpaDAOTest {
    private IVendaJpaDAO vendaDAO;
    private IVendaJpaDAO vendaExclusaoDAO;
    private IClienteJpaDAO clienteDAO;
    private IProdutoJpaDAO produtoDAO;
    private Random rd;
    private ClienteJpa cliente;
    private ProdutoJpa produto;
    public VendaJpaDAOTest() {
        this.vendaDAO = new VendaJpaDAO();
        vendaExclusaoDAO = new VendaExclusaoJpaDAO();
        this.clienteDAO = new ClienteJpaDAO();
        this.produtoDAO = new ProdutoJpaDAO();
        rd = new Random();
    }
    @Before
    public void init() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        this.cliente = cadastrarCliente();
        this.produto = cadastrarProduto("A1", BigDecimal.TEN);
    }
    @After
    public void end() throws DAOException {
        excluirVendas();
        excluirProdutos();
        clienteDAO.excluir(this.cliente);
    }
    @Test
    public void pesquisar() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        VendaJpa venda = criarVenda("A1");
        VendaJpa retorno = vendaDAO.cadastrar(venda);
        assertNotNull(retorno);
        VendaJpa vendaConsultada = vendaDAO.consultar(venda.getId());
        assertNotNull(vendaConsultada);
        assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
    }
    @Test
    public void salvar() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException {
        VendaJpa venda = criarVenda("A2");
        VendaJpa retorno = vendaDAO.cadastrar(venda);
        assertNotNull(retorno);

        assertTrue(venda.getValorTotal().equals(BigDecimal.valueOf(20)));
        assertTrue(venda.getStatus().equals(Venda.Status.INICIADA));

        VendaJpa vendaConsultada = vendaDAO.consultar(venda.getId());
        assertTrue(vendaConsultada.getId() != null);
        assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
    }
    @Test
    public void cancelarVenda() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        String codigoVenda = "A3";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDAO.cadastrar(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        retorno.setStatus(VendaJpa.Status.CANCELADA);
        vendaDAO.cancelarVenda(venda);

        VendaJpa vendaConsultada = vendaDAO.consultar(venda.getId());
        assertEquals(codigoVenda, vendaConsultada.getCodigo());
        assertEquals(VendaJpa.Status.CANCELADA, vendaConsultada.getStatus());
    }
    @Test
    public void adicionarMaisProdutosDoMesmo() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        String codigoVenda = "A4";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDAO.cadastrar(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        VendaJpa vendaConsultada = vendaDAO.consultarComCollection(venda.getId());
        vendaConsultada.adicionarProduto(produto, 1);

        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
        BigDecimal valorTotal = BigDecimal.valueOf(30).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
        assertTrue(vendaConsultada.getStatus().equals(VendaJpa.Status.INICIADA));
    }
    @Test
    public void adicionarMaisProdutosDiferentes() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        String codigoVenda = "A5";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDAO.cadastrar(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        ProdutoJpa prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCodigo());

        //TODO Usando este método pra evitar a exception org.hibernate.LazyInitializationException
        // Ele busca todos os dados da lista pois a mesma por default é lazy

        VendaJpa vendaConsultada = vendaDAO.consultarComCollection(venda.getId());
        vendaConsultada.adicionarProduto(prod, 1);

        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
        assertTrue(vendaConsultada.getStatus().equals(Venda.Status.INICIADA));
    }

    @Test(expected = DAOException.class)
    public void salvarVendaMesmoCodigoExistente() throws TipoChaveNaoEncontradaException, DAOException {
        VendaJpa venda = criarVenda("A6");
        VendaJpa retorno = vendaDAO.cadastrar(venda);
        assertNotNull(retorno);

        VendaJpa venda1 = criarVenda("A6");
        VendaJpa retorno1 = vendaDAO.cadastrar(venda1);
        assertNull(retorno1);
        assertTrue(venda.getStatus().equals(Venda.Status.INICIADA));
    }
    @Test
    public void removerProduto() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        String codigoVenda = "A7";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDAO.cadastrar(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        ProdutoJpa prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCodigo());

        VendaJpa vendaConsultada = vendaDAO.consultarComCollection(venda.getId());
        vendaConsultada.adicionarProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));


        vendaConsultada.removerProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 2);
        valorTotal = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
        assertTrue(vendaConsultada.getStatus().equals(VendaJpa.Status.INICIADA));
    }
    @Test
    public void removerApenasUmProduto() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        String codigoVenda = "A8";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDAO.cadastrar(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        ProdutoJpa prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCodigo());

        VendaJpa vendaConsultada = vendaDAO.consultarComCollection(venda.getId());
        vendaConsultada.adicionarProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));


        vendaConsultada.removerProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 2);
        valorTotal = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
        assertTrue(vendaConsultada.getStatus().equals(VendaJpa.Status.INICIADA));
    }
    @Test
    public void removerTodosProdutos() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        String codigoVenda = "A9";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDAO.cadastrar(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        ProdutoJpa prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCodigo());

        VendaJpa vendaConsultada = vendaDAO.consultarComCollection(venda.getId());
        vendaConsultada.adicionarProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));


        vendaConsultada.removerTodosProdutos();
        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 0);
        assertTrue(vendaConsultada.getValorTotal().equals(BigDecimal.valueOf(0)));
        assertTrue(vendaConsultada.getStatus().equals(VendaJpa.Status.INICIADA));
    }
    @Test
    public void finalizarVenda() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        String codigoVenda = "A10";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDAO.cadastrar(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        venda.setStatus(VendaJpa.Status.CONCLUIDA);
        vendaDAO.finalizarVenda(venda);

        VendaJpa vendaConsultada = vendaDAO.consultarComCollection(venda.getId());
        assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
        assertEquals(VendaJpa.Status.CONCLUIDA, vendaConsultada.getStatus());
    }
    @Test(expected = UnsupportedOperationException.class)
    public void tentarAdicionarProdutosVendaFinalizada() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        String codigoVenda = "A11";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDAO.cadastrar(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        venda.setStatus(VendaJpa.Status.CONCLUIDA);
        vendaDAO.finalizarVenda(venda);

        VendaJpa vendaConsultada = vendaDAO.consultarComCollection(venda.getId());
        assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
        assertEquals(VendaJpa.Status.CONCLUIDA, vendaConsultada.getStatus());

        vendaConsultada.adicionarProduto(this.produto, 1);
    }
    private void excluirProdutos() throws DAOException {
        Collection<ProdutoJpa> list = this.produtoDAO.buscarTodos();
        list.forEach(prod -> {
            try {
                this.produtoDAO.excluir(prod);
            } catch (DAOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }
    private void excluirVendas() throws DAOException {
        Collection<VendaJpa> list = this.vendaExclusaoDAO.buscarTodos();
        list.forEach(prod -> {
            try {
                this.vendaExclusaoDAO.excluir(prod);
            } catch (DAOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }
    private ProdutoJpa cadastrarProduto(String codigo, BigDecimal valor) throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        ProdutoJpa produto = new ProdutoJpa();
        produto.setCodigo(codigo);
        produto.setDescricao("Produto 1");
        produto.setNome("Produto 1");
        produto.setValor(valor);
        produtoDAO.cadastrar(produto);
        return produto;
    }
    private ClienteJpa cadastrarCliente() throws TipoChaveNaoEncontradaException, DAOException {
        ClienteJpa cliente = new ClienteJpa();
        cliente.setCpf(rd.nextLong());
        cliente.setNome("Guilherme");
        cliente.setCidade("Porto Alegre");
        cliente.setEnd("End");
        cliente.setEstado("RS");
        cliente.setNumero(10);
        cliente.setTel(1199999999L);
        clienteDAO.cadastrar(cliente);
        return cliente;
    }
    private VendaJpa criarVenda(String codigo) {
        VendaJpa venda = new VendaJpa();
        venda.setCodigo(codigo);
        venda.setDataVenda(Instant.now());
        venda.setCliente(this.cliente);
        venda.setStatus(VendaJpa.Status.INICIADA);
        venda.adicionarProduto(this.produto, 2);
        return venda;
    }
}
