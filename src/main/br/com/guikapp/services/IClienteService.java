package main.br.com.guikapp.services;

import main.br.com.guikapp.domain.Cliente;
import main.br.com.guikapp.exceptions.DAOException;
import main.br.com.guikapp.services.generic.IGenericService;

public interface IClienteService extends IGenericService<Cliente, Long> {
    Cliente buscarPorCPF(Long cpf) throws DAOException;
}
