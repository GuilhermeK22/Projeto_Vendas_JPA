package main.br.com.guikapp.services;

import main.br.com.guikapp.dao.IClienteDAO;
import main.br.com.guikapp.domain.Cliente;
import main.br.com.guikapp.exceptions.DAOException;
import main.br.com.guikapp.services.generic.GenericService;

public class ClienteService extends GenericService<Cliente, Long> implements IClienteService {
    public ClienteService(IClienteDAO clienteDAO) {
        super(clienteDAO);
    }

    @Override
    public Cliente buscarPorCPF(Long cpf) throws DAOException {
        return null;
    }

}
