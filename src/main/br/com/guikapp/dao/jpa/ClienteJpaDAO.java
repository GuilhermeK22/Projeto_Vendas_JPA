package main.br.com.guikapp.dao.jpa;

import main.br.com.guikapp.dao.generic.jpa.GenericJpaDAO;
import main.br.com.guikapp.domain.jpa.ClienteJpa;

public class ClienteJpaDAO extends GenericJpaDAO<ClienteJpa, Long>implements IClienteJpaDAO {
    public ClienteJpaDAO() {
        super(ClienteJpa.class);
    }
}
