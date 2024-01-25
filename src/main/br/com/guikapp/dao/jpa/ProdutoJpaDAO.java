package main.br.com.guikapp.dao.jpa;

import main.br.com.guikapp.dao.generic.jpa.GenericJpaDAO;
import main.br.com.guikapp.domain.jpa.ProdutoJpa;

public class ProdutoJpaDAO extends GenericJpaDAO<ProdutoJpa, Long> implements IProdutoJpaDAO {
    public ProdutoJpaDAO() {
        super(ProdutoJpa.class);
    }
}
