package course.SpringBiga.Services;

import course.SpringBiga.Domain.Cidade;
import course.SpringBiga.Domain.Cliente;
import course.SpringBiga.Domain.DTO.ClienteDTO;
import course.SpringBiga.Domain.DTO.ClienteNewDTO;
import course.SpringBiga.Domain.Endereco;
import course.SpringBiga.Domain.Enums.TipoCliente;
import course.SpringBiga.Repositories.CidadeRepository;
import course.SpringBiga.Repositories.ClienteRepository;
import course.SpringBiga.Repositories.EnderecoRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;


    public Cliente find(Integer id) throws ObjectNotFoundException {
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
        }

    public Cliente insert (Cliente obj){//insert quando o id for nulo
        obj.setId(null);
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }


    public Cliente update (Cliente obj) throws ObjectNotFoundException {//update quando o id nao for nulo
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(Integer id){
        repo.deleteById(id);
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Cliente fromDTO(ClienteDTO objDTO){
        return new Cliente(objDTO.getId(),objDTO.getName(),objDTO.getEmail(),null,null);
    }

    public Cliente fromDTO(ClienteNewDTO objDTO){
        Cliente cli = new Cliente(null, objDTO.getName(), objDTO.getEmail(), objDTO.getCpfCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
        Optional<Cidade> cid = cidadeRepository.findById(objDTO.getCidadeId());
        Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid.get());
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDTO.getTelefone1());
        if (objDTO.getTelefone2() != null){
            cli.getTelefones().add(objDTO.getTelefone2());
        }
        if (objDTO.getTelefone3() != null){
            cli.getTelefones().add(objDTO.getTelefone3());
        }
        return cli;
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }


}

