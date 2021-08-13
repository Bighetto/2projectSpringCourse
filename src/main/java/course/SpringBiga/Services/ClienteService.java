package course.SpringBiga.Services;

import course.SpringBiga.Domain.Categoria;
import course.SpringBiga.Domain.Cliente;
import course.SpringBiga.Domain.DTO.ClienteDTO;
import course.SpringBiga.Repositories.ClienteRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;


    public Cliente find(Integer id) throws ObjectNotFoundException {
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
        }

    public Cliente insert (Cliente obj){//insert quando o id for nulo
        obj.setId(null);
        return repo.save(obj);
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

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }
}

