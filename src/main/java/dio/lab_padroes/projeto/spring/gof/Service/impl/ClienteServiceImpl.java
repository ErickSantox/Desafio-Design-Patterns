package dio.lab_padroes.projeto.spring.gof.Service.impl;

import dio.lab_padroes.projeto.spring.gof.Model.Cliente;
import dio.lab_padroes.projeto.spring.gof.Model.ClienteRepository;
import dio.lab_padroes.projeto.spring.gof.Model.Endereco;
import dio.lab_padroes.projeto.spring.gof.Model.EnderecoRepository;
import dio.lab_padroes.projeto.spring.gof.Service.ClienteService;
import dio.lab_padroes.projeto.spring.gof.Service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;


    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarporId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {

        SalvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {

        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if (clienteBd.isPresent()){
            SalvarClienteComCep(cliente);
        }


    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    private void SalvarClienteComCep(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }
}
