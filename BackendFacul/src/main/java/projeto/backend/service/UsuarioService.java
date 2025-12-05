package projeto.backend.service;

import org.springframework.stereotype.Service;
import projeto.backend.entity.Usuario;
import projeto.backend.dto.UsuarioRequestDto;
import projeto.backend.mapper.UsuarioMapper;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private final UsuarioMapper mapper;

    public UsuarioService(UsuarioMapper mapper) {
        this.mapper = mapper;
    }

    public Usuario criarUsuario(UsuarioRequestDto dto) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(dto.getEmail())) {
                throw new RuntimeException("Email já cadastrado");
            }
        }

        Usuario usuario = mapper.toEntity(dto);
        usuario.setId(usuarios.size() + 1);
        usuarios.add(usuario);
        return usuario;
    }

    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    public Usuario buscarPorId(Integer id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario atualizarUsuario(Integer id, UsuarioRequestDto dto) {
        Usuario usuario = buscarPorId(id);

        for (Usuario u : usuarios) {
            if (!u.getId().equals(id) && u.getEmail().equals(dto.getEmail())) {
                throw new RuntimeException("Email já cadastrado por outro usuário");
            }
        }

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());

        return usuario;
    }

    public void deletarUsuario(Integer id) {
        Usuario usuario = buscarPorId(id);
        usuarios.remove(usuario);
    }
}