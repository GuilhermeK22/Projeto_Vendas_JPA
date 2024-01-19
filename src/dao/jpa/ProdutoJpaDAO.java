package dao.jpa;

import dao.generic.jpa.GenericJpaDAO;
import domain.jpa.ProdutoJpa;

public class ProdutoJpaDAO extends GenericJpaDAO<ProdutoJpa, Long> implements IProdutoJpaDAO {
    public ProdutoJpaDAO() {
        super(ProdutoJpa.class);
    }
}
