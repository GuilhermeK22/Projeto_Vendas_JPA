package dao.jpa;

import dao.generic.jpa.GenericJpaDAO;
import domain.Cliente;
import domain.jpa.ClienteJpa;

public class ClienteJpaDAO extends GenericJpaDAO<ClienteJpa, Long>implements IClienteJpaDAO {
    public ClienteJpaDAO() {
        super(ClienteJpa.class);
    }
}
