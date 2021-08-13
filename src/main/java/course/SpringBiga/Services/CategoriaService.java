package course.SpringBiga.Services;

import course.SpringBiga.Domain.Categoria;
import course.SpringBiga.Repositories.CategoriaRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;


    public Categoria find(Integer id) throws ObjectNotFoundException {
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
        }

    public Categoria insert (Categoria obj){//insert quando o id for nulo
        obj.setId(null);
        return repo.save(obj);
    }

    public Categoria update (Categoria obj) throws ObjectNotFoundException {//update quando o id nao for nulo
        find(obj.getId());
        return repo.save(obj);
    }

    public void delete(Integer id){
            repo.deleteById(id);
    }

    public List<Categoria> findAll() {
        return repo.findAll();
    }
}


