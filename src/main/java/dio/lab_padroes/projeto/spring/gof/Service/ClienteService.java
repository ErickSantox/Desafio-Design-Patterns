package dio.lab_padroes.projeto.spring.gof.Service;

import dio.lab_padroes.projeto.spring.gof.Model.Cliente;
import org.springframework.stereotype.Service;

public interface ClienteService {
    Iterable<Cliente> buscarTodos();

    Cliente buscarporId(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);
}
