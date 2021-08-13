package course.SpringBiga.Domain.DTO;


import com.sun.istack.NotNull;
import course.SpringBiga.Domain.Cliente;

public class ClienteDTO {

    private Integer id;

    @NotNull
    private String name;
    @NotNull
    private String email;

    public ClienteDTO(){
    }

    public ClienteDTO(Cliente obj){
        id = obj.getId();
        name = obj.getName();
        email = obj.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
