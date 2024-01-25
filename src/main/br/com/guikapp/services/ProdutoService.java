package main.br.com.guikapp.services;

import main.br.com.guikapp.dao.IProdutoDAO;
import main.br.com.guikapp.domain.Produto;
import main.br.com.guikapp.services.generic.GenericService;

public class ProdutoService extends GenericService<Produto, String> implements IProdutoService {
    public ProdutoService(IProdutoDAO dao) {
        super(dao);
    }
}
