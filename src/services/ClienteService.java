package services;

import dao.IClienteDAO;
import domain.Cliente;
import exceptions.DAOException;
import services.generic.GenericService;

public class ClienteService extends GenericService<Cliente, Long> implements IClienteService {
    public ClienteService(IClienteDAO clienteDAO) {
        super(clienteDAO);
    }

    @Override
    public Cliente buscarPorCPF(Long cpf) throws DAOException {
        return null;
    }

}
